package com.example.evaware.data.model;

import com.google.firebase.firestore.DocumentId;

public class AddressModel {
    @DocumentId
    public String id;
    public String fullName;
    public String phone;
    public String email;
    public String province;
    public String district;
    public String ward;
    public String street;

    public AddressModel(String id) {
        this.id = id;
    }

    public AddressModel(String id, String fullName, String phone, String email, String province,
                        String district, String ward, String street) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.street = street;
    }
}
