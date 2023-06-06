package com.example.evaware.presentation.wishlist;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.evaware.data.model.WishItemModel;
import com.example.evaware.data.repo.WishlistRepository;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class WishViewModel extends AndroidViewModel {
    private static final String TAG = "WishListViewModel";
    private WishlistRepository repository = new WishlistRepository();

    public WishViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<List<WishItemModel>> getWishList(){
        MutableLiveData<List<WishItemModel>> wishListLiveData = new MutableLiveData<>();
        List<WishItemModel> wishList = new ArrayList<WishItemModel>();
        repository.getAllWish().addOnSuccessListener(task->{
            List<DocumentSnapshot> docs = task.getDocuments();
            for(DocumentSnapshot doc : docs){
                WishItemModel wishItemModel = doc.toObject(WishItemModel.class);
                wishList.add(wishItemModel);
            }
            wishListLiveData.setValue(wishList);
        });
        return wishListLiveData;
    }
    public LiveData<String> addWishList(WishItemModel wishItemModel){
        MutableLiveData<String> wishIdLiveData = new MutableLiveData<String>();
        repository.addToWishList(wishItemModel).addOnSuccessListener(task->{
            String documentId = task.getId();
            wishIdLiveData.setValue(documentId);
        });
        return wishIdLiveData;
    }
    public LiveData<String> remove(String wishItemId) {
        MutableLiveData<String> message = new MutableLiveData<>();

        repository.removeWish(wishItemId).addOnSuccessListener(task -> {
            // You need to provide a value to setValue() method
            message.setValue("Wish item removed successfully");
        }).addOnFailureListener(e -> {
            // Handle any failure that occurred during the removeWish() operation
            message.setValue("Error removing wish item: " + e.getMessage());
        });

        return message;
    }

}
