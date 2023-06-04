package com.example.evaware.data.repo;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class UserRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "UserRepository";
    private CollectionReference userRef;
    public UserRepository(){
        userRef = db.collection("users");
    }
    public Task<Void> createUser(HashMap<String, Object> user, String userId){
        return userRef.document(userId).set(user).addOnFailureListener(e->{
            Log.e(TAG, "update: " + e.getLocalizedMessage());
        });
    }
    public Task<DocumentSnapshot> getUserById(String id){
        return userRef.document(id).get();
    }
}
