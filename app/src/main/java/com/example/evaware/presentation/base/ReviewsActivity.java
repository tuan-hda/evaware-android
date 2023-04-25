package com.example.evaware.presentation.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;


import com.example.evaware.R;
import com.example.evaware.databinding.ActivityLoginBinding;
import com.example.evaware.databinding.ActivityMainBinding;


public class ReviewsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

    }
}