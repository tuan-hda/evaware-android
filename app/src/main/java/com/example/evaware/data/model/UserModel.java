package com.example.evaware.data.model;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserModel {
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public String Uid;
    public DocumentReference userRef;
    public String email;
    public String rank;
    public Timestamp birthday;
    public String first_name;
    public String last_name;
    public boolean male;
    public String username;
    public String password;
    public String phone;
    public String role;

    public UserModel() {
        this.Uid = auth.getCurrentUser().getUid();
        this.userRef = db.collection("users").document(this.Uid);
    }

    @NonNull
    @Override
    public String toString() {
        return "UserModel: {" +
                "UID: " + this.Uid + '\'' +
                "userRef: " + this.userRef + '\'' +
                "email: " + this.email + '\'' +
                "rank: " + this.rank + '\'' +
                "birthday " + this.birthday + '\'' +
                "first_name: " + this.first_name + '\'' +
                "last_name: " + this.last_name + '\'' +
                "male: " + this.male + '\'' +
                "username: " + this.username + '\'' +
                "password: " + this.password + '\'' +
                "phone: " + this.phone + '\'' +
                "role: " + this.role + '\'' +
                "}";
    }
}
