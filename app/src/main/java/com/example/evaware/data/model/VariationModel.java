package com.example.evaware.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.List;

public class VariationModel {
    @DocumentId
    public String id;
    @ServerTimestamp
    public Timestamp created_at;
    @ServerTimestamp
    public Timestamp updated_at;

    @Exclude
    public List<ImageModel> images;
    public int inventory;
    public String name;
}
