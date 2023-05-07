package com.example.evaware.data.model;

import com.google.firebase.firestore.DocumentId;

public class AddressModel {
    @DocumentId
    public String id;
    public String name;
    public String phone;
    public String email;
    public String city;
    public String district;
    public String ward;
    public String street;

    public AddressModel(String id) {
        this.id = id;
    }

    public AddressModel(String id, String fullName, String phone, String email, String city,
                        String district, String ward, String street) {
        this.id = id;
        this.name = fullName;
        this.phone = phone;
        this.email = email;
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.street = street;
    }
}
