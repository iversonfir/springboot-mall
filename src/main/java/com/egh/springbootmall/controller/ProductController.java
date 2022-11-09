package com.egh.springbootmall.controller;

import com.egh.springbootmall.constant.ProductCategory;
import com.egh.springbootmall.dto.ProductQueryParams;
import com.egh.springbootmall.dto.ProductRequest;
import com.egh.springbootmall.model.Product;
import com.egh.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController
{

    @Autowired
    private ProductService productService;

    /**
     * getProducts 與 getProduct 實作不判斷 null 原因，Restful Api 定義的差別
     * 就我的理解就是
     * getProducts 是盒子，永遠都在
     * getProduct 是單一元素，所以要判斷資料存不存在
     */
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort
    )
    {
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBY(orderBy);
        productQueryParams.setOrder(sort);

        List<Product> products = productService.getProducts(productQueryParams);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId)
    {
        Product product = productService.getProductById(productId);
        if (product == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else
        {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest)
    {
        Integer productId = productService.createProduct(productRequest);
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest)
    {
        if (productId == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        productService.updateProduct(productId, productRequest);
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId)
    {
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}