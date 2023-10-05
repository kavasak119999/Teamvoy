package com.teamvoy.services;

import com.teamvoy.dto.OrderItemRequest;
import com.teamvoy.entity.Order;

import java.util.List;

public interface OrderService {
    void markOrderAsPaid(Long orderId, String username);
    Order placeOrder(List<OrderItemRequest> orderItems, String username);
    void deleteExpiredOrders();
}