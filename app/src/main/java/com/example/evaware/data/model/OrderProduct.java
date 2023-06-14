package com.example.evaware.data.model;

public class OrderProduct {
    private int mImageResourceId;
    private String price;
    private String description;

    public OrderProduct(int mImageResourceId, String price, String description) {
        this.mImageResourceId = mImageResourceId;
        this.price = price;
        this.description = description;
    }

    public int getmImageResourceId() {
        return mImageResourceId;
    }

    public String getPrice() {
        return price;
    }


    public String getDescription() {
        return description;
    }

}
