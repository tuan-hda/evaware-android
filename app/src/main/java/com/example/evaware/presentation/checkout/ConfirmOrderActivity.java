package com.example.evaware.presentation.checkout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.evaware.databinding.ActivityConfirmOrderBinding;

public class ConfirmOrderActivity extends AppCompatActivity {
    ActivityConfirmOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityConfirmOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}