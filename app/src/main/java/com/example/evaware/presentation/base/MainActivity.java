package com.example.evaware.presentation.base;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.evaware.R;
import com.example.evaware.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class MainActivity extends FragmentActivity {
    private static final String TAG = "MainActivity";
    private  ActivityMainBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupBottomNavigation();
        handleIntent();
    }

    private void handleIntent() {
        String fragment = getIntent().getStringExtra("fragment");
        Log.e(TAG, "handleIntent: " + fragment);
        if (fragment != null && fragment == "bag") {
            NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.nav_graph);
            navGraph.setStartDestination(R.id.bagFragment);
            navController.setGraph(navGraph);
        }
        if (fragment != null && fragment == "orders") {
            NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.nav_graph);
            navGraph.setStartDestination(R.id.userFragment);
            navController.setGraph(navGraph);
        }
    }

    private void setupBottomNavigation() {
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = Objects.requireNonNull(navHostFragment).getNavController();

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
    }
}