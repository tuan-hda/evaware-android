package com.example.evaware.data.model;

public class UserModel {
    public UserModel(){

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserModel(String id, String email, String name, String img_url) {
        this.id = id;
        this.email = email;
        this.img_url = img_url;
        this.name = name;
    }

    private String id;
    private String email;
    private String img_url;
    private String name;
}
