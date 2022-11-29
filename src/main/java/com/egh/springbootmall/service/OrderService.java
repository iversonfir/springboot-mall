package com.egh.springbootmall.service;

import com.egh.springbootmall.dto.CreateOrderRequest;
import com.egh.springbootmall.model.Order;

public interface OrderService
{
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderByOrderId(Integer orderId);
}
