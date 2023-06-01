package com.example.evaware;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import com.example.evaware.databinding.ActivityReviewsBinding;
import com.example.evaware.presentation.new_review.NewReviewActivity;

public class ReviewsActivity extends AppCompatActivity {
    private ActivityReviewsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvNewReviews.setOnClickListener(view -> {
            Intent intent = new Intent(this, NewReviewActivity.class);
            startActivity(intent);
        });
    }
}