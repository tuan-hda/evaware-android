package com.example.evaware.presentation.payment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.evaware.data.model.PaymentMethodModel;
import com.example.evaware.data.repo.PaymentRepository;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PaymentViewModel extends AndroidViewModel {
    private static final String TAG = "PaymentVIewModel";
    private PaymentRepository repo = new PaymentRepository();
    private List<PaymentMethodModel> list = new ArrayList<>();
    private MutableLiveData<List<PaymentMethodModel>> data = new MutableLiveData<>();

    public PaymentViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<PaymentMethodModel>> getData() {
        if (list.size()!=0) {
            return data;
        }

        repo.getData().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot snapshot: queryDocumentSnapshots) {
                list.add(snapshot.toObject(PaymentMethodModel.class));
            }
            data.setValue(list);
        });

        return data;
    }
}
