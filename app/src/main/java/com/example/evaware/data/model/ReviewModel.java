package com.example.evaware.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.ServerTimestamp;

public class ReviewModel {
    private String id;
    private float rating_star;
    private String content;
    @ServerTimestamp
    private Timestamp updated_at;
    @ServerTimestamp
    private Timestamp create_at;
    public DocumentReference user_ref;
}
