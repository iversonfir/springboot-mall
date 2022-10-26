package com.egh.springbootmall.dao.impl;

import com.egh.springbootmall.dao.ProductDao;
import com.egh.springbootmall.model.Product;
import com.egh.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(int productId) {
        String sql = "select product_id,product_name, category, image_url, price," +
                " stock, description, created_date, last_modified_date " +
                "from product where product_id= :product_id";
        HashMap<String, Object> map = new HashMap<>();
        map.put("product_id", productId);
        List<Product> list = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        return list.size() > 0 ? list.get(0) : null;
    }
}
