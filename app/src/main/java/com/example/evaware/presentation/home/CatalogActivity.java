package com.example.evaware.presentation.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.GridView;

import com.example.evaware.R;
import com.example.evaware.databinding.ActivityCatalogBinding;
import com.example.evaware.databinding.ActivityConfirmOrderBinding;

import java.util.ArrayList;
import java.util.List;

public class CatalogActivity extends AppCompatActivity {
    private GridView gridView;
    private CatalogAdapter adapter;
    private List<String> dataList;
    private ActivityCatalogBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCatalogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    public void init() {
        gridView = binding.gvProduct;
        dataList = new ArrayList<>();
        dataList.add("Item 1");
        dataList.add("Item 2");
        dataList.add("Item 3");
        dataList.add("Item 4");
        dataList.add("Item 5");
        dataList.add("Item 6");
        dataList.add("Item 4");
        dataList.add("Item 5");
        dataList.add("Item 6");

        adapter = new CatalogAdapter(this, dataList);
        gridView.setAdapter(adapter);
    }
}