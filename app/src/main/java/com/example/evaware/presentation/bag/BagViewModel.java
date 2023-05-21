package com.example.evaware.presentation.bag;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.evaware.data.model.BagItemModel;
import com.example.evaware.data.model.ProductModel;
import com.example.evaware.data.model.VariationModel;
import com.example.evaware.data.repo.BagRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BagViewModel extends AndroidViewModel {
    private static final String TAG = "BagViewModel";
    private BagRepository repo = new BagRepository();
    private MutableLiveData<List<BagItemModel>> bagList = new MutableLiveData<>();
    private final List<BagItemModel> queryBagList = new ArrayList<>();

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public BagViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<BagItemModel>> getBagList() {
        List<Task> tasks = new ArrayList<>();

        if (queryBagList.size() != 0) {
            return bagList;
        }

        repo.getBagList().addOnSuccessListener(task -> {
            for (DocumentSnapshot document : task.getDocuments()) {
                BagItemModel item = document.toObject(BagItemModel.class);
                queryBagList.add(item);
                item.path = document.getReference().getPath();
                Task<DocumentSnapshot> t1 = item.product_ref.get();
                Task<DocumentSnapshot> t2 = item.variation_ref.get();
                tasks.add(t1);
                tasks.add(t2);
            }
            Tasks.whenAllSuccess(tasks).addOnSuccessListener(objects -> {
                for (int i = 0; i < objects.size(); i++) {
                    int idx = i / 2;
                    if (i % 2 == 0) {
                        queryBagList.get(idx).product = ((DocumentSnapshot) objects.get(i)).toObject(ProductModel.class);
                    } else {
                        queryBagList.get(idx).variation = ((DocumentSnapshot) objects.get(i)).toObject(VariationModel.class);
                    }
                }
                bagList.setValue(queryBagList);
            });
        }).addOnFailureListener(e -> {
            Log.d(TAG, "getBagList:failure: " + e.getLocalizedMessage());
        });

        return bagList;
    }


    public void changeQty(int i, int value) {
        BagItemModel item = queryBagList.get(i);
        int newVal = item.qty + value;
        if (newVal >= 1) {
            item.qty = newVal;
            repo.updateBagItem(db.document(item.path), item).addOnSuccessListener(unused -> {
               bagList.setValue(queryBagList);
            });
        }
    }

    public void removeItem(int i, Snackbar snackbar) {
        repo.removeBagItem(db.document(Objects.requireNonNull(bagList.getValue()).get(i).path)).addOnSuccessListener(unused -> {
            queryBagList.remove(i);
            snackbar.dismiss();
        });
    }

    public void addItem(BagItemModel item) {
        repo.add(item).addOnSuccessListener(documentReference -> {
            queryBagList.add(item);
        });
    }
}
