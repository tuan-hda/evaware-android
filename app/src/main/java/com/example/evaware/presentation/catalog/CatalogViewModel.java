package com.example.evaware.presentation.catalog;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.evaware.data.model.CategoryModel;
import com.example.evaware.data.model.ProductModel;
import com.example.evaware.data.model.VariationModel;
import com.example.evaware.data.repo.CategoryRepository;
import com.example.evaware.data.repo.ProductRepository;
import com.example.evaware.databinding.ActivityFilterBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CatalogViewModel extends AndroidViewModel {
    private static final String TAG = "CatalogViewModel";
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    private MutableLiveData<List<ProductModel>> productsLiveData;
    private List<ProductModel> products;
    private int option = 0;

    public CatalogViewModel(@NonNull Application application) {
        super(application);
        init();
    }

    private void init() {
        categoryRepository = new CategoryRepository();
        productRepository = new ProductRepository();
        products = new ArrayList<>();
        productsLiveData = new MutableLiveData<>();
    }

    public LiveData<List<ProductModel>> getAllProductsByCategory(String categoryId) {
        if (products.size() != 0) {
            return productsLiveData;
        }

        forceGet(categoryId);

        return productsLiveData;
    }

    public LiveData<List<ProductModel>> getAllProducts(String categoryId) {
        if (products.size() != 0) {
            return productsLiveData;
        }

        forceGet(categoryId, 0);

        return productsLiveData;
    }

    public void forceGet(String categoryId) {
        DocumentReference categoryReference = categoryRepository.getCategoriesById(categoryId);

        productRepository.getAllProductsByCategory(categoryReference).addOnSuccessListener(task -> {
            List<DocumentSnapshot> documents = task.getDocuments();
            for (DocumentSnapshot document : documents) {
                try {
                    ProductModel product = document.toObject(ProductModel.class);
                    if (product.getDesc().length() > 55) {
                        product.setDesc(product.getDesc().substring(0, 55 - 3) + "...");
                    }
                    products.add(product);
                } catch (Exception e) {
                    Log.d(TAG, "getAllProductsByCategory: " + e.getMessage());
                }

            }
            productsLiveData.setValue(products);
        }).addOnFailureListener(e -> {
            Log.d(TAG, "getCategoryList:failure: " + e.getLocalizedMessage());
        });
    }

    public void forceGet(String categoryId, int option) {
        DocumentReference categoryReference = null;
        if (categoryId != null) {
            categoryReference = categoryRepository.getCategoriesById(categoryId);
        }

        this.option = option;
        String opt = "";
        Query.Direction direction = Query.Direction.ASCENDING;
        switch (option) {
            case 0:
                opt = "price";
                direction = Query.Direction.DESCENDING;
                break;
            case 1:
                opt = "price";
                direction = Query.Direction.ASCENDING;
                break;
            case 2:
                opt = "created_at";
                direction = Query.Direction.DESCENDING;
                break;
            case 3:
                opt = "created_at";
                direction = Query.Direction.ASCENDING;
        }

        Log.e(TAG, "forceGet: " + opt + " " + direction.toString());

        products = new ArrayList<>();

        Query query = null;
        if (categoryId != null) {
            query = productRepository.getAllProductsByCategoryAlt(categoryReference)
                    .orderBy(opt, direction);
        } else {
            query = productRepository.getAllProductAlt().orderBy(opt, direction);
        }

        query.get().addOnSuccessListener(task -> {
            List<DocumentSnapshot> documents = task.getDocuments();
            for (DocumentSnapshot document : documents) {
                try {
                    ProductModel product = document.toObject(ProductModel.class);
                    products.add(product);
                } catch (Exception e) {
                    Log.d(TAG, "getAllProductsByCategory: " + e.getMessage());
                }
            }
            productsLiveData.setValue(products);
        }).addOnFailureListener(e -> {
            Log.d(TAG, "getCategoryList:failure: " + e.getLocalizedMessage());
        });


    }

    public void forceGet(String categoryId, double minPrice, double maxPrice, @Nullable boolean apply,
                         ActivityFilterBinding binding, List<CategoryModel> categoryModels, List<VariationModel> variationModels) {
        DocumentReference categoryReference = null;
        if (categoryId != null) {
            categoryReference = categoryRepository.getCategoriesById(categoryId);
        }

        String opt = "";
        Query.Direction direction = Query.Direction.ASCENDING;
        switch (option) {
            case 0:
                opt = "price";
                direction = Query.Direction.DESCENDING;
                break;
            case 1:
                opt = "price";
                direction = Query.Direction.ASCENDING;
                break;
            case 2:
                opt = "created_at";
                direction = Query.Direction.DESCENDING;
                break;
            case 3:
                opt = "created_at";
                direction = Query.Direction.ASCENDING;
        }


        Query query = null;
        if (categoryId == null) {
            query = productRepository.getAllProductAlt()
                    .orderBy(opt, direction)
                    .whereGreaterThanOrEqualTo("price", minPrice)
                    .whereLessThanOrEqualTo("price", maxPrice);
        } else {
            query = productRepository.getAllProductsByCategoryAlt(categoryReference)
                    .orderBy(opt, direction)
                    .whereGreaterThanOrEqualTo("price", minPrice)
                    .whereLessThanOrEqualTo("price", maxPrice);
        }

        if (categoryModels.size() != 0) {
            Log.e(TAG, "forceGet: " + " trig");
            List<DocumentReference> references = new ArrayList<>();
            for (CategoryModel model : categoryModels) {
                references.add(categoryRepository.getCategoriesById(model.getId()));
            }
            query = query.whereIn("category_ref", references);
        }

        query.get().addOnSuccessListener(task -> {
            products = new ArrayList<>();
            List<DocumentSnapshot> documents = task.getDocuments();
            Log.e(TAG, "forceGet: " + products.size() );
            for (DocumentSnapshot document : documents) {
                try {
                    ProductModel product = document.toObject(ProductModel.class);

                    if (variationModels.size() != 0) {
                        List<String> references = new ArrayList<>();
                        for (VariationModel model : variationModels) {
                            if (!references.contains(model.name)) {
                                references.add(model.name);
                            }
                        }
                        FirebaseFirestore.getInstance()
                                .collection("products")
                                .document(product.id)
                                .collection("variations").whereIn("name", references).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        if (queryDocumentSnapshots.size() != 0) {
                                            products.add(product);
                                            binding.filterBtShowMore.setText("Show " + products.size() + " items");
                                            if (apply) {
                                                productsLiveData.setValue(products);
                                            }
                                        }
                                    }
                                });
                    } else {
                        products.add(product);
                        binding.filterBtShowMore.setText("Show " + products.size() + " items");
                        if (apply) {
                            productsLiveData.setValue(products);
                        }
                    }
                } catch (Exception e) {
                    Log.d(TAG, "getAllProductsByCategory: " + e.getMessage());
                }
            }

        }).addOnFailureListener(e -> {
            Log.d(TAG, "getCategoryList:failure: " + e.getLocalizedMessage());
        });
    }
}
