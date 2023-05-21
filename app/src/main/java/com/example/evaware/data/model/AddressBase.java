package com.example.evaware.data.model;

import java.io.Serializable;

public class AddressBase implements Serializable {
    protected String name;
    protected int code;

    public AddressBase(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public AddressBase() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
