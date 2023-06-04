package com.example.evaware.data.model;

import com.google.firebase.firestore.DocumentReference;

public class ProductRecommendation {
    private DocumentReference product_ref;

    public ProductRecommendation(DocumentReference product_ref) {
        this.product_ref = product_ref;
    }

    public DocumentReference getProduct_ref() {
        return product_ref;
    }

    public void setProduct_ref(DocumentReference product_ref) {
        this.product_ref = product_ref;
    }

    public ProductRecommendation() {
    }
}
