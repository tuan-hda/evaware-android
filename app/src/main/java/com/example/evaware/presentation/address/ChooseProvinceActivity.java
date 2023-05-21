package com.example.evaware.presentation.address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.example.evaware.data.model.AddressBase;
import com.example.evaware.data.model.District;
import com.example.evaware.data.model.Province;
import com.example.evaware.data.model.Ward;
import com.example.evaware.databinding.ActivityChooseProvinceBinding;
import com.example.evaware.utils.LoadingDialog;
import com.example.evaware.utils.RemoveAccent;

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
    private Call<List<Province>> callPro;
    private Call<Province> callDis;
    private Call<District> callWar;
    private LoadingDialog dialog;
    private ProvinceAdapter adapter;
    private String type;
    private AddressBase val, dep;

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

        handleIntent();
        setUpSearch();
        fetchData();
        setUpRecycler();
    }

    private void handleIntent() {
        type = getIntent().getStringExtra("type");
        val = (AddressBase) getIntent().getSerializableExtra("value");
        dep = (AddressBase) getIntent().getSerializableExtra("dep");
    }

    private void setUpSearch() {
        binding.search.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.search.btnClear.setOnClickListener(v -> {
            if (binding.search.edtSearch.getText().toString().length() != 0) {
                binding.search.edtSearch.setText("");
            }
        });
    }

    private void fetchData() {
        dialog.showDialog();

        switch (type) {
            case "province":
                fetchProvince();
                break;
            case "district":
                fetchDistrict();
                break;
            default:
                fetchWard();
        }
    }

    private void fetchWard() {
        callWar = api.getDistrictDetail(dep.getCode());
        callWar.enqueue(new Callback<District>() {
            @Override
            public void onResponse(Call<District> call, Response<District> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "onResponse:notSuccessful " + response.code());
                } else {
                    District data = response.body();
                    List<AddressBase> list = new ArrayList<>();
                    assert data != null;
                    for (Ward item : data.getWardList()) {
                        list.add(new AddressBase(RemoveAccent.removeAccent(item.getName()), item.getCode()));
                    }
                    adapter = new ProvinceAdapter(ChooseProvinceActivity.this, list, val);
                    binding.listProvince.setAdapter(adapter);
                }

                dialog.dismissDialog();
            }

            @Override
            public void onFailure(Call<District> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void fetchDistrict() {
        callDis = api.getProvinceDetail(dep.getCode());
        callDis.enqueue(new Callback<Province>() {
            @Override
            public void onResponse(Call<Province> call, Response<Province> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "onResponse:notSuccessful " + response.code());
                } else {
                    Province data = response.body();
                    List<AddressBase> list = new ArrayList<>();
                    assert data != null;
                    for (District item : data.getDistrictList()) {
                        list.add(new AddressBase(RemoveAccent.removeAccent(item.getName()), item.getCode()));
                    }
                    adapter = new ProvinceAdapter(ChooseProvinceActivity.this, list, val);
                    binding.listProvince.setAdapter(adapter);
                }

                dialog.dismissDialog();
            }

            @Override
            public void onFailure(Call<Province> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void fetchProvince() {
        callPro = api.getProvinces();
        callPro.enqueue(new Callback<List<Province>>() {
            @Override
            public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "onResponse:notSuccessful " + response.code());
                } else {
                    List<Province> data = response.body();
                    List<AddressBase> list = new ArrayList<>();
                    assert data != null;
                    for (Province item : data) {
                        list.add(new AddressBase(RemoveAccent.removeAccent(item.getName()), item.getCode()));
                    }
                    adapter = new ProvinceAdapter(ChooseProvinceActivity.this, list, val);
                    binding.listProvince.setAdapter(adapter);
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
        adapter = new ProvinceAdapter(this, new ArrayList<>());
        binding.listProvince.setAdapter(adapter);
        binding.listProvince.setLayoutManager(new LinearLayoutManager(this));
    }


}