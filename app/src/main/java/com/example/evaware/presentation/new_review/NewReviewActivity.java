package com.example.evaware.presentation.new_review;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.evaware.databinding.ActivityNewReviewBinding;

import java.util.ArrayList;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class NewReviewActivity extends AppCompatActivity {

    private ActivityNewReviewBinding binding;
    private ActivityResultLauncher<Intent> galleryLauncher;
    private RecyclerView imgFromGallery;
    private ArrayList<Uri> selectedImagesList;
    private ImageAdapter imageAdapter;
    private MaterialRatingBar ratingBar;
    private TextView tvLevelRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setUpButtons();
        setUpRatingBar();
    }

    private void init(){

        binding = ActivityNewReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imgFromGallery = binding.rvImgList;
        ratingBar = binding.ratingbar;
        tvLevelRate = binding.tvLevelRate;

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
    private void setUpButtons(){
        binding.btnClose.setOnClickListener(view -> finish());
        binding.cvOpenGallery.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            galleryLauncher.launch(intent);
        });
        binding.btnSendReview.setOnClickListener(view ->{
            if(binding.edtNewReview.getText().toString().equals("")){
                binding.edtNewReview.setError("Please enter your review");
            }
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
