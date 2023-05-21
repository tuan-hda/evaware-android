package com.example.evaware.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;

public class PaymentMethodModel {
    @DocumentId
    public String id;
    public String logo;
    public String account_no;
    public String name;
    public String provider;
    public String exp;
    public Boolean isCard;
    public String img;
    @ServerTimestamp
    public Timestamp created_at;
    @ServerTimestamp
    public Timestamp updated_at;

    public PaymentMethodModel() {
    }

    public PaymentMethodModel(String id, String logo, String accountNo, String name,
                              String provider, String exp, Boolean isCard) {
        this.id = id;
        this.logo = logo;
        this.account_no = accountNo;
        this.name = name;
        this.provider = provider;
        this.exp = exp;
        this.isCard = isCard;
    }
}
