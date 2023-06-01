package com.example.evaware.presentation.product;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.evaware.data.model.ImgProduct;
import com.example.evaware.data.model.ProductDetail;
import com.example.evaware.data.model.ProductModel;
import com.example.evaware.data.model.VariationModel;
import com.example.evaware.data.model.VariationModelsDetail;
import com.example.evaware.data.model.VariationProductModel;
import com.example.evaware.data.repo.ProductRepository;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private static final String TAG = "ProductViewModel";
    private ProductRepository productRepository;
    private MutableLiveData<ProductModel> productModelLiveData;
    private MutableLiveData<List<VariationProductModel>> variantsLiveData;
    private MutableLiveData<List<String>> imgListLiveData;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository();
        productModelLiveData = new MutableLiveData<>();
        variantsLiveData = new MutableLiveData<>();
        imgListLiveData = new MutableLiveData<>();
    }

    public LiveData<ProductModel> getProductModelById(String id) {

        DocumentReference productReference = productRepository.getProductById(id);
        productReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null && document.exists()) {
                    try {
                        ProductModel productModel = document.toObject(ProductModel.class);
                        productModelLiveData.setValue(productModel);
                    }
                    catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
        });
        return productModelLiveData;
    }

    public LiveData<List<VariationProductModel>> getVariations(String productId) {
        MutableLiveData<List<VariationProductModel>> variantsLiveData = new MutableLiveData<>();
        productRepository.getVariationOfProduct(productId).addOnSuccessListener(task -> {
            List<DocumentSnapshot> documents = task.getDocuments();
            List<VariationProductModel> variationModels = new ArrayList<>();
            for (DocumentSnapshot doc : documents) {
                try {
                    VariationProductModel variant = doc.toObject(VariationProductModel.class);
                    Log.d(TAG, variant.toString());
                    variationModels.add(variant);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }
            variantsLiveData.setValue(variationModels);
        }).addOnFailureListener(e -> {
            Log.d(TAG, e.getMessage());
        });
        return variantsLiveData;
    }

    public LiveData<List<String>> getListImgUrl(String productId, String variationProductId) {
        MutableLiveData<List<String>> listImgLiveData = new MutableLiveData<>();
        productRepository.getImgOfVariation(productId, variationProductId).addOnSuccessListener(task -> {
            List<String> imgUrls = new ArrayList<>();
            for (DocumentSnapshot doc : task.getDocuments()) {
                try {
                    ImgProduct productImg = doc.toObject(ImgProduct.class);
                    imgUrls.add(productImg.getImg_url());
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }
            listImgLiveData.setValue(imgUrls);
        }).addOnFailureListener(e -> {
            Log.d(TAG, e.getMessage());
        });

        return listImgLiveData;
    }
}
