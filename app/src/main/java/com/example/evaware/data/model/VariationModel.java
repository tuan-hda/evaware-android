package com.example.evaware.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;

public class VariationModel {
    @DocumentId
    public String id;
    @ServerTimestamp
    public Timestamp created_at;
    @ServerTimestamp
    public Timestamp updated_at;
    public String image;
    public int inventory;
    public String type;
}
