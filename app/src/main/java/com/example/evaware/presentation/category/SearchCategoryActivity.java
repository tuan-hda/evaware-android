package com.example.evaware.presentation.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.example.evaware.data.model.CategoryModel;
import com.example.evaware.databinding.ActivitySearchCategoryBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchCategoryActivity extends AppCompatActivity{
    private ActivitySearchCategoryBinding binding;
    private RecyclerView recyclerView;
    private List<CategoryModel> categories;
    private CategoryViewModel categoryViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        loadData();
        setUpEditText();


    }

    private void setUpEditText() {
        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterCategories(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void filterCategories(String query) {
        List<CategoryModel> originalCategories = categoryViewModel.getAllCategory().getValue();
        if (originalCategories != null) {
            List<CategoryModel> filteredCategories = new ArrayList<>();
            for (CategoryModel category : originalCategories) {
                if (category.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredCategories.add(category);
                }
            }
            CategoryAdapter adapter = new CategoryAdapter(filteredCategories, this);
            recyclerView.setAdapter(adapter);
        }
    }

    public void init() {
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        recyclerView = binding.rcvCategories;
        binding.appbar.appbarTitle.setText("Categories");
        categories = new ArrayList<CategoryModel>();
        CategoryAdapter adapter = new CategoryAdapter(categories, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        binding.appbar.btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void loadData() {
        categoryViewModel.getAllCategory().observe(this, categoryModels -> {
            if (categoryModels.size() == 0) {
                Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
            } else {
                CategoryAdapter adapter = new CategoryAdapter(categoryModels, this);
                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapter);
            }
        });
    }

}