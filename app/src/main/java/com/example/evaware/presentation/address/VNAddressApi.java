package com.example.evaware.presentation.address;

import com.example.evaware.data.model.District;
import com.example.evaware.data.model.Province;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VNAddressApi {
    @GET("p")
    Call<List<Province>> getProvinces();

    @GET("p/{code}?depth=2")
    Call<Province> getProvinceDetail(@Path("code") int code);

    @GET("d/{code}?depth=2")
    Call<District> getDistrictDetail(@Path("code") int code);
}
