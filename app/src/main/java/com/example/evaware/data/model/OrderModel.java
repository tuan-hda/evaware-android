package com.example.evaware.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OrderModel implements Serializable {
    @DocumentId
    public String id;
    public Date createdAt;
    public Date updatedAt;

    @ServerTimestamp
    public Timestamp created_at;
    @ServerTimestamp
    public Timestamp updated_at;

    public Date delivered_date;
    public Date shipping_date;
    public String address_id;
    public DocumentReference user_ref;

    public String name;
    public String phone;
    public String email;
    public String province;
    public int province_id;
    public String district;
    public int district_id;
    public String ward;
    public int ward_id;
    public String street;
    public String payment_method;

    public int status;
    public Double total;
    public String voucher_id;
    public String voucher_code;
    public int voucher_percent;

    public List<BagItemModel> order_items;

    public OrderModel() {
    }

    public OrderModel(Date created_at, String address_id, Double total, String voucher_id, List<BagItemModel> order_items) {
        this.createdAt = created_at;
        this.address_id = address_id;
        this.total = total;
        this.voucher_id = voucher_id;
        this.order_items = order_items;
    }

    public OrderModel(DocumentReference user_ref, String name, String phone, String email, String province, int province_id, String district, int district_id, String ward, int ward_id, String street, String payment_method, int status, Double total) {
        this.user_ref = user_ref;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.province = province;
        this.province_id = province_id;
        this.district = district;
        this.district_id = district_id;
        this.ward = ward;
        this.ward_id = ward_id;
        this.street = street;
        this.payment_method = payment_method;
        this.status = status;
        this.total = total;
    }

    public String getAddress(){
        return this.street + ", " +
                this.ward + ", " +
                this.district + ", " +
                this.province;
    }
}
