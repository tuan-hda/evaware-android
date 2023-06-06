package com.example.evaware.data.model;

public class ImgProduct {
    public ImgProduct(String id, String img_url) {
        this.id = id;
        this.img_url = img_url;
    }
    public ImgProduct(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    String id;
    String img_url;
}
