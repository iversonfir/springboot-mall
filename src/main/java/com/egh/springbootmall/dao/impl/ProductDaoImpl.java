package com.egh.springbootmall.dao.impl;

import com.egh.springbootmall.dao.ProductDao;
import com.egh.springbootmall.dto.ProductQueryParams;
import com.egh.springbootmall.dto.ProductRequest;
import com.egh.springbootmall.model.Product;
import com.egh.springbootmall.rowmapper.ProductRowMapper;
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
public class ProductDaoImpl implements ProductDao
{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(int productId)
    {
        String sql = "select product_id,product_name, category, image_url, price," +
                " stock, description, created_date, last_modified_date " +
                "from product where product_id= :product_id";
        HashMap<String, Object> map = new HashMap<>();
        map.put("product_id", productId);
        List<Product> list = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public Integer createProduct(ProductRequest productRequest)
    {

        String sql = "INSERT INTO product(product_name, category, image_url, price, stock, " +
                "description, created_date, last_modified_date) " +
                "VALUES (:productName, :category, :imageUrl, :price, :stock, :description, " +
                ":createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int productId = keyHolder.getKey().intValue();

        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest)
    {
        String sql = "UPDATE product SET product_name=:productName, category=:category, image_url=:imageUrl, price=:price, stock=:stock," +
                "description=:description, last_modified_date=:lastModifiedDate" +
                " WHERE product_id=:productId";
        HashMap<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());
        map.put("lastModifiedDate", new Date());
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteProduct(Integer productId)
    {
        String sql = "DELETE FROM product WHERE product_id=:productId";
        HashMap<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams)
    {
        String sql = " SELECT product_id,product_name, category, image_url, price, " +
                " stock, description, created_date, last_modified_date " +
                " FROM product " +
                " WHERE 1=1 ";

        HashMap<String, Object> map = new HashMap<>();

        // 查詢條件
        if (productQueryParams.getCategory() != null)
        {
            sql += " AND category=:category ";
            map.put("category", productQueryParams.getCategory().name());
        }
        if ( productQueryParams.getSearch() != null)
        {
            sql += "AND product_name like :search ";
            map.put("search", "%" + productQueryParams.getSearch() + "%");
        }

        //排序
        sql+=" ORDER BY " +productQueryParams.getOrderBY()+" "+ productQueryParams.getOrder();

        //分頁
        sql+=" LIMIT :limit  OFFSET :offset ";
        map.put("limit",productQueryParams.getLimit());
        map.put("offset",productQueryParams.getOffset());

        return namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
    }

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams)
    {
        String sql = " SELECT count(*) " +
                " FROM product " +
                " WHERE 1=1 ";

        HashMap<String, Object> map = new HashMap<>();

        // 查詢條件
        if (productQueryParams.getCategory() != null)
        {
            sql += " AND category=:category ";
            map.put("category", productQueryParams.getCategory().name());
        }
        if ( productQueryParams.getSearch() != null)
        {
            sql += "AND product_name like :search ";
            map.put("search", "%" + productQueryParams.getSearch() + "%");
        }
        return namedParameterJdbcTemplate.queryForObject(sql,map,Integer.class);
    }
}
