package com.egh.springbootmall.dao;

import com.egh.springbootmall.dto.ProductRequest;
import com.egh.springbootmall.model.Product;

public interface ProductDao
{
    Product getProductById(int productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProduct(Integer productId);
}
