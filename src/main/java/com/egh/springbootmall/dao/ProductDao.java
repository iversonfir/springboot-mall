package com.egh.springbootmall.dao;

import com.egh.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(int productId);
}
