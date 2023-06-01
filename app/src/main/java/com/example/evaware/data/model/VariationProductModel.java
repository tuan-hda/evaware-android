package com.example.evaware.data.model;

import com.google.firebase.firestore.DocumentId;

public class VariationProductModel {
    public VariationProductModel(String id, int inventory, String name, String img_url) {
        this.id = id;
        this.inventory = inventory;
        this.name = name;
        this.img_url = img_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public VariationProductModel() {

    }

    @DocumentId
    private String id;
    private int inventory;
    private String name;
    private String img_url;
}
