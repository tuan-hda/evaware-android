package com.example.evaware.presentation.filter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.View;

import com.example.evaware.data.model.CategoryModel;
import com.example.evaware.data.model.ProductModel;
import com.example.evaware.data.model.VariationModel;
import com.example.evaware.data.repo.CategoryRepository;
import com.example.evaware.data.repo.ProductRepository;
import com.example.evaware.databinding.ActivityFilterBinding;
import com.example.evaware.presentation.catalog.CatalogViewModel;
import com.example.evaware.utils.CurrencyFormat;
import com.example.evaware.utils.GlobalStore;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.slider.RangeSlider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Filter extends AppCompatActivity {
    List<CategoryModel> categories = new ArrayList<>();
    ActivityFilterBinding binding;
    private CatalogViewModel viewModel;
    private static final String TAG = "Filter";
    private String categoryId;
    private ProductRepository repository;
    private CategoryRepository categoryRepository;
    CollectionReference productRef = FirebaseFirestore.getInstance().collection("products");
    DocumentReference categoryReference;
    List<Float> initialValues;
    List<Float> initialRanges;
    List<VariationModel> selectVariations;
    List<CategoryModel> selectCategories;
    FilterViewModel filterViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFilterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new ProductRepository();
        categoryRepository = new CategoryRepository();
        selectVariations = new ArrayList<>();
        selectCategories = new ArrayList<>();
        initialValues = new ArrayList<>();
        initialRanges = new ArrayList<>();

        binding.filterIbClose.setOnClickListener(v -> {
            finish();
        });


        initEle();
        handleIntent();
        getVariations();
        getMinMax();
        filterListen();
    }

    private void initEle() {
        filterViewModel = new ViewModelProvider(this).get(FilterViewModel.class);

        binding.filterBtShowMore.setOnClickListener(v -> {
            viewModel.forceGet(categoryId, binding.filterRsPrice.getValues().get(0), binding.filterRsPrice.getValues().get(1), true, binding, selectCategories, selectVariations);
            GlobalStore.getInstance().setData("filterPrice", binding.filterRsPrice.getValues());
            GlobalStore.getInstance().setData("filterVariations", selectVariations);
            GlobalStore.getInstance().setData("filterCategories", selectCategories);
            finish();
        });

        categoryRepository.getAllCategories().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                categories = new ArrayList<>();

                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                    categories.add(snapshot.toObject(CategoryModel.class));
                }

                CategoryAdapter adapter = new CategoryAdapter(Filter.this, categories, filterViewModel);
                binding.filterLvListCategories.setAdapter(adapter);
                binding.filterLvListCategories.setLayoutManager(new LinearLayoutManager(Filter.this));
            }
        });

        filterViewModel.selectCategories.observe(this, categoryModels -> {
            selectCategories = categoryModels;
            List<Float> values = binding.filterRsPrice.getValues();
            float vl1 = 0, vl2 = 0;
            if (values.size() == 1) {
                vl1 = values.get(0);
            }
            if (values.size() == 2) {
                vl2 = values.get(1);
            }
            viewModel.forceGet(categoryId, vl1, vl2, false, binding, selectCategories, selectVariations);
        });

        filterViewModel.selectVariations.observe(this, models -> {
            selectVariations = models;
            List<Float> values = binding.filterRsPrice.getValues();
            float vl1 = 0, vl2 = 0;
            if (values.size() == 1) {
                vl1 = values.get(0);
            }
            if (values.size() == 2) {
                vl2 = values.get(1);
            }
            viewModel.forceGet(categoryId, vl1, vl2, false, binding, selectCategories, selectVariations);
        });
    }


    private void filterListen() {
        binding.filterRsPrice.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                List<Float> values = binding.filterRsPrice.getValues();
                viewModel.forceGet(categoryId, values.get(0), values.get(1), false, binding, selectCategories, selectVariations);
            }
        });
    }

    private void handleIntent() {
        viewModel = (CatalogViewModel) GlobalStore.getInstance().getData("catalogViewModel");
        if (GlobalStore.getInstance().getData("filterVariations") != null) {
            filterViewModel.setVariations((List<VariationModel>) GlobalStore.getInstance().getData("filterVariations"));
        }
        if (GlobalStore.getInstance().getData("filterCategories") != null) {
            filterViewModel.setCategories((List<CategoryModel>) GlobalStore.getInstance().getData("filterCategories"));
        }

        categoryId = getIntent().getStringExtra("categoryId");
        if (categoryId != null) {
            binding.filterLvListCategories.setVisibility(View.GONE);
            binding.textCategories.setVisibility(View.GONE);
            categoryReference = categoryRepository.getCategoriesById(categoryId);
        }
    }

    public void getVariations() {
        Query query = null;
        if (categoryReference != null) {
            query = repository.getAllProductsByCategoryAlt(categoryReference);
        } else {
            query = repository.getAllProductAlt();
        }

        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<VariationModel> variations = new ArrayList<>();
            for (DocumentSnapshot productSnapshot : queryDocumentSnapshots) {
                CollectionReference variationCollectionRef = productSnapshot.getReference().collection("variations");
                variationCollectionRef.get().addOnSuccessListener(variationQueryDocumentSnapshots -> {
                    for (DocumentSnapshot variationSnapshot : variationQueryDocumentSnapshots) {
                        VariationModel variation = variationSnapshot.toObject(VariationModel.class);
                        variations.add(variation);
                    }
                    VariationAdapter adapter = new VariationAdapter(Filter.this, variations, filterViewModel);
                    binding.filterLvListColours.setAdapter(adapter);
                    binding.filterLvListColours.setLayoutManager(new LinearLayoutManager(Filter.this));
                }).addOnFailureListener(e -> {
                    Log.e(TAG, "getVariations: ", e);
                });
            }

        }).addOnFailureListener(e -> {
            Log.e(TAG, "getVariations: ", e);
        });
    }

    public void getMinMax() {
        Query query = null;
        if (categoryReference != null) {
            query = repository.getAllProductsByCategoryAlt(categoryReference);
        } else {
            query = repository.getAllProductAlt();
        }


        query.orderBy("price", Query.Direction.ASCENDING).limit(1).get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                DocumentSnapshot minDocumentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                ProductModel minProduct = minDocumentSnapshot.toObject(ProductModel.class);
                if (minProduct != null) {
                    if (initialValues.size() != 0) {
                        Float fl1 = initialValues.get(0);
                        List<Float> prices = (List<Float>) GlobalStore.getInstance().getData("filterPrice");
                        if (prices != null) {
                            binding.filterRsPrice.setValues(prices);
                        } else {
                            binding.filterRsPrice.setValues(minProduct.price.floatValue(), fl1);
                        }
                    } else {
                        initialValues.add(minProduct.price.floatValue());
                        binding.filterRsPrice.setValues(minProduct.price.floatValue(), minProduct.price.floatValue());

                    }
                    binding.filterRsPrice.setValueFrom(minProduct.price.floatValue());
                    binding.filterTvMinPrice.setText(CurrencyFormat.getFormattedPrice(minProduct.price));
                }
            } else {

            }
        }).addOnFailureListener(e -> {

        });

        query.orderBy("price", Query.Direction.DESCENDING).limit(1).get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                ProductModel product = documentSnapshot.toObject(ProductModel.class);
                if (product != null) {
                    if (initialValues.size() != 0) {
                        Float fl1 = initialValues.get(0);
                        List<Float> prices = (List<Float>) GlobalStore.getInstance().getData("filterPrice");
                        if (prices != null) {
                            binding.filterRsPrice.setValues(prices);
                        } else {
                            binding.filterRsPrice.setValues(fl1, product.price.floatValue());
                        }

                    } else {
                        initialValues.add(product.price.floatValue());
                    }
                    binding.filterRsPrice.setValueTo(product.price.floatValue());
                    binding.filterTvMaxPrice.setText(CurrencyFormat.getFormattedPrice(product.price));
                }
            } else {

            }
        }).addOnFailureListener(e -> {

        });
    }
}
