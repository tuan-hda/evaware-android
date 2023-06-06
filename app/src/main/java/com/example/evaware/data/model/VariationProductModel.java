package com.example.evaware.data.model;

import com.google.firebase.firestore.DocumentId;

public class VariationProductModel {
    public VariationProductModel(String id, int inventory, String name) {
        this.id = id;
        this.inventory = inventory;
        this.name = name;
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

    @Override
    public String toString() {
        return "VariationProductModel{" +
                "id='" + id + '\'' +
                ", inventory=" + inventory +
                ", name='" + name + '\'' +
                '}';
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

    public VariationProductModel() {

    }

    @DocumentId
    private String id;
    private int inventory;
    private String name;
}
