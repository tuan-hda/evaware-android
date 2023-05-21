package com.example.evaware.data.repo;

import android.util.Log;

import com.example.evaware.data.model.BagItemModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class BagRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "BagDataSource";
    private CollectionReference barRef;

    public BagRepository() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        barRef = db.collection("users").document(userId).collection("cart");
    }

    public Task<QuerySnapshot> getBagList() {
        return barRef.get();
    }

    public Task<Void> updateBagItem(DocumentReference ref, BagItemModel item) {
        return ref.set(item).addOnFailureListener(e -> {
            Log.e(TAG, "updateBag:failure " + e.getLocalizedMessage());
        });
    }

    public Task<Void> removeBagItem(DocumentReference ref) {
        return ref.delete().addOnFailureListener(e -> {
            Log.e(TAG, "removeBagItem:failure " + e.getLocalizedMessage());
        });
    }

    public Task<DocumentReference> add(BagItemModel itemModel) {
        return barRef.add(itemModel).addOnFailureListener(e -> {
            Log.e(TAG, "add:failure " + e.getLocalizedMessage());
        });
    }
}
