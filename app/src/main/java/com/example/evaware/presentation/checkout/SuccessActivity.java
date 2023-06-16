package com.example.evaware.presentation.checkout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.evaware.databinding.ActivitySuccessBinding;
import com.example.evaware.presentation.base.MainActivity;

public class SuccessActivity extends AppCompatActivity {
    ActivitySuccessBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySuccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnToOrders.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("fragment", "orders");
            startActivity(intent);
        });
    }
}