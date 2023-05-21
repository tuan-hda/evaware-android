package com.example.evaware.data.repo;

import androidx.lifecycle.LiveData;

import com.example.evaware.data.model.AddressModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
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
}
