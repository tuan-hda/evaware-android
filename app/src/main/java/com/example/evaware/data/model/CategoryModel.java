package com.example.evaware.data.model;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;

public class CategoryModel {
    public CategoryModel(String id, String img_url, String name, DocumentReference type_ref) {
        this.id = id;
        this.img_url = img_url;
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryModel{" +
                "id='" + id + '\'' +
                ", img_url='" + img_url + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
    public CategoryModel(){

    }

    public String getId() {
        return id;
    }

    public String getImg_url() {
        return img_url;
    }

    @DocumentId
    private String id;
    private String img_url;
    private String name;
    public String getImgUrl() {
        return img_url;
    }

    public void setImgUrl(String imgUrl) {
        this.img_url = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryModel(String name, String imgUrl) {
        this.img_url = imgUrl;
        this.name = name;
    }
}
