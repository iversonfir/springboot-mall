package com.egh.springbootmall.dao.impl;

import com.egh.springbootmall.dao.OrderDao;
import com.egh.springbootmall.model.Order;
import com.egh.springbootmall.model.OrderItem;
import com.egh.springbootmall.rowmapper.OrderItemRowMapper;
import com.egh.springbootmall.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao
{
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createOrder(Integer userId, int totalAmount)
    {
        String sql = "INSERT INTO `order` (user_id,total_amount,created_date, last_modified_date) " +
                "VALUES (:userId,:totalAmount,:createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("totalAmount",totalAmount);

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int orderId = keyHolder.getKey().intValue();
        return orderId;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItems)
    {
        String sql = "INSERT INTO order_item(order_id, product_id, quantity, amount) " +
                "VALUES (:orderId, :productId, :quantity, :amount)";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItems.size()];

        for (int i = 0; i < orderItems.size(); i++) {
            OrderItem orderItem = orderItems.get(i);

            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("orderId", orderId);
            parameterSources[i].addValue("productId", orderItem.getProductId());
            parameterSources[i].addValue("quantity", orderItem.getQuantity());
            parameterSources[i].addValue("amount", orderItem.getAmount());
        }

        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
    }

    @Override
    public Order getOrderByOrderId(Integer orderId)
    {
        String sql = "SELECT * FROM `order` WHERE order_id= :orderId ";
        HashMap<String, Object> map = new HashMap<>();
        map.put("orderId",orderId);
        List<Order> list = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());
        return list.size()>0?list.get(0):null;
    }

    @Override
    public List<OrderItem> getOrderItemByOrderId(Integer orderId)
    {
        String sql = "SELECT o.*,p.product_name " +
                "FROM order_item o " +
                "LEFT JOIN product p on p.product_id=o.product_id " +
                "WHERE o.order_id=:orderId ";
        HashMap<String, Object> map = new HashMap<>();
        map.put("orderId",orderId);
        List<OrderItem> list = namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());
        return list.size()>0?list:null;
    }
}
