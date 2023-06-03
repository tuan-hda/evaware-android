package com.example.evaware;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.example.evaware.databinding.ActivityReviewsBinding;
import com.example.evaware.presentation.new_review.NewReviewActivity;

public class ReviewsActivity extends AppCompatActivity {
    private ActivityReviewsBinding binding;
    private String productId;
    private String productName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        binding.tvNewReviews.setOnClickListener(view -> {
            Intent intent = new Intent(this, NewReviewActivity.class);
            intent.putExtra("productId", productId);
            intent.putExtra("productName", productName);
            startActivity(intent);
        });
    }
    public void init(){
        Intent intent = getIntent();
        productId = intent.getStringExtra("productId");
    }
}