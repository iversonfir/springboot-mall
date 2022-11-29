package com.egh.springbootmall.controller;

import com.egh.springbootmall.dto.CreateOrderRequest;
import com.egh.springbootmall.model.Order;
import com.egh.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
public class OrderController
{
    @Autowired
    private OrderService orderService;

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Validated CreateOrderRequest createOrderRequest)
    {
        Integer orderId = orderService.createOrder(userId, createOrderRequest);
        Order order = orderService.getOrderByOrderId(orderId);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
