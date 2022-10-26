package com.egh.springbootmall.service.impl;

import com.egh.springbootmall.dao.ProductDao;
import com.egh.springbootmall.model.Product;
import com.egh.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(int productId) {
        return productDao.getProductById(productId);
    }
}