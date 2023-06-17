package com.example.evaware.data.model;

import com.example.evaware.R;
import com.google.firebase.firestore.DocumentReference;

public class SavedModel {
    public DocumentReference productRef;
    public DocumentReference wishListRef;
    public String imageUrl;
    public Double price;
    public String desc;
    public String name;

    public SavedModel(DocumentReference wishListRef, DocumentReference productRef, String imageUrl, String name, Double price, String desc) {
        this.wishListRef = wishListRef;
        this.productRef = productRef;
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
        this.desc = desc;
    }
}
