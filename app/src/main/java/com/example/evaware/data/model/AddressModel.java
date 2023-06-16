package com.example.evaware.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;

public class AddressModel implements Serializable {
    @DocumentId
    public String id;
    public String path;
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
    @ServerTimestamp
    public transient Timestamp created_at;
    @ServerTimestamp
    public transient Timestamp updated_at;

    public AddressModel() {
    }

    public AddressModel(String fullName, String phone, String email, String province, int province_id,
                        String district, int district_id, String ward, int ward_id, String street) {
        this.name = fullName;
        this.phone = phone;
        this.email = email;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.street = street;
        this.province_id = province_id;
        this.district_id = district_id;
        this.ward_id = ward_id;
    }
}
