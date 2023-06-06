package com.example.evaware.data.model;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentId;

//public class UserModel {
//    private final FirebaseAuth auth = FirebaseAuth.getInstance();
//    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//    public String Uid;
//    public DocumentReference userRef;
//    public String email;
//    public String rank;
//    public Timestamp birthday;
//    public String first_name;
//    public String last_name;
//    public boolean male;
//    public String username;
//    public String password;
//    public String phone;
//    public String role;
//
//    public UserModel() {
//        this.Uid = auth.getCurrentUser().getUid();
//        this.userRef = db.collection("users").document(this.Uid);
//    }
//
//    @NonNull
//    @Override
//    public String toString() {
//        return "UserModel: {" +
//                "UID: " + this.Uid + '\'' +
//                "userRef: " + this.userRef + '\'' +
//                "email: " + this.email + '\'' +
//                "rank: " + this.rank + '\'' +
//                "birthday " + this.birthday + '\'' +
//                "first_name: " + this.first_name + '\'' +
//                "last_name: " + this.last_name + '\'' +
//                "male: " + this.male + '\'' +
//                "username: " + this.username + '\'' +
//                "password: " + this.password + '\'' +
//                "phone: " + this.phone + '\'' +
//                "role: " + this.role + '\'' +
//                "}";
//    }

public class UserModel {
    public UserModel(){

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserModel(String id, String email, String name, String img_url) {
        this.id = id;
        this.email = email;
        this.img_url = img_url;
        this.name = name;
    }
    @DocumentId
    private String id;
    private String email;
    private String img_url;
    private String name;
}
