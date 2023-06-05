package com.example.evaware.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;

public class WishItemModel {
    public WishItemModel(String id, DocumentReference product_ref, Timestamp create_at, Timestamp update_at) {
        this.id = id;
        this.product_ref = product_ref;
        this.create_at = create_at;
        this.update_at = update_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DocumentReference getProduct_ref() {
        return product_ref;
    }

    public void setProduct_ref(DocumentReference product_ref) {
        this.product_ref = product_ref;
    }

    public Timestamp getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Timestamp create_at) {
        this.create_at = create_at;
    }

    public Timestamp getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(Timestamp update_at) {
        this.update_at = update_at;
    }
    @DocumentId
    private String id;
    private DocumentReference product_ref;
    private Timestamp create_at;
    private Timestamp update_at;
    public WishItemModel(){}

}
