package com.example.evaware.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;

public class BagItemModel {
    @DocumentId
    public String id;
    public DocumentReference productRef;
    public Timestamp createdAt;
    public Integer qty;
    public Timestamp updatedAt;
    public DocumentReference variationRef;

    public BagItemModel() {}

    public BagItemModel(DocumentReference productRef, Timestamp createdAt, Integer qty,
                        Timestamp updatedAt, DocumentReference variationRef) {
        this.productRef = productRef;
        this.createdAt = createdAt;
        this.qty = qty;
        this.updatedAt = updatedAt;
        this.variationRef = variationRef;
    }
}
