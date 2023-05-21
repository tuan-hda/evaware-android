package com.example.evaware.presentation.address;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.evaware.R;
import com.example.evaware.databinding.ActivityAddAddressBinding;

public class AddAddressActivity extends AppCompatActivity {
    private ActivityAddAddressBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.appbar.appbarTitle.setText("Add address");
        binding.appbar.btnBack.setOnClickListener(v -> {
            finish();
        });

        setUpButtons();
    }

    private void setUpButtons() {
        binding.province.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChooseProvinceActivity.class);
            startActivity(intent);
        });
    }
}