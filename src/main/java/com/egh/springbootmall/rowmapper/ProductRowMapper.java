package com.egh.springbootmall.rowmapper;

import com.egh.springbootmall.constant.ProductCategory;
import com.egh.springbootmall.model.Product;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductRowMapper implements RowMapper<Product>
{
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        Product product = new Product();
        product.setProductId(rs.getInt("product_id"));
        product.setPrice(rs.getInt("price"));
        product.setStock(rs.getInt("stock"));
        product.setProductName(rs.getString("product_name"));
        product.setCategory(ProductCategory.valueOf(rs.getString("category")));
        product.setImageUrl(rs.getString("image_url"));
        product.setDescription(rs.getString("description"));
        product.setCreatedDate(rs.getTimestamp("created_date"));
        product.setLastModifiedDate(rs.getTimestamp("last_modified_date"));

        return product;
    }
}
