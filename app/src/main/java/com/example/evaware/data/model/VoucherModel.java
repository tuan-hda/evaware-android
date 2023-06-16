package com.example.evaware.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class VoucherModel {
    public String id;
    public String name;
    public String code;
    public int percent;
    public int quantity;
    public Timestamp start_date;
    public Timestamp end_date;

    @ServerTimestamp
    public Timestamp created_at;
    @ServerTimestamp
    public Timestamp updated_at;
}
