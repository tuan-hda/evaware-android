package com.example.evaware.presentation.catalog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.GridView;

import com.example.evaware.data.model.ProductModel;
import com.example.evaware.databinding.ActivityCatalogBinding;
import com.example.evaware.presentation.bottomSheet.Sort;
import com.example.evaware.presentation.category.CategoryViewModel;
import com.example.evaware.presentation.filter.Filter;
import com.example.evaware.utils.GlobalStore;
import com.example.evaware.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

public class CatalogActivity extends AppCompatActivity {
    private GridView gridView;
    private CatalogAdapter adapter;
    private List<ProductModel> dataList;
    private ActivityCatalogBinding binding;
    private String categoryId;
    private String categoryName;

    private CatalogViewModel viewModel;
    private LoadingDialog loadingDialog;
    private EditText edtSearch;
    private Sort sort;
    private static final String TAG = "CatalogActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCatalogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        categoryId = intent.getStringExtra("categoryId");
        categoryName = intent.getStringExtra("categoryName");


        init();
        sort = new Sort(categoryId, viewModel);
        loadData();
        setUpEditText();
        GlobalStore.getInstance().setData("filterPrice", null);
    }

    private void setUpEditText() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed in this case
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter the products based on the search query
                filterProducts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed in this case
            }
        });
    }

    private void filterProducts(String query) {

        List<ProductModel> originalProducts = viewModel.getAllProductsByCategory(categoryId).getValue();
        if (originalProducts != null) {

            List<ProductModel> filteredProducts = new ArrayList<>();

            for (ProductModel product : originalProducts) {
                if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredProducts.add(product);
                }
            }

            adapter = new CatalogAdapter(this, filteredProducts.size(), filteredProducts);
            gridView.setAdapter(adapter);
        }
    }


    private void loadData() {
        loadingDialog.showDialog();
        viewModel.getAllProductsByCategory(categoryId).observe(this, productModels -> {
            dataList = new ArrayList<>();
            for (ProductModel product : productModels) {
                dataList.add(product);
            }
            adapter = new CatalogAdapter(this, dataList.size(), dataList);
            Log.e(TAG, "loadData: " + dataList.size());
            gridView.setAdapter(adapter);
            loadingDialog.dismissDialog();
        });
    }

    public void init() {
        viewModel = new ViewModelProvider(this).get(CatalogViewModel.class);
        loadingDialog = new LoadingDialog(this);
        gridView = binding.gvProduct;
        binding.appbar.appbarTitle.setText(categoryName);
        dataList = new ArrayList<>();
        edtSearch = binding.edtSearch;

        binding.appbar.btnBack.setOnClickListener(v -> {
            finish();
        });

        binding.btnSort.setOnClickListener(v -> {
            sort.show(getSupportFragmentManager(), "Sort");
        });

        binding.btnFilter.setOnClickListener(v -> {
            Intent intent = new Intent(CatalogActivity.this, Filter.class);
            intent.putExtra("categoryId", categoryId);
            CatalogViewModel catalogViewModel = new ViewModelProvider(this).get(CatalogViewModel.class);
            GlobalStore.getInstance().setData("catalogViewModel", catalogViewModel);
            startActivity(intent);
        });
    }
}