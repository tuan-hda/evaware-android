package com.example.evaware.data.repo;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ProductRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "ProductRepo";
    private CollectionReference productRef;

    public ProductRepository() {
        productRef = db.collection("products");
    }

    public DocumentReference getProductById(String id) {
        return productRef.document(id);
    }

    public Task<QuerySnapshot> getAllProductsByCategory(DocumentReference category) {
        return productRef.whereEqualTo("category_ref", category).get();
    }

    public Task<QuerySnapshot> getVariationOfProduct(String id) {
        return productRef.document(id).collection("variations").get();
    }

    public Task<QuerySnapshot> getImgOfVariation(String productId, String variationId) {
        return productRef
                .document(productId).collection("variations")
                .document(variationId).collection("images").get();
    }

}
