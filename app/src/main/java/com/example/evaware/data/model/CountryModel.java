package com.example.evaware.data.model;

public class CountryModel {
    private int mImageResourceId;
    private String mName;

    public CountryModel(int imageResourceId, String name) {
        mImageResourceId = imageResourceId;
        mName = name;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public String getName() {
        return mName;
    }
}
