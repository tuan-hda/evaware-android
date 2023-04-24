package com.example.evaware.data.repo;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class BagRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "BagDataSource";
    private CollectionReference bags;

    public BagRepository(String userId) {
        bags = db.collection("users").document(userId).collection("cart");
    }

    public Task<QuerySnapshot> getBagList() {
        return bags.get();
    }
}
