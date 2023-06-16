package com.example.evaware.data.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.Date;
import java.util.List;

public class OrderModel {
    public String id;
    public Date created_at;
    public Date updated_at;
    public Date delivered_date;
    public Date shipping_date;
    public String address_id;
    public int status;
    public int total;
    public String voucher_id;
    public List<String> order_items;

    public OrderModel(Date created_at, String address_id, int total, String voucher_id, List<String> order_items) {
        this.created_at = created_at;
        this.address_id = address_id;
        this.total = total;
        this.voucher_id = voucher_id;
        this.order_items = order_items;
    }
}
