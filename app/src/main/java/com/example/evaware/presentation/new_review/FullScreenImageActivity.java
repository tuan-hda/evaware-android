package com.example.evaware;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class FullScreenImageActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the status bar and make the activity full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_full_screen_image);

        imageView = findViewById(R.id.iv_full_screen_image);

        // Retrieve the image URI from the intent extras
        Uri imageUri = Uri.parse(getIntent().getStringExtra("imageUri"));

        // Load the image into the full-screen ImageView using Glide
        Glide.with(this)
                .load(imageUri)
                .fitCenter()
                .into(imageView);

        // Set click listener to close the activity when the image is clicked
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}