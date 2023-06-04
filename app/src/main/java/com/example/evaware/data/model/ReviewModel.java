package com.example.evaware.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

public class ReviewModel {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getRating_star() {
        return rating_star;
    }

    public void setRating_star(float rating_star) {
        this.rating_star = rating_star;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Timestamp getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Timestamp create_at) {
        this.create_at = create_at;
    }

    public DocumentReference getUser_ref() {
        return user_ref;
    }

    public void setUser_ref(DocumentReference user_ref) {
        this.user_ref = user_ref;
    }

    public ReviewModel(String id, float rating_star, String content, Timestamp updated_at, Timestamp create_at, DocumentReference user_ref) {
        this.id = id;
        this.rating_star = rating_star;
        this.content = content;
        this.updated_at = updated_at;
        this.create_at = create_at;
        this.user_ref = user_ref;
    }

    public ReviewModel() {

    }
    @DocumentId
    private String id;
    private float rating_star;
    private String content;
    @ServerTimestamp
    private Timestamp updated_at;
    @ServerTimestamp
    private Timestamp create_at;
    public DocumentReference user_ref;
    @Exclude
    private UserModel user;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
