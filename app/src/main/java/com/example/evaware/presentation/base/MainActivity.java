package com.example.evaware.presentation.base;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import androidx.core.splashscreen.SplashScreen;
import androidx.navigation.ui.AppBarConfiguration;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private com.example.evaware.databinding.ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        binding = com.example.evaware.databinding.ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}