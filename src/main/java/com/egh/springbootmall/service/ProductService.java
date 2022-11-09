package com.egh.springbootmall.service;

import com.egh.springbootmall.constant.ProductCategory;
import com.egh.springbootmall.dto.ProductRequest;
import com.egh.springbootmall.model.Product;

import java.util.List;

public interface ProductService
{
    Product getProductById(int productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProduct(Integer productId);

    List<Product> getProducts(ProductCategory category, String search);
}
