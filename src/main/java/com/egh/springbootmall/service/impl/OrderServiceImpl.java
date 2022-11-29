package com.egh.springbootmall.service.impl;

import com.egh.springbootmall.dao.OrderDao;
import com.egh.springbootmall.dao.ProductDao;
import com.egh.springbootmall.dao.UserDao;
import com.egh.springbootmall.dto.BuyItem;
import com.egh.springbootmall.dto.CreateOrderRequest;
import com.egh.springbootmall.model.Order;
import com.egh.springbootmall.model.OrderItem;
import com.egh.springbootmall.model.Product;
import com.egh.springbootmall.model.User;
import com.egh.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService
{
    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Transient
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest)
    {

        // 1. 檢查 user 是否存在，不存在則加上 log
        User user = userDao.getUserById(userId);
        if(user==null){
            log.warn("無此使用者 {} ",userId );
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItems = new ArrayList<>();
        for (BuyItem buyItem : createOrderRequest.getBuyItems())
        {
            Integer productId = buyItem.getProductId();
            // 2. 檢查 product 是否存在
            Product product = productDao.getProductById(productId);
            if (product==null)
            {
                log.warn("無此商品 {} ",productId );
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            // 3. 庫存是否足夠

            //嚴重錯誤!!!!
            // if (product.getStock()<=0)
            // 要用當下扣除的來算
            int stock = product.getStock()- buyItem.getQuantity();
            if (stock<0)
            {
                log.warn("商品 {} 庫存數量不足，無法購買。剩餘庫存 {}，欲購買數量 {}",
                        buyItem.getProductId(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            Integer quantity = buyItem.getQuantity();
            int amount = product.getPrice() * quantity;
            totalAmount +=amount;

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(productId);
            orderItem.setQuantity(quantity);
            orderItem.setAmount(amount);

            // 4. 扣除商品庫存
            productDao.updateStock(productId,stock);

            orderItems.add(orderItem);
        }

        Integer orderId = orderDao.createOrder(userId,totalAmount);

        orderDao.createOrderItems(orderId,orderItems);

        return orderId;
    }

    @Override
    public Order getOrderByOrderId(Integer orderId)
    {
        Order order=orderDao.getOrderByOrderId(orderId);
        List<OrderItem> orderItems=orderDao.getOrderItemByOrderId(orderId);
        order.setOrderItems(orderItems);
        return order;
    }
}
