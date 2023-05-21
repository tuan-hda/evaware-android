package com.example.evaware.presentation.address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.example.evaware.R;
import com.example.evaware.data.model.Province;
import com.example.evaware.databinding.ActivityChooseProvinceBinding;
import com.example.evaware.utils.LoadingDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChooseProvinceActivity extends AppCompatActivity {
    private ActivityChooseProvinceBinding binding;
    private static final String TAG = "ChooseProvinceActivity";
    private VNAddressApi api;
    private Call<List<Province>> call;
    private LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChooseProvinceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.appbar.appbarTitle.setText("Choose province");
        binding.appbar.btnBack.setOnClickListener(v -> {
            finish();
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://provinces.open-api.vn/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(VNAddressApi.class);

        dialog = new LoadingDialog(this);

        setUpSearch();
        fetchData();
        setUpRecycler();
    }

    private void setUpSearch() {

    }

    private void fetchData() {
        dialog.showDialog();

        call = api.getProvinces();
        call.enqueue(new Callback<List<Province>>() {
            @Override
            public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "onResponse:notSuccessful " + response.code());
                } else {
                    List<Province> data = response.body();
                    List<String> list = new ArrayList<>();
                    assert data != null;
                    for (Province province: data) {
                        list.add(province.getName());
                    }
                    ProvinceAdapter adapter = new ProvinceAdapter(ChooseProvinceActivity.this, list);
                    binding.listProvince.setAdapter(adapter);
                    binding.listProvince.setLayoutManager(new LinearLayoutManager(ChooseProvinceActivity.this));
                }

                dialog.dismissDialog();
            }

            @Override
            public void onFailure(Call<List<Province>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void setUpRecycler() {

    }
}