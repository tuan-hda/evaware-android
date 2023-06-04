package com.example.evaware.data.repo;

import android.util.Log;

import com.example.evaware.data.model.UserModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class UserRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "UserRepository";
    private CollectionReference userRef;
    public UserRepository(){
        userRef = db.collection("users");
    }
    public Task<DocumentReference> createUser(UserModel user){
        return userRef.add(user).addOnFailureListener(e->{
            Log.e(TAG, "update: " + e.getLocalizedMessage());
        });
    }
    public Task<QuerySnapshot> getUserById(String id){
        return userRef.whereEqualTo("id", id).get();
    }
}
