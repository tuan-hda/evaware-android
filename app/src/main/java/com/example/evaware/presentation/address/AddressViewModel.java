package com.example.evaware.presentation.address;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.evaware.data.model.AddressModel;
import com.example.evaware.data.repo.AddressRepository;
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
                list.add(snapshot.toObject(AddressModel.class));
            }
            data.setValue(list);
        });

        return data;
    }
}
