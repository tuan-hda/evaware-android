package com.example.evaware.presentation.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;


import com.example.evaware.ReviewsActivity;
import com.example.evaware.data.model.ProductDetail;
import com.example.evaware.data.model.ProductModel;
import com.example.evaware.data.model.VariationModel;
import com.example.evaware.data.model.VariationModelsDetail;
import com.example.evaware.data.model.VariationProductModel;
import com.example.evaware.databinding.ActivityProductBinding;
import com.example.evaware.presentation.catalog.CatalogActivity;
import com.example.evaware.utils.LoadingDialog;
import com.facebook.FacebookActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ProductActivity extends AppCompatActivity implements VariationProductAdapter.OnVariationProductListener {
    private ActivityProductBinding binding;

    private ViewPager viewPager;
    private SlideProductAdapter slideAdapter;
    private String productModelId;
    private ProductViewModel productViewModel;
    private ProductModel product = new ProductModel();
    private LoadingDialog dialog;
    List<VariationModelsDetail> variantsDetail = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        dialog.showDialog();
        loadData();


    }

    private void loadData() {
        AtomicReference<Integer> count = new AtomicReference<>(0);
        ProductDetail productDetail = new ProductDetail();

        productViewModel.getProductModelById(productModelId).observe(this, productModel -> {
            binding.tvProductPrice.setText(String.format("$%S", productModel.getPrice()));
            binding.tvProductDes.setText(productModel.getDesc());
        });

        productViewModel.getVariations(productModelId).observe(this, variants -> {
            for (VariationProductModel variant : variants) {
                VariationModelsDetail variantDetail = new VariationModelsDetail();
                variantDetail.setModel(variant);

                productViewModel.getListImgUrl(productModelId, variant.getId()).observe(this, strings -> {
                    if (count.get() == 0) {
                        count.set(1);
                        slideAdapter = new SlideProductAdapter(this, strings);
                        viewPager.setAdapter(slideAdapter);
                    }
                    variantDetail.setListImgUrls(strings);
                });

                variantsDetail.add(variantDetail);
            }

            VariationProductAdapter adapter = new VariationProductAdapter(variantsDetail);
            productDetail.setVariationModelDetails(variantsDetail);
            adapter.setOnVariationProductListener(this);
            binding.rvVariations.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            binding.rvVariations.setAdapter(adapter);
            dialog.dismissDialog();
        });
    }


    void init() {
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        viewPager = binding.vpProductSlide;
        dialog = new LoadingDialog(this);
        Intent intent = getIntent();
        productModelId = intent.getStringExtra("productModelId");

        binding.llReviews.setOnClickListener(view -> {
            Intent intent1 = new Intent(this, ReviewsActivity.class);
            this.startActivity(intent1);
        });
    }

    @Override
    public void onVariationProductClick(int position) {
        List<String> slideList = variantsDetail.get(position).getListImgUrls();
        slideAdapter = new SlideProductAdapter(this, slideList);
        viewPager.setAdapter(slideAdapter);
    }
}

