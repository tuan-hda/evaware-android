package com.example.evaware.presentation.payment;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.evaware.data.model.PaymentMethodModel;
import com.example.evaware.data.repo.PaymentRepository;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

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
        if (list.size() != 0) {
            return data;
        }

        repo.getData().addOnSuccessListener(queryDocumentSnapshots -> {

            for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                list.add(snapshot.toObject(PaymentMethodModel.class));
            }
            data.setValue(list);
        });

        return data;
    }

    public LiveData<List<PaymentMethodModel>> getData(boolean actuallyGet) {
        if (list.size() != 0) {
            return data;
        }

        if (actuallyGet)
            repo.getData().addOnSuccessListener(queryDocumentSnapshots -> {

                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                    list.add(snapshot.toObject(PaymentMethodModel.class));
                }
                data.setValue(list);
            });

        return data;
    }


    public void forceGet() {
        list.clear();

        repo.getData().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                list.add(snapshot.toObject(PaymentMethodModel.class));
            }
            data.setValue(list);
        });
    }

    public Task<DocumentReference> add(PaymentMethodModel item) {
        return repo.add(item).addOnSuccessListener(documentReference -> {
            list.add(item);
            data.setValue(list);
        }).addOnFailureListener(e -> {
            Log.e(TAG, "add: " + e.getLocalizedMessage());
        });
    }

    public Task<Void> update(PaymentMethodModel item) {
        return repo.update(item).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                int index = -1;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).id.equals(item.id)) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    list.set(index, item);
                    data.setValue(list);
                }
            }
        });
    }
}
