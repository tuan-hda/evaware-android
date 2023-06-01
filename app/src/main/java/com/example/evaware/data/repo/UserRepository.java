package com.example.evaware.data.repo;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "DeliveryRepository";
    private CollectionReference userRef;


}
