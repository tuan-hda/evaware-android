package com.example.evaware.presentation.address;

import com.example.evaware.data.model.Province;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface VNAddressApi {
    @GET("p")
    Call<List<Province>> getProvinces();
}
