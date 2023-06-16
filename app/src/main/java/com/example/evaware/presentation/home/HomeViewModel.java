package com.example.evaware.presentation.home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.evaware.data.model.BagItemModel;
import com.example.evaware.data.model.CategoryModel;
import com.example.evaware.data.model.TypeofCategory;
import com.example.evaware.data.repo.CategoryRepository;
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
    private CategoryRepository categoryRepo = new CategoryRepository();
    private final List<TypeofCategory> queryTypeOfCategoryList = new ArrayList<>();
    private final List<CategoryModel> queryCategoryList = new ArrayList<>();
    private MutableLiveData<List<TypeofCategory>> typeOfCategoryList = new MutableLiveData<>();
    private MutableLiveData<List<CategoryModel>> categoryLiveData = new MutableLiveData<>();

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<TypeofCategory>> getListTypeOfCategories() {
        if (queryTypeOfCategoryList.size() != 0) {
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

    public LiveData<List<CategoryModel>> getAllCategory() {
        if (queryCategoryList.size() != 0) {
            return categoryLiveData;
        }
        categoryRepo.getAllCategories().addOnSuccessListener(task -> {
            List<DocumentSnapshot> snapshots = task.getDocuments();
            for (DocumentSnapshot document : snapshots) {
                try {
                    CategoryModel categoryModel = document.toObject(CategoryModel.class);
                    queryCategoryList.add(categoryModel);
                } catch (Exception e) {
                    Log.d("dcm", "getAllCategory: " + e.getMessage());
                }


            }
            categoryLiveData.setValue(queryCategoryList);
        });
        return categoryLiveData;
    }
}
