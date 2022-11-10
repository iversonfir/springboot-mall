package com.egh.springbootmall.dao;

import com.egh.springbootmall.constant.ProductCategory;
import com.egh.springbootmall.dto.ProductQueryParams;
import com.egh.springbootmall.dto.ProductRequest;
import com.egh.springbootmall.model.Product;

import java.util.List;

public interface ProductDao
{
    Product getProductById(int productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProduct(Integer productId);

    List<Product> getProducts(ProductQueryParams productQueryParams);

    Integer countProduct(ProductQueryParams productQueryParams);
}
