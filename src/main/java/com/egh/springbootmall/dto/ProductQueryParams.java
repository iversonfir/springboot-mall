package com.egh.springbootmall.dto;

import com.egh.springbootmall.constant.ProductCategory;

public class ProductQueryParams
{
    private ProductCategory category;
    private String search;
    private String orderBY;
    private String order;

    public String getOrderBY()
    {
        return orderBY;
    }

    public void setOrderBY(String orderBY)
    {
        this.orderBY = orderBY;
    }

    public String getOrder()
    {
        return order;
    }

    public void setOrder(String order)
    {
        this.order = order;
    }

    public ProductCategory getCategory()
    {
        return category;
    }

    public void setCategory(ProductCategory category)
    {
        this.category = category;
    }

    public String getSearch()
    {
        return search;
    }

    public void setSearch(String search)
    {
        this.search = search;
    }
}
