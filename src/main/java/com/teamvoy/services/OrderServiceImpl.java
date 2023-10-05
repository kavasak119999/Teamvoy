package com.teamvoy.services;

import com.teamvoy.dto.OrderItemRequest;
import com.teamvoy.entity.Goods;
import com.teamvoy.entity.Order;
import com.teamvoy.entity.User;
import com.teamvoy.exceptions.NotFoundException;
import com.teamvoy.repository.GoodsRepository;
import com.teamvoy.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private OrderRepository orderRepository;
    private GoodsRepository goodsRepository;
    private UserDetailsService userDetailsService;


    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderServiceImpl(OrderRepository orderRepository, GoodsRepository goodsRepository, UserDetailsService userDetailsService) {
        this.orderRepository = orderRepository;
        this.goodsRepository = goodsRepository;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Order placeOrder(List<OrderItemRequest> orderItems,
                            String username) {
        logger.info("Placing order: username={}", username);

        User user = (User) userDetailsService.loadUserByUsername(username);

        Order order = new Order();
        order.setPaid(false);
        order.setOrderTime(LocalDateTime.now());
        order.setUser(user);

        List<Goods> goodsList = new ArrayList<>();
        double totalOrderPrice = 0.0; // Initialize the total price

        for (OrderItemRequest item : orderItems) {
            Goods goods = goodsRepository.findById(item.getGoodsId())
                    .orElseThrow(NotFoundException::new);

            if (goods.getQuantity() < item.getQuantity()) {
                logger.warn("Insufficient quantity for goods with ID " + goods.getId());
                throw new RuntimeException("Insufficient quantity for goods with ID " + goods.getId());
            }

            goods.setQuantity(goods.getQuantity() - item.getQuantity());
            goodsRepository.save(goods);

            // Calculate the price for this item and add it to the total
            double itemPrice = goods.getPrice() * item.getQuantity();
            totalOrderPrice += itemPrice;

            goodsList.add(goods);
        }

        order.setGoodsList(goodsList);
        order.setTotalPrice(totalOrderPrice);

        Order result = orderRepository.save(order);

        logger.info("Order placed: {}", order.getId());
        return result;
    }

    @Override
    public void markOrderAsPaid(Long orderId,
                                String username) {
        logger.info("Marking order as paid: orderId={}, username={}", orderId, username);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(NotFoundException::new);

        // Check if the order's user matches the authenticated user
        String orderUsername = order.getUser().getUsername();

        if (!username.equals(orderUsername)) {
            logger.warn("Access denied: You can only mark your own orders as paid. Username: " + username);
            throw new AccessDeniedException("Access denied: You can only mark your own orders as paid.");
        }

        // Proceed to mark the order as paid
        if (!order.isPaid()) {
            order.setPaid(true);
            orderRepository.save(order);

            logger.info("Order marked as paid: {}", orderId);
        } else {
            logger.info("Order is already marked as paid: {}", orderId);
        }
    }

    // Scheduled method for deleting expired orders
    @Scheduled(fixedDelay = 600000) // 10 minutes in milliseconds
    @Override
    public void deleteExpiredOrders() {
        logger.info("Deleting expired orders...");

        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);
        List<Order> expiredOrders = orderRepository.findByPaidAndOrderTimeBefore(false, tenMinutesAgo);
        orderRepository.deleteAll(expiredOrders);

        logger.info("Deleted {} expired orders", expiredOrders.size());
    }

}