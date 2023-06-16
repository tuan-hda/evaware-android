package com.example.evaware.data.repo;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.evaware.data.model.BagItemModel;
import com.example.evaware.data.model.PaymentMethodModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class PaymentRepository {
    private static final String TAG = "PaymentRepository";
    private CollectionReference colRef;

    public PaymentRepository() {
        colRef = FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getUid()).collection("payment");
    }

    public Task<QuerySnapshot> getData() {
        return colRef.get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: " + e.getLocalizedMessage());
            }
        });
    }

    public Task<DocumentReference> add(PaymentMethodModel itemModel) {
        return colRef.add(itemModel).addOnFailureListener(e -> {
            Log.e(TAG, "add:failure " + e.getLocalizedMessage());
        });
    }

    public Task<Void> update(PaymentMethodModel itemModel) {
        return colRef.document(itemModel.id).set(itemModel).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: " + e.getLocalizedMessage());
            }
        });
    }
}
