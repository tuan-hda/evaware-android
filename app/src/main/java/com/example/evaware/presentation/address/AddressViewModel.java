package com.example.evaware.presentation.address;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.evaware.data.model.AddressModel;
import com.example.evaware.data.repo.AddressRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class AddressViewModel extends AndroidViewModel {
    private static final String TAG = "AddressViewModel";
    private final AddressRepository repo = new AddressRepository();

    public AddressViewModel(@NonNull Application application) {
        super(application);
    }

    private List<AddressModel> list = new ArrayList<>();
    private MutableLiveData<List<AddressModel>> data = new MutableLiveData<>();

    public MutableLiveData<List<AddressModel>> getData() {
        if (list.size() != 0) {
            return data;
        }

        repo.getData().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                AddressModel item = snapshot.toObject(AddressModel.class);
                item.path = snapshot.getReference().getPath();
                list.add(item);
            }
            data.setValue(list);
        });

        return data;
    }

    public void add(AddressModel item) {
        repo.add(item).addOnSuccessListener(documentReference -> {
            list.add(item);
            data.setValue(list);
        }).addOnFailureListener(e -> {
            Log.e(TAG, "add: " + e.getLocalizedMessage());
        });
    }

    public void update(int i, AddressModel item) {
        repo.update(item).addOnSuccessListener(unused -> {
            list.set(i, item);
            data.setValue(list);
        }).addOnFailureListener(e -> {
            Log.e(TAG, "update: " + e.getLocalizedMessage());
        });
    }

    public void delete(int i) {
        repo.delete(list.get(i)).addOnSuccessListener(unused -> {
            list.remove(i);
            data.setValue(list);
        });
    }
}
