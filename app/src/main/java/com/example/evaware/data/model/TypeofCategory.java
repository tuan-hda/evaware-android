package com.example.evaware.data.model;

import com.google.firebase.firestore.DocumentId;


public class TypeofCategory {


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getImg_url() {
        return img_url;
    }

    @DocumentId
    private String id;
    private String name;
    private String desc;
    private String img_url;

    public TypeofCategory() {

    }

    public TypeofCategory(String id, String name, String desc, String img_url) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.img_url = img_url;
    }
}
