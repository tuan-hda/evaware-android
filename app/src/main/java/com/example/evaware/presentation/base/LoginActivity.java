package com.example.evaware.presentation.base;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.evaware.R;
import com.example.evaware.databinding.ActivityLoginBinding;


public class LoginActivity extends FragmentActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}
