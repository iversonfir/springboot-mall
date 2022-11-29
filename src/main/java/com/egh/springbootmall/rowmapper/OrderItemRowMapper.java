package com.egh.springbootmall.rowmapper;

import com.egh.springbootmall.model.OrderItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemRowMapper implements RowMapper<OrderItem>
{
    @Override
    public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(rs.getInt("order_id"));
        orderItem.setQuantity(rs.getInt("quantity"));
        orderItem.setAmount(rs.getInt("amount"));
        orderItem.setProductId(rs.getInt("product_id"));
        orderItem.setOrderItemId(rs.getInt("order_item_id"));

        //product name
        orderItem.setProductName(rs.getString("product_name"));

        return orderItem;
    }
}