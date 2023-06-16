package com.example.evaware.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;

public class PaymentMethodModel implements Serializable {
    @DocumentId
    public String id;
    public String logo;
    public String account_no;
    public String name;
    public String provider;
    public String exp;
    public Boolean is_card;
//    public String img;
    @ServerTimestamp
    public transient Timestamp created_at;
    @ServerTimestamp
    public transient Timestamp updated_at;

    public PaymentMethodModel() {
    }

    public PaymentMethodModel(String logo, String accountNo, String name,
                              String provider, String exp, Boolean isCard) {
        this.logo = logo;
        this.account_no = accountNo;
        this.name = name;
        this.provider = provider;
        this.exp = exp;
        this.is_card = isCard;
    }
}
