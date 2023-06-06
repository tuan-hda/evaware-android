package com.example.evaware.presentation.new_review;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.evaware.R;

public class FullScreenImageActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_full_screen_image);

        imageView = findViewById(R.id.iv_full_screen_image);

        if (getIntent().hasExtra("imageUri")) {
            Uri imageUri = Uri.parse(getIntent().getStringExtra("imageUri"));
            loadImageUri(imageUri);
        } else if (getIntent().hasExtra("imageUrl")) {
            String imageUrl = getIntent().getStringExtra("imageUrl");
            loadImageUrl(imageUrl);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadImageUri(Uri imageUri) {
        Glide.with(this)
                .load(imageUri)
                .fitCenter()
                .into(imageView);
    }

    private void loadImageUrl(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .fitCenter()
                .into(imageView);
    }
}