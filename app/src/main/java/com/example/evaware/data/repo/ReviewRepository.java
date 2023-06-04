package com.example.evaware.data.repo;

import android.util.Log;

import com.example.evaware.data.model.ImageModel;
import com.example.evaware.data.model.ReviewModel;
import com.example.evaware.data.model.UserModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class ReviewRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "ReviewRepository";
    private CollectionReference reviewRef;
    public ReviewRepository(String productId){
        reviewRef = db.collection("products").document(productId).collection("reviews");
    }
    public Task<DocumentReference> postReview(ReviewModel reviewModel){
        return reviewRef.add(reviewModel).addOnFailureListener(e->{
            Log.e(TAG, "post review: " + e.getLocalizedMessage());
        });
    }
    public Task<Void> update (Map<String, Object> updates, String reviewId) {
        return reviewRef.document(reviewId).update(updates).addOnFailureListener(e -> {
            Log.e(TAG, "update review: " + e.getLocalizedMessage());
        });
    }
    public Task<DocumentReference> addImage(ImageModel imageModel, String reviewId){
        return reviewRef.document(reviewId).collection("images").add(imageModel);

    }
    public Task<QuerySnapshot> getAllReviewByProduct (String productId){
        return reviewRef.get();
    }
    public Task<QuerySnapshot> getImagesByReview(String reviewId){
        return reviewRef.document(reviewId).collection("images").get();
    }


}