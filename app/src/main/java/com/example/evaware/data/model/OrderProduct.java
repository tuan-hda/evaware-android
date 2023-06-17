package com.example.evaware.data.model;

public class OrderProduct {
    public String imageUrl;
    public String price;
    public String description;

    public OrderProduct(String imageUrl, String price, String description) {
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
    }
}
