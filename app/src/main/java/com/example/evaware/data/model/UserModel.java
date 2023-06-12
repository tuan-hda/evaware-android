package com.example.evaware.data.model;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentId;

import java.util.Date;

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

    @NonNull
    @Override
    public String toString() {
        return "UserModel: {" +
                "UID: " + this.id + '\'' +
                "email: " + this.email + '\'' +
                "name: " + this.name + '\'' +
                "rank: " + this.rank + '\'' +
                "birthday " + this.dob + '\'' +
                "male: " + this.male + '\'' +
                "username: " + this.username + '\'' +
                "password: " + this.password + '\'' +
                "phone: " + this.phone + '\'' +
                "role: " + this.role + '\'' +
                "}";
    }

    @DocumentId
    public String id;
    public String email;
    public String img_url;
    public String name;
    public String rank;
    public Date dob;
    public boolean male;
    public String username;
    public String password;
    public String phone;
    public String role;
}
