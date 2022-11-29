package com.egh.springbootmall.dto;

import java.util.List;

public class CreateOrderRequest
{
    private List<BuyItem> buyItems;

    public List<BuyItem> getBuyItems()
    {
        return buyItems;
    }

    public void setBuyItems(List<BuyItem> buyItems)
    {
        this.buyItems = buyItems;
    }
}
