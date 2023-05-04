package com.example.evaware.data.model;

import com.google.firebase.firestore.DocumentId;

public class PaymentMethodModel {
    @DocumentId
    public String id;
    public String logo;
    public String accountNo;
    public String name;
    public String provider;
    public String exp;
    public Boolean isCard;

    public PaymentMethodModel() {
    }

    public PaymentMethodModel(String id, String logo, String accountNo, String name,
                              String provider, String exp, Boolean isCard) {
        this.id = id;
        this.logo = logo;
        this.accountNo = accountNo;
        this.name = name;
        this.provider = provider;
        this.exp = exp;
        this.isCard = isCard;
    }
}
