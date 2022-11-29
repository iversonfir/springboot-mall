package com.egh.springbootmall.dao;

import com.egh.springbootmall.model.Order;
import com.egh.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao
{

    Integer createOrder(Integer userId, int totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItems);

    Order getOrderByOrderId(Integer orderId);

    List<OrderItem> getOrderItemByOrderId(Integer orderId);
}
