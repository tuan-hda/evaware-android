package com.example.evaware.data.model;

public class Suggestion {
    public Suggestion(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
    }

    private String name;
    private String imgUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
