package com.example.evaware.presentation.wishlist;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.evaware.data.model.ProductModel;
import com.example.evaware.data.model.SavedModel;
import com.example.evaware.data.model.WishItemModel;
import com.example.evaware.data.repo.WishlistRepository;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class WishViewModel extends AndroidViewModel {
    private static final String TAG = "WishListViewModel";
    private WishlistRepository repository = new WishlistRepository();
    MutableLiveData<List<SavedModel>> savedListLiveData = new MutableLiveData<>();

    public WishViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<WishItemModel>> getWishList() {
        MutableLiveData<List<WishItemModel>> wishListLiveData = new MutableLiveData<>();
        List<WishItemModel> wishList = new ArrayList<WishItemModel>();
        repository.getAllWish().addOnSuccessListener(task -> {
            List<DocumentSnapshot> docs = task.getDocuments();
            for (DocumentSnapshot doc : docs) {
                WishItemModel wishItemModel = doc.toObject(WishItemModel.class);
                wishList.add(wishItemModel);
            }
            wishListLiveData.setValue(wishList);
        });
        return wishListLiveData;
    }

    public LiveData<List<SavedModel>> getSavedList() {
        repository.getAllWish().addOnSuccessListener(task -> {
            List<Task<SavedModel>> tasks = new ArrayList<>();

            for (DocumentSnapshot doc : task.getDocuments()) {
                WishItemModel wishItemModel = doc.toObject(WishItemModel.class);

                TaskCompletionSource<SavedModel> taskCompletionSource = new TaskCompletionSource<>();
                Task<SavedModel> future = taskCompletionSource.getTask();
                tasks.add(future);

                wishItemModel.getProduct_ref().get().addOnSuccessListener(snapshot -> {
                    ProductModel product = snapshot.toObject(ProductModel.class);
                    SavedModel item = new SavedModel(doc.getReference(), wishItemModel.getProduct_ref(), product.image_thumbnail, product.name, (Double) product.price, product.desc);
                    taskCompletionSource.setResult(item);
                }).addOnFailureListener(e -> {
                    Log.e(TAG, "getProductInfo: failure" + e.getLocalizedMessage());
                    taskCompletionSource.setException(e);
                });
            }

            Task<List<SavedModel>> allTasks = Tasks.whenAllSuccess(tasks);

            allTasks.addOnSuccessListener(savedModelsResult -> {
                savedListLiveData.setValue(savedModelsResult);
            }).addOnFailureListener(e -> {
                Log.e(TAG, "getAllWishList: failure" + e.getLocalizedMessage());
            });
        }).addOnFailureListener(e -> {
            Log.e(TAG, "getAllWishList: failure" + e.getLocalizedMessage());
        });

        return savedListLiveData;
    }

    public LiveData<String> addWishList(WishItemModel wishItemModel) {
        MutableLiveData<String> wishIdLiveData = new MutableLiveData<String>();
        repository.addToWishList(wishItemModel).addOnSuccessListener(task -> {
            String documentId = task.getId();
            wishIdLiveData.setValue(documentId);
        });
        return wishIdLiveData;
    }

    public LiveData<String> remove(String wishItemId) {
        return remove(wishItemId, null);
    }
    public LiveData<String> remove(String wishItemId, Snackbar snackbar) {
        MutableLiveData<String> message = new MutableLiveData<>();

        repository.removeWish(wishItemId).addOnSuccessListener(task -> {
            message.setValue("Wish item removed successfully");

            List<SavedModel> currentList = savedListLiveData.getValue();
            if (currentList != null) {
                for (int i = 0; i < currentList.size(); i++) {
                    SavedModel savedModel = currentList.get(i);
                    if (savedModel.wishListRef.getId().equals(wishItemId)) {
                        currentList.remove(i);
                        break;
                    }
                }
                savedListLiveData.setValue(currentList);
            }

            if(snackbar != null){
                snackbar.dismiss();
            }
        }).addOnFailureListener(e -> {
            // Handle any failure that occurred during the removeWish() operation
            message.setValue("Error removing wish item: " + e.getMessage());

            if(snackbar != null){
                snackbar.dismiss();
            }
        });

        return message;
    }

}
