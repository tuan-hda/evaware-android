package com.example.evaware.presentation.new_review;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evaware.data.model.ReviewModel;
import com.example.evaware.databinding.ActivityNewReviewBinding;
import com.example.evaware.presentation.auth.AuthUserViewModel;
import com.example.evaware.presentation.product.ProductViewModel;
import com.example.evaware.utils.LoadingDialog;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class NewReviewActivity extends AppCompatActivity {

    private ActivityNewReviewBinding binding;
    private ActivityResultLauncher<Intent> galleryLauncher;
    private RecyclerView imgFromGallery;
    private ArrayList<Uri> selectedImagesList;
    private ImageAdapter imageAdapter;
    private MaterialRatingBar ratingBar;
    private TextView tvLevelRate;
    private ReviewViewModel reviewViewModel;
    private AuthUserViewModel authUserViewModel;
    private String productId;
    private String productName;
    private LoadingDialog loadingDialog;
    private DocumentReference userRef;
    private int reviewQty;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        loadingUserRef();
        setUpButtons();
        setUpRatingBar();
    }

    private void loadingUserRef() {
        authUserViewModel.getUserRefById().observe(this, docRef -> {
            userRef = docRef;
        });
    }

    private void init() {

        binding = ActivityNewReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadingDialog = new LoadingDialog(this);

        Intent intent = getIntent();
        productId = intent.getStringExtra("productId");
        productName = intent.getStringExtra("productName");
        String reviewQtyString = intent.getStringExtra("reviewQty");
        reviewQty = Integer.parseInt(reviewQtyString);

        reviewViewModel = new ViewModelProvider(this).get(ReviewViewModel.class);
        reviewViewModel.setReviewRef(productId);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        authUserViewModel = new ViewModelProvider(this).get(AuthUserViewModel.class);

        imgFromGallery = binding.rvImgList;
        ratingBar = binding.ratingbar;
        tvLevelRate = binding.tvLevelRate;
        selectedImagesList = new ArrayList<>();

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            if (data.getClipData() != null) {
                                // Multiple images selected
                                ClipData clipData = data.getClipData();
                                selectedImagesList = new ArrayList<>();
                                for (int i = 0; i < clipData.getItemCount(); i++) {
                                    Uri selectedImage = clipData.getItemAt(i).getUri();
                                    selectedImagesList.add(selectedImage);
                                }
                            } else if (data.getData() != null) {
                                // Single image selected
                                Uri selectedImage = data.getData();
                                selectedImagesList = new ArrayList<>();
                                selectedImagesList.add(selectedImage);
                            }
                            setUpRecyclerView();
                        }
                    }
                });
    }

    private void setUpButtons() {
        binding.btnClose.setOnClickListener(view -> finish());
        binding.cvOpenGallery.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            galleryLauncher.launch(intent);
        });
        binding.btnSendReview.setOnClickListener(view -> {
            loadingDialog.showDialog();

            if (binding.edtNewReview.getText().toString().equals("")) {
                binding.edtNewReview.setError("Please enter your review");
            }
            try {
                AtomicReference<Integer> count = new AtomicReference<>(0);
                ReviewModel review = new ReviewModel();
                review.setUser_ref(userRef);
                review.setContent(binding.edtNewReview.getText().toString());
                review.setUpdated_at(new Timestamp(new Date()));
                review.setCreate_at(new Timestamp(new Date()));
                review.setRating_star(binding.ratingbar.getRating());
                reviewViewModel.postReview(review, selectedImagesList, productName);
            } catch (Exception e) {
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                loadingDialog.dismissDialog();
            }
            productViewModel.updateReviewQty(productId, reviewQty + 1);
            finish();
        });
    }


    private void setUpRatingBar() {
        ratingBar.setOnRatingChangeListener((ratingBar, rating) -> {
            // Update the text based on the rating
            String levelText;
            switch ((int) rating) {
                case 1:
                    levelText = "Terrible";
                    break;
                case 2:
                    levelText = "Bad";
                    break;
                case 3:
                    levelText = "OK";
                    break;
                case 4:
                    levelText = "Good";
                    break;
                case 5:
                    levelText = "Excellent";
                    break;
                default:
                    levelText = "Unknown";
                    break;
            }
            tvLevelRate.setText(levelText);
        });
    }

    private void setUpRecyclerView() {
        imageAdapter = new ImageAdapter(selectedImagesList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        imgFromGallery.setLayoutManager(layoutManager);
        imgFromGallery.setAdapter(imageAdapter);
    }
}
