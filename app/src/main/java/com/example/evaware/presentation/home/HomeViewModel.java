package com.example.evaware.presentation.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.evaware.data.model.BagItemModel;
import com.example.evaware.data.model.TypeofCategory;
import com.example.evaware.data.repo.TypeOfCategoryRepo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private TypeOfCategoryRepo repo = new TypeOfCategoryRepo();
    private final List<TypeofCategory> queryTypeOfCategoryList = new ArrayList<>();
    private MutableLiveData<List<TypeofCategory>> typeOfCategoryList = new MutableLiveData<>();

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<TypeofCategory>> getListTypeOfCategories(){
        if(queryTypeOfCategoryList.size() != 0){
            return typeOfCategoryList;
        }
        repo.getTypeOfCategoryList().addOnSuccessListener(task -> {
            for (DocumentSnapshot document : task.getDocuments()) {
                TypeofCategory item = document.toObject(TypeofCategory.class);
                queryTypeOfCategoryList.add(item);
            }
            typeOfCategoryList.setValue(queryTypeOfCategoryList);
        });
        return typeOfCategoryList;
    }

}
