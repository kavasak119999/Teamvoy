package com.teamvoy.controller;

import com.teamvoy.dto.OrderItemRequest;
import com.teamvoy.entity.Order;
import com.teamvoy.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place-order")
    public ResponseEntity<Order> placeOrder(@RequestBody List<OrderItemRequest> orderItems,
                                            Authentication authentication) {
        Order order = orderService.placeOrder(orderItems, authentication.getName());
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PostMapping("/mark-paid/{orderId}")
    public ResponseEntity<Void> markOrderAsPaid(@PathVariable Long orderId,
                                                Authentication authentication) {
        orderService.markOrderAsPaid(orderId, authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}