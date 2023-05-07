package com.example.evaware.presentation.checkout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.evaware.databinding.ActivitySuccessBinding;

public class SuccessActivity extends AppCompatActivity {
    ActivitySuccessBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySuccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}