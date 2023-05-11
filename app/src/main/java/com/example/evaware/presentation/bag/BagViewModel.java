package com.example.evaware.presentation.bag;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.evaware.data.model.BagItemModel;
import com.example.evaware.data.repo.BagRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class BagViewModel extends AndroidViewModel {
    private static final String TAG = "BagViewModel";
    private BagRepository repo = new BagRepository("RGJv21rBXou2UBR4dtSn");
    private MutableLiveData<List<BagItemModel>> bagList = new MutableLiveData<>();

    public BagViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<BagItemModel>> getBagList() {
        List<BagItemModel> queryBagList = new ArrayList<>();

        repo.getBagList().addOnSuccessListener(task -> {
            for (DocumentSnapshot document : task.getDocuments()) {
                queryBagList.add(document.toObject(BagItemModel.class));
            }

            bagList.setValue(queryBagList);
        }).addOnFailureListener(e -> {
            Log.d(TAG, "getBagList:failure: " + e.getLocalizedMessage());
        });

        return bagList;
    }
}
