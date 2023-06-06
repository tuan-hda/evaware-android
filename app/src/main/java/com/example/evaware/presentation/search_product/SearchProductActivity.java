package com.example.evaware.presentation.search_product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.evaware.data.model.ProductModel;
import com.example.evaware.databinding.ActivityProductBinding;
import com.example.evaware.databinding.ActivitySearchProductBinding;
import com.example.evaware.presentation.product.ProductViewModel;
import com.example.evaware.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

public class SearchProductActivity extends AppCompatActivity {
    private ActivitySearchProductBinding binding;
    private ProductViewModel productViewModel;
    private List<ProductModel> productModelList = new ArrayList<>();
    private LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        loadData();
        setData();
    }

    private void setData() {
        SearchProductAdapter adapter = new SearchProductAdapter(productModelList, this);
        binding.rvProducts.setLayoutManager(new LinearLayoutManager(this));
        binding.rvProducts.setAdapter(adapter);
    }

    private void loadData() {
        loadingDialog.showDialog();
        productViewModel.getAllProduct().observe(this, productModels -> {
            productModelList = productModels;
            loadingDialog.dismissDialog();
        });
    }

    private void init() {
        binding.edtSearchProduct.requestFocus();
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        loadingDialog = new LoadingDialog(this);

        binding.edtSearchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No implementation needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No implementation needed
            }

            @Override
            public void afterTextChanged(Editable s) {
                filterProducts(s.toString());
            }
        });
    }
    private void filterProducts(String query) {
        List<ProductModel> filteredList = new ArrayList<>();
        for (ProductModel product : productModelList) {
            if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(product);
            }
        }
        SearchProductAdapter adapter = new SearchProductAdapter(filteredList, this);
        binding.rvProducts.setAdapter(adapter);
    }
}