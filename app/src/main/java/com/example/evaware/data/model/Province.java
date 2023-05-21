package com.example.evaware.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Province {
    private String name;
    private int code;

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

    @SerializedName("districts")
    private List<District> districtList;

    public Province() {
    }

    public Province(String name, int code, List<District> districtList) {
        this.name = name;
        this.code = code;
        this.districtList = districtList;
    }

    public List<District> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<District> districtList) {
        this.districtList = districtList;
    }


}
