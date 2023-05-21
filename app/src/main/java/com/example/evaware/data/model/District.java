package com.example.evaware.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class District {
    private String name;
    private int code;
    @SerializedName("wards")
    private List<Ward> wardList;

    public District() {
    }

    public District(String name, int code, List<Ward> wardList) {
        this.name = name;
        this.code = code;
        this.wardList = wardList;
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

    public List<Ward> getWardList() {
        return wardList;
    }

    public void setWardList(List<Ward> wardList) {
        this.wardList = wardList;
    }
}
