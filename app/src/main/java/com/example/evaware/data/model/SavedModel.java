package com.example.evaware.data.model;

import com.example.evaware.R;
import com.google.firebase.firestore.DocumentReference;

public class SavedModel {
    public DocumentReference productRef;
    public DocumentReference wishListRef;
    public String imageUrl;
    public Float price;
    public String desc;

    public SavedModel(DocumentReference wishListRef, DocumentReference productRef, String imageUrl, Float price, String desc) {
        this.wishListRef = wishListRef;
        this.productRef = productRef;
        this.imageUrl = imageUrl;
        this.price = price;
        this.desc = desc;
    }
}
