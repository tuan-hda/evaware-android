package com.example.evaware.data.repo;

import android.util.Log;

import com.example.evaware.data.model.WishItemModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class WishlistRepository {
    private static final String TAG = "CartIRepository";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference wishRef;

    public WishlistRepository() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        wishRef = db.collection("users").document(userId).collection("wishlist");
    }

    public Task<DocumentReference> addToWishList(WishItemModel wishItemModel) {
        return wishRef.add(wishItemModel).addOnFailureListener(e -> {
            Log.e(TAG, "addWishItem:failed", e);
        });
    }

    public Task<QuerySnapshot> findWishItemByProductRef(DocumentReference documentReference) {
        return wishRef.whereEqualTo("product_ref", documentReference).get().addOnFailureListener(e -> {
            Log.e(TAG, "findWishItemByProductRef:failed", e);
        });
    }

    public Task<QuerySnapshot> getAllWish(){
        return wishRef.get();
    }
    public Task<Void> removeWish(String wishId){
        return wishRef.document(wishId).delete();
    }
}
