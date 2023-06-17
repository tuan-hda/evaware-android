package com.example.evaware.data.repo;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ProductRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "ProductRepo";
    private CollectionReference productRef;

    public ProductRepository() {
        productRef = db.collection("products");
    }
    public Task<QuerySnapshot> getAllProduct(){
        return productRef.get().addOnFailureListener(e -> Log.e(TAG, "getAllProduct: ", e ));
    }

    public Query getAllProductAlt(){
        return productRef;
    }

    public DocumentReference getProductById(String id) {
        return productRef.document(id);
    }

    public Task<QuerySnapshot> getAllProductsByCategory(DocumentReference category) {
        return productRef.whereEqualTo("category_ref", category).get();
    }

    public Query getAllProductsByCategoryAlt(DocumentReference category) {
        return productRef.whereEqualTo("category_ref", category);
    }

    public Task<QuerySnapshot> getVariationOfProduct(String id) {
        return productRef.document(id).collection("variations").get();
    }
    public Task<DocumentSnapshot> getVariationRefById(String productId, String variationId){
        return productRef.document(productId).collection("variations").document(variationId).get();
    }

    public Task<QuerySnapshot> getImgOfVariation(String productId, String variationId) {
        return productRef
                .document(productId).collection("variations")
                .document(variationId).collection("images").get();
    }

    public Task<Void> updateReviewQty(String productId, int reviewQty) {
        DocumentReference productDocRef = productRef.document(productId);

        Map<String, Object> updates = new HashMap<>();
        updates.put("review_qty", reviewQty);

        return productDocRef.update(updates).addOnFailureListener(e -> {
                    Log.e(TAG, "add:failure " + e.getLocalizedMessage());
                }
        );
    }

    public Task<DocumentSnapshot> getMeasurements(String productId) {
        return productRef.document(productId).collection("product_info")
                .document("measurements").get();
    }
    public Task<DocumentSnapshot> getComposition(String productId) {
        return productRef.document(productId).collection("product_info")
                .document("composition").get();
    }
    public Task<QuerySnapshot> getRecommendations(String productId){
        return productRef.document(productId).collection("recommendations").get();
    }

    public Task<Void> updateQuantity(DocumentReference variationRef, int qty){
        Map<String, Object> updates = new HashMap<>();
        updates.put("inventory", qty);
        return variationRef.update(updates).addOnFailureListener(e -> {
                    Log.e(TAG, "updateQuantity:failure " + e.getLocalizedMessage());
                }
        );
    }

}
