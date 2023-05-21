package com.example.evaware.data.repo;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class PaymentRepository {
    private static final String TAG = "PaymentRepository";
    private CollectionReference colRef;

    public PaymentRepository() {
        colRef = FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getUid()).collection("payment");
    }

    public Task<QuerySnapshot> getData() {
        return colRef.get();
    }
}
