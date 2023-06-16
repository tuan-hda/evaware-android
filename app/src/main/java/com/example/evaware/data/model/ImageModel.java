package com.example.evaware.data.model;

import com.google.firebase.firestore.DocumentId;

public class ImageModel {
    @DocumentId
    public String id;
    public String img_url;

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public ImageModel(String id, String img_url) {
        this.id = id;
        this.img_url = img_url;
    }
    public ImageModel(){

    }

}
