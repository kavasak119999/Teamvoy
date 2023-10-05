package com.teamvoy.services;

import com.teamvoy.dto.OrderItemRequest;
import com.teamvoy.entity.Goods;
import com.teamvoy.entity.Order;
import com.teamvoy.entity.User;
import com.teamvoy.repository.GoodsRepository;
import com.teamvoy.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
public class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @InjectMocks
    private OrderServiceImpl orderServiceMocks;

    @Autowired
    private OrderRepository orderRepository;

    @Mock
    private OrderRepository orderRepositoryMock;

    @Mock
    private GoodsRepository goodsRepositoryMock;

    @Mock
    private UserDetailsService userDetailsServiceMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService.setOrderRepository(orderRepository);
    }

    @Test
    public void testPlaceOrder() {
        // Arrange
        List<OrderItemRequest> orderItems = new ArrayList<>();
        orderItems.add(new OrderItemRequest(1L, 2));

        UserDetails userDetails = new User("username", "password");

        when(userDetailsServiceMock.loadUserByUsername("username")).thenReturn(userDetails);

        Goods goods = new Goods("Test Goods", 100.0, 5);
        when(goodsRepositoryMock.findById(1L)).thenReturn(Optional.of(goods));

        // Action
        Order order = orderService.placeOrder(orderItems, userDetails.getUsername());

        // Assert
        assertNotNull(order);
        assertFalse(order.isPaid());
        assertNotNull(order.getOrderTime());
        assertEquals(userDetails, order.getUser());
        assertNotNull(order.getGoodsList());
        assertEquals(200.0, order.getTotalPrice()); // Assuming goods price is 100.0 for this test
    }

    @Test
    public void testMarkOrderAsPaid() {
        Long orderId = 1L;

        User authenticatedUser = new User("username", "password");
        Order order = new Order();
        order.setId(orderId);
        order.setUser(authenticatedUser);

        when(orderRepositoryMock.findById(orderId)).thenReturn(Optional.of(order));

        // Action
        orderServiceMocks.markOrderAsPaid(orderId, authenticatedUser.getUsername());

        // Assert
        assertTrue(order.isPaid());
    }

    @Test
    public void testDeleteExpiredOrders() {
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);
        List<Order> expiredOrders = new ArrayList<>();
        when(orderRepositoryMock.findByPaidAndOrderTimeBefore(false, tenMinutesAgo)).thenReturn(expiredOrders);

        // Action
        orderServiceMocks.deleteExpiredOrders();

        // Assert
        verify(orderRepositoryMock, times(1)).deleteAll(expiredOrders);
    }

}