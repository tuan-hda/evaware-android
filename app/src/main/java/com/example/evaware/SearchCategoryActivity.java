package com.example.evaware;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.evaware.data.model.CategoryModel;
import com.example.evaware.data.model.TypeofCategory;
import com.example.evaware.data.repo.CategoryRepository;
import com.example.evaware.data.repo.OnDataFetchListener;
import com.example.evaware.databinding.ActivityDeliveryAddressBinding;
import com.example.evaware.databinding.ActivitySearchCategoryBinding;
import com.example.evaware.presentation.home.CategoryAdapter;
import com.example.evaware.presentation.home.TypeOfCategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchCategoryActivity extends AppCompatActivity implements OnDataFetchListener<List<CategoryModel>> {
    private ActivitySearchCategoryBinding binding;
    private RecyclerView recyclerView;
    private String categoryId;
    private String categoryName;
    private List<CategoryModel> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        categoryId = intent.getStringExtra("itemId");
        categoryName = intent.getStringExtra("itemName");

        CategoryRepository repo = new CategoryRepository(categoryId);
        repo.loadDataByTypeRef(this);

        init();

    }

    public void init() {
        recyclerView = binding.rcvCategories;
        binding.tvTitle.setText(categoryName);
        categories = new ArrayList<CategoryModel>();
        CategoryAdapter adapter = new CategoryAdapter(categories, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onSuccess(List<CategoryModel> data) {
        for (CategoryModel item : data) {
            categories.add(item);
            CategoryAdapter adapter = new CategoryAdapter(categories, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onFailure(Exception e) {
        Toast.makeText(this, "Failed to fetch data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}