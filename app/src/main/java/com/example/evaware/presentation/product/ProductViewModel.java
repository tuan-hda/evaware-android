package com.example.evaware.presentation.product;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.evaware.data.model.CompositionModel;
import com.example.evaware.data.model.ImgProduct;
import com.example.evaware.data.model.MeasurementModel;
import com.example.evaware.data.model.ProductModel;
import com.example.evaware.data.model.ProductRecommendation;
import com.example.evaware.data.model.VariationProductModel;
import com.example.evaware.data.repo.ProductRepository;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
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
                    } catch (Exception e) {
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

    public void updateReviewQty(String productId, int reviewQty) {
        productRepository.updateReviewQty(productId, reviewQty).addOnSuccessListener(task -> {
            Log.d(TAG, "update review quantity successful");
        });
    }

    public LiveData<List<HashMap<String, Object>>> getComposition(String productId) {
        MutableLiveData<List<HashMap<String, Object>>> compositionLiveData = new MutableLiveData<>();

        productRepository.getComposition(productId).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();

                if (document.exists()) {

                    if (document.exists()) {
                        List<HashMap<String, Object>> compositionList = new ArrayList<>();

                        for (String attribute : document.getData().keySet()) {
                            HashMap<String, Object> attributeMap = new HashMap<>();
                            attributeMap.put(attribute, document.get(attribute));
                            compositionList.add(attributeMap);
                        }

                        compositionLiveData.setValue(compositionList);
                    } else {
                        Log.e(TAG, "Can not find measurement");
                        compositionLiveData.setValue(null);
                    }
                }
            }
        });
        return compositionLiveData;
    }

    public LiveData<List<HashMap<String, Object>>> getMeasurements(String productId) {
        MutableLiveData<List<HashMap<String, Object>>> measurementsLiveData = new MutableLiveData<>();

        productRepository.getMeasurements(productId).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();

                if (document.exists()) {
                    List<HashMap<String, Object>> measurementsList = new ArrayList<>();

                    for (String attribute : document.getData().keySet()) {
                        HashMap<String, Object> attributeMap = new HashMap<>();
                        attributeMap.put(attribute, document.get(attribute));
                        measurementsList.add(attributeMap);
                    }

                    measurementsLiveData.setValue(measurementsList);
                } else {
                    Log.e(TAG, "Cannot find measurements");
                    measurementsLiveData.setValue(null);
                }
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, e.getLocalizedMessage());
        });

        return measurementsLiveData;
    }

    public LiveData<List<ProductModel>> getProductRecommendations(String productId) {
        MutableLiveData<List<ProductModel>> productModelLiveData = new MutableLiveData<>();
        List<Task<?>> tasks = new ArrayList<>();

        productRepository.getRecommendations(productId).addOnSuccessListener(task -> {
            List<DocumentSnapshot> documents = task.getDocuments();

            for (DocumentSnapshot doc : documents) {
                ProductRecommendation productRecommendation = doc.toObject(ProductRecommendation.class);
                tasks.add(productRecommendation.getProduct_ref().get());
            }

            Tasks.whenAllSuccess(tasks).addOnSuccessListener(objects -> {
                List<ProductModel> products = new ArrayList<>();

                for (Object obj : objects) {
                    if (obj instanceof DocumentSnapshot) {
                        DocumentSnapshot snapshot = (DocumentSnapshot) obj;
                        ProductModel product = snapshot.toObject(ProductModel.class);
                        if (product != null) {
                            products.add(product);
                        }
                    }
                }
                productModelLiveData.setValue(products);
            });
        });

        return productModelLiveData;
    }

    public LiveData<DocumentReference> getProductRefBbyId(String id){
        MutableLiveData<DocumentReference> liveData = new MutableLiveData<>();
        productRepository.getProductById(id).get().addOnSuccessListener(task->{
            liveData.setValue(task.getReference());
        });
        return liveData;
    }
}
