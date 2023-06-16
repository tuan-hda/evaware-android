package com.example.evaware.presentation.catalog;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.evaware.data.model.ProductModel;
import com.example.evaware.data.repo.CategoryRepository;
import com.example.evaware.data.repo.ProductRepository;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CatalogViewModel extends AndroidViewModel {
    private static final String TAG = "CatalogViewModel";
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    private MutableLiveData<List<ProductModel>> productsLiveData;
    private List<ProductModel> products;

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
            DocumentReference categoryReference = categoryRepository.getCategoriesById(categoryId);
            if (products.size() != 0) {
                return productsLiveData;
            }

            productRepository.getAllProductsByCategory(categoryReference).addOnSuccessListener(task -> {
                List<DocumentSnapshot> documents = task.getDocuments();
                for (DocumentSnapshot document : documents) {
                    try{
                        ProductModel product = document.toObject(ProductModel.class);
                        if(product.getDesc().length() > 55){
                            product.setDesc(product.getDesc().substring(0, 55-3) + "...");
                        }
                        products.add(product);
                    }catch (Exception e){
                        Log.d(TAG, "getAllProductsByCategory: " + e.getMessage());
                    }

                }
                productsLiveData.setValue(products);
            }).addOnFailureListener(e -> {
                Log.d(TAG, "getCategoryList:failure: " + e.getLocalizedMessage());
            });

        return productsLiveData;
    }

}
