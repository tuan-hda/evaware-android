package com.example.evaware.data.model;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.ServerTimestamp;

public class ProductModel {
    @DocumentId
    public String id;
    public String path;
    public DocumentReference category_ref;
    @ServerTimestamp

    public Timestamp created_at;
    @ServerTimestamp
    public Timestamp updated_at;
    public String desc;
    public DocumentReference discount_ref;
    public String image_thumbnail;
    public String name;
    public int price;
    public DocumentReference room_ref;

    public ProductModel() {
    }

    public ProductModel(String id, DocumentReference category_ref, String desc, DocumentReference discount_ref, String image_thumbnail, String name, int price, DocumentReference room_ref) {
        this.id = id;
        this.category_ref = category_ref;
        this.desc = desc;
        this.discount_ref = discount_ref;
        this.image_thumbnail = image_thumbnail;
        this.name = name;
        this.price = price;
        this.room_ref = room_ref;
    }

    @NonNull
    @Override
    public String toString() {
        return "ProductModel{" +
                "id='" + id + '\'' +
                ", path='" + path + '\'' +
                ", category_ref=" + category_ref +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", desc='" + desc + '\'' +
                ", discount_ref=" + discount_ref +
                ", image_thumbnail='" + image_thumbnail + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", room_ref=" + room_ref +
                '}';
    }
}
