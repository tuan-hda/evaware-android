package com.example.evaware.presentation.review;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;


import com.example.evaware.data.model.ReviewDetail;
import com.example.evaware.data.model.ReviewModel;
import com.example.evaware.databinding.ActivityReviewsBinding;
import com.example.evaware.presentation.new_review.NewReviewActivity;
import com.example.evaware.presentation.new_review.ReviewViewModel;
import com.example.evaware.utils.ConvertTimestamp;
import com.example.evaware.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ReviewsActivity extends AppCompatActivity {
    private ActivityReviewsBinding binding;
    private String productId;
    private String productName;
    private ReviewViewModel reviewViewModel;
    private LoadingDialog loadingDialog;
    private int reviewQty;

    private List<ReviewDetail> reviewDetails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        setUpButton();
        swipeRefresh();
    }

    private void swipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener(() -> {
            loadData(true);
            binding.swipeRefresh.setRefreshing(false);
        });
    }

    private void setUpButton() {
        binding.tvNewReviews.setOnClickListener(view -> {
            Intent intent = new Intent(this, NewReviewActivity.class);
            intent.putExtra("productId", productId);
            intent.putExtra("productName", productName);
            intent.putExtra("reviewQty", String.valueOf(reviewQty));
            startActivity(intent);
        });
        binding.appbar.btnBack.setOnClickListener(view -> {
            finish();
        });
    }

    public void init() {
        Intent intent = getIntent();
        productId = intent.getStringExtra("productId");
        productName = intent.getStringExtra("productName");

        reviewViewModel = new ViewModelProvider(this).get(ReviewViewModel.class);
        reviewViewModel.setReviewRef(productId);

        binding.appbar.appbarTitle.setText("Reviews");

        loadingDialog = new LoadingDialog(this);
        loadData(false);
    }

    private void loadData(Boolean withoutDialogLoading) {
        reviewViewModel.getAllReviewByProductId(productId).observe(this, reviewModels -> {
            if (!withoutDialogLoading) {
                loadingDialog.showDialog();
            }

            List<ReviewDetail> newReviewDetails = new ArrayList<>(); // Create a new list

            int totalReviewModels = reviewModels.size();
            reviewQty = totalReviewModels;
            AtomicInteger processedReviewModels = new AtomicInteger(0);

            for (ReviewModel reviewModel : reviewModels) {
                reviewViewModel.getImagesByReview(reviewModel.getId()).observe(this, imageModels -> {
                    ReviewDetail reviewDetail = new ReviewDetail(imageModels, reviewModel);
                    reviewDetail.setDayCreate(ConvertTimestamp.convertToRelativeTimeSpanString(reviewDetail.getReview().getUpdated_at()));
                    newReviewDetails.add(reviewDetail); // Add to the new list

                    int processedCount = processedReviewModels.incrementAndGet();
                    if (processedCount == totalReviewModels) {
                        reviewDetails = newReviewDetails; // Update the reference to the new list

                        ReviewAdapter reviewAdapter = new ReviewAdapter(this, reviewDetails);
                        binding.rvReviews.setLayoutManager(new LinearLayoutManager(this));
                        binding.rvReviews.setAdapter(reviewAdapter);

                        if (!withoutDialogLoading)
                            loadingDialog.dismissDialog();
                    }
                });
            }
        });
    }

}