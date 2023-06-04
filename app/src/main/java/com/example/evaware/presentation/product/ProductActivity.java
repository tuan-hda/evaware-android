package com.example.evaware.presentation.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;


import com.example.evaware.databinding.ActivityProductBinding;
import com.example.evaware.presentation.bottomSheet.ProductInfoDialog;
import com.example.evaware.presentation.catalog.CatalogAdapter;
import com.example.evaware.presentation.review.ReviewsActivity;
import com.example.evaware.data.model.ProductDetail;
import com.example.evaware.data.model.ProductModel;
import com.example.evaware.data.model.VariationModelsDetail;
import com.example.evaware.data.model.VariationProductModel;
import com.example.evaware.utils.CurrencyFormat;
import com.example.evaware.utils.LoadingDialog;
import com.example.evaware.utils.SnackBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ProductActivity extends AppCompatActivity implements VariationProductAdapter.OnVariationProductListener {
    private ActivityProductBinding binding;

    private ViewPager viewPager;
    private SlideProductAdapter slideAdapter;
    private String productModelId;
    private ProductViewModel productViewModel;
    private LoadingDialog dialog;
    private String productName;
    List<VariationModelsDetail> variantsDetail = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        loadData(false);
        setUpBtn();

    }

    private void setUpBtn() {
        binding.btnBack.setOnClickListener(view -> {
            finish();
        });
        binding.imgBtnSavedItem.setOnClickListener(view -> {
            SnackBar.showSnackSuccessful(ProductActivity.this, binding.getRoot(), (ViewGroup) findViewById(android.R.id.content));
        });
    }

    private void loadData(Boolean withoutDialogLoading) {
        if (!withoutDialogLoading) {
            dialog.showDialog();
        }
        AtomicReference<Integer> count = new AtomicReference<>(0);
        ProductDetail productDetail = new ProductDetail();

        productViewModel.getProductModelById(productModelId).observe(this, productModel -> {
            productName = productModel.getName();
            binding.tvProductPrice.setText(CurrencyFormat.getFormattedPrice(productModel.getPrice()));
            binding.tvProductDes.setText(productModel.getDesc());
            binding.tvProductQty.setText(String.valueOf(productModel.getReview_qty()));
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
            if (!withoutDialogLoading) {
                dialog.dismissDialog();
            }
            loadRecommendProduct();
        });
    }

    private void loadRecommendProduct() {
        productViewModel.getProductRecommendations(productModelId).observe(this, productModels -> {
            CatalogAdapter adapter = new CatalogAdapter(this,productModels.size(), productModels);
            binding.gvOtherProductList.setAdapter(adapter);
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
            intent1.putExtra("productId", productModelId);
            intent1.putExtra("productName", productName);
            this.startActivity(intent1);
        });

        binding.llOpenProductIndfo.setOnClickListener(view -> {
            productViewModel.getComposition(productModelId).observe(this, compositionList -> {
                productViewModel.getMeasurements(productModelId).observe(this, measurementList -> {
                    ProductInfoDialog bottomSheetProductInfo = new ProductInfoDialog();
                    bottomSheetProductInfo.setComposition(compositionList);
                    bottomSheetProductInfo.setMeasurements(measurementList);

                    bottomSheetProductInfo.show(getSupportFragmentManager(), "ModalBottomSheetDialog");
                });
            });
        });
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Boolean withoutDialogLoading = true;
                variantsDetail.clear();
                loadData(withoutDialogLoading);
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onVariationProductClick(int position) {
        List<String> slideList = variantsDetail.get(position).getListImgUrls();
        slideAdapter = new SlideProductAdapter(this, slideList);
        viewPager.setAdapter(slideAdapter);
    }
}

