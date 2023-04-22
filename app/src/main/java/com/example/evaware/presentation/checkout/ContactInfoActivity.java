package com.example.evaware.presentation.checkout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.evaware.databinding.ActivityContactInfoBinding;

public class ContactInfoActivity extends AppCompatActivity {
    ActivityContactInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityContactInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setUpAppBar();
        setUpContinueBtn();
    }

    private void setUpAppBar() {
        binding.btnBack.setOnClickListener(view -> {
            finish();
        });
    }

    private void setUpContinueBtn() {
        binding.btnContinue.setOnClickListener(view -> {
            Intent intent = new Intent(ContactInfoActivity.this, DeliveryAddressActivity.class);
            startActivity(intent);
        });
    }
}