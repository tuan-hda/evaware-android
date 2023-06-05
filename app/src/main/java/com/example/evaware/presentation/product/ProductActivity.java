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


import com.example.evaware.R;
import com.example.evaware.data.model.BagItemModel;
import com.example.evaware.data.model.WishItemModel;
import com.example.evaware.databinding.ActivityProductBinding;
import com.example.evaware.presentation.bag.BagViewModel;
import com.example.evaware.presentation.bottomSheet.ProductInfoDialog;
import com.example.evaware.presentation.catalog.CatalogAdapter;
import com.example.evaware.presentation.review.ReviewsActivity;
import com.example.evaware.data.model.ProductDetail;
import com.example.evaware.data.model.ProductModel;
import com.example.evaware.data.model.VariationModelsDetail;
import com.example.evaware.data.model.VariationProductModel;
import com.example.evaware.presentation.wishlist.WishViewModel;
import com.example.evaware.utils.CurrencyFormat;
import com.example.evaware.utils.GlobalStore;
import com.example.evaware.utils.LoadingDialog;
import com.example.evaware.utils.SnackBar;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.Date;
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
    private BagViewModel bagViewModel;
    private WishViewModel wishViewModel;
    private LoadingDialog dialog;
    private String productName;
    List<VariationModelsDetail> variantsDetail = new ArrayList<>();
    private boolean saved = false;
    private DocumentReference productRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        loadData(false);
        setUpBtn();
        setUpRefresh();

    }

    void init() {
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        viewPager = binding.vpProductSlide;
        dialog = new LoadingDialog(this);
        Intent intent = getIntent();
        productModelId = intent.getStringExtra("productModelId");
        bagViewModel = new ViewModelProvider(this).get(BagViewModel.class);
        wishViewModel = new ViewModelProvider(this).get(WishViewModel.class);
        binding.imgBtnSavedItem.setBackgroundResource(R.drawable.heart);
        productViewModel.getProductRefBbyId(productModelId).observe(this, productRefData -> {
            productRef = productRefData;
        });

        checkProductInSaved();
    }

    private void checkProductInSaved() {
        List<WishItemModel> list = (List<WishItemModel>) GlobalStore.getInstance().getData("wishList");
        for (WishItemModel item : list) {
            if (item.getProduct_ref().getId().equals(productModelId)) {
                binding.imgBtnSavedItem.setBackgroundResource(R.drawable.heart_filled);
                saved = true;
                return;
            }
        }
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
            CatalogAdapter adapter = new CatalogAdapter(this, productModels.size(), productModels);
            binding.gvOtherProductList.setAdapter(adapter);
        });
    }


    private void setUpBtn() {
        binding.btnBack.setOnClickListener(view -> {
            finish();
        });
        binding.imgBtnSavedItem.setOnClickListener(view -> {
            SnackBar.showSnackSuccessful(ProductActivity.this, binding.getRoot(), (ViewGroup) findViewById(android.R.id.content));
        });
        binding.btnAddToBag.setOnClickListener(view -> {
            productViewModel.getProductRefBbyId(productModelId).observe(this, documentReference -> {
                BagItemModel bagItemModel = new BagItemModel();
                bagItemModel.product_ref = documentReference;
                bagItemModel.created_at = (new Timestamp(new Date()));
                bagItemModel.updated_at = (new Timestamp(new Date()));
                bagViewModel.addItem(bagItemModel);
            });
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

        binding.llReviews.setOnClickListener(view -> {
            Intent intent1 = new Intent(this, ReviewsActivity.class);
            intent1.putExtra("productId", productModelId);
            intent1.putExtra("productName", productName);
            this.startActivity(intent1);
        });

        binding.imgBtnSavedItem.setOnClickListener(view -> {
            List<WishItemModel> list = (List<WishItemModel>) GlobalStore.getInstance().getData("wishList");
            if (saved) {
                    dialog.showDialog();
                    for (WishItemModel item : list) {
                        if (item.getProduct_ref().getId().equals(productModelId)) {
                            wishViewModel.remove(item.getId()).observe(this, message->{
                                list.remove(item);
                                binding.imgBtnSavedItem.setBackgroundResource(R.drawable.heart);
                                dialog.dismissDialog();
                            });

                        }
                    }
                saved = false;
            } else {
                dialog.showDialog();
                WishItemModel wishItemModel = new WishItemModel();
                wishItemModel.setProduct_ref(productRef);
                wishItemModel.setCreate_at(new Timestamp(new Date()));
                wishItemModel.setUpdate_at((new Timestamp(new Date())));
                wishViewModel.addWishList(wishItemModel).observe(this, wishId -> {
                    wishItemModel.setId(wishId);
                    list.add(wishItemModel);
                    binding.imgBtnSavedItem.setBackgroundResource(R.drawable.heart_filled);
                    dialog.dismissDialog();
                });
                saved = true;
            }
            GlobalStore.getInstance().setData("wishList", list);
        });

    }

    @Override
    public void onVariationProductClick(int position) {
        List<String> slideList = variantsDetail.get(position).getListImgUrls();
        slideAdapter = new SlideProductAdapter(this, slideList);
        viewPager.setAdapter(slideAdapter);
    }

    private void setUpRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            Boolean withoutDialogLoading = true;
            variantsDetail.clear();
            loadData(withoutDialogLoading);
            binding.swipeRefreshLayout.setRefreshing(false);
        });
    }
}

