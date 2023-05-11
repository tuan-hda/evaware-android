package com.example.evaware.presentation.base;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.evaware.R;
import com.example.evaware.databinding.SearchBinding;

public class SearchActivity extends AppCompatActivity {
    private SearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SearchActivity.this, "Hello world", Toast.LENGTH_SHORT).show();
            }
        });
    }
}