package com.egh.springbootmall.controller;

import com.egh.springbootmall.constant.ProductCategory;
import com.egh.springbootmall.dto.ProductQueryParams;
import com.egh.springbootmall.dto.ProductRequest;
import com.egh.springbootmall.model.Product;
import com.egh.springbootmall.service.ProductService;
import com.egh.springbootmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
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
    public ResponseEntity<Page<Product>> getProducts(
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,
            @RequestParam(defaultValue = "5") @Max(100) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
    )
    {
        ProductQueryParams productQueryParams = new ProductQueryParams();

        //查詢條件
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);

        //排序
        productQueryParams.setOrderBY(orderBy);
        productQueryParams.setOrder(sort);

        //分頁
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        List<Product> products = productService.getProducts(productQueryParams);
        Integer total=productService.countProduct(productQueryParams);

        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResult(products);

        return ResponseEntity.status(HttpStatus.OK).body(page);
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