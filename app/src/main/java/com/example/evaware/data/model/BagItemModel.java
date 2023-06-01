package com.example.evaware.data.model;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

public class BagItemModel {
    @DocumentId
    public String id;
    public String path;
    public DocumentReference product_ref;
    @Exclude
    public ProductModel product;

    private Integer price;
    @ServerTimestamp
    public Timestamp created_at;
    public Integer qty;
    @ServerTimestamp
    public Timestamp updated_at;

    public BagItemModel(String id, String path, DocumentReference product_ref, ProductModel product, Integer price, Timestamp created_at, Integer qty, Timestamp updated_at, DocumentReference variation_ref, VariationModel variation) {
        this.id = id;
        this.path = path;
        this.product_ref = product_ref;
        this.product = product;
        this.price = price;
        this.created_at = created_at;
        this.qty = qty;
        this.updated_at = updated_at;
        this.variation_ref = variation_ref;
        this.variation = variation;
    }

    public DocumentReference variation_ref;
    public VariationModel variation;

    public BagItemModel() {
    }

    public BagItemModel(DocumentReference productRef, DocumentReference variation_ref, Integer qty) {
        this.product_ref = productRef;
        this.variation_ref = variation_ref;
        this.qty = qty;
    }

    @NonNull
    @Override
    public String toString() {
        return "BagItemModel{" +
                "id='" + id + '\'' +
                ", path='" + path + '\'' +
                ", product_ref=" + product_ref +
                ", created_at=" + created_at +
                ", qty=" + qty +
                ", updated_at=" + updated_at +
                ", variation_ref=" + variation_ref +
                '}';
    }
}
