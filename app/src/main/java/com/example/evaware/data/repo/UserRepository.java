package com.example.evaware.data.repo;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

//public class UserRepository {
//    private final FirebaseAuth auth = FirebaseAuth.getInstance();
//    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
//    private static final String TAG = "UserDataSource";
//
//    public String Uid;
//    public DocumentReference userRef;
//
//    public UserRepository() {
//        this.Uid = auth.getCurrentUser().getUid();
//        this.userRef = db.collection("users").document(this.Uid);
//    }
////    Get user info
//    public Task<DocumentSnapshot> getUserInfo(){ return userRef.get();}
//
////    Update
//    public Task<Void> updateUserStringField(String fieldName, String value){
//        return userRef.update(fieldName, value).addOnFailureListener(e -> {
//            Log.e(TAG, "updateUser:failure " + e.getLocalizedMessage());
//        });
//    }
//    public Task<Void> updateUserTimestampField(String fieldName, Timestamp value){
//        return userRef.update(fieldName, value).addOnFailureListener(e -> {
//            Log.e(TAG, "updateUser:failure " + e.getLocalizedMessage());
//        });
//    }
//    public Task<Void> updateUserBooleanField(String fieldName, boolean value){
//        return userRef.update(fieldName, value).addOnFailureListener(e -> {
//            Log.e(TAG, "updateUser:failure " + e.getLocalizedMessage());
//        });
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
