package com.example.evaware.data.repo;

import android.util.Log;

import com.example.evaware.data.model.AddressModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class AddressRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "DeliveryRepository";
    private CollectionReference addrRef;

    public AddressRepository() {
        addrRef = db.collection("users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).collection("addresses");
    }

    public Task<QuerySnapshot> getData() {
        return addrRef.get();
    }

    public Task<DocumentReference> add(AddressModel item) {
        return addrRef.add(item).addOnFailureListener(e -> {
            Log.e(TAG, "update: " + e.getLocalizedMessage());
        });
    }

    public Task<Void> update(AddressModel item) {
        return db.document(item.path).set(item).addOnFailureListener(e -> {
            Log.e(TAG, "update: " + e.getLocalizedMessage());
        });
    }

    public Task<Void> delete(AddressModel item) {
        return db.document(item.path).delete().addOnFailureListener(e -> {
            Log.e(TAG, "delete: " + e.getLocalizedMessage());
        });
    }
}
