package com.egh.springbootmall.service.impl;

import com.egh.springbootmall.constant.ProductCategory;
import com.egh.springbootmall.dao.ProductDao;
import com.egh.springbootmall.dto.ProductQueryParams;
import com.egh.springbootmall.dto.ProductRequest;
import com.egh.springbootmall.model.Product;
import com.egh.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService
{
    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(int productId)
    {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest)
    {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest)
    {
        productDao.updateProduct(productId, productRequest);
    }

    @Override
    public void deleteProduct(Integer productId)
    {
        productDao.deleteProduct(productId);
    }

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams)
    {
        return productDao.getProducts(productQueryParams);
    }

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams)
    {
        return productDao.countProduct(productQueryParams);
    }
}
