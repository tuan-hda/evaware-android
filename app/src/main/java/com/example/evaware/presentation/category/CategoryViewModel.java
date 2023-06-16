package com.example.evaware.presentation.category;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.evaware.data.model.CategoryModel;
import com.example.evaware.data.repo.CategoryRepository;
import com.example.evaware.data.repo.TypeOfCategoryRepo;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private CategoryRepository repo = new CategoryRepository();
    private static final String TAG = "CategoryViewModel";
    private TypeOfCategoryRepo typeOfCategoryRepo = new TypeOfCategoryRepo();
    private List<CategoryModel> queryCategoryList = new ArrayList<CategoryModel>();
    private MutableLiveData<List<CategoryModel>> categoriesLiveData = new MutableLiveData<List<CategoryModel>>();

    private List<CategoryModel> categories = new ArrayList<>();

    public CategoryViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<CategoryModel>> getCategoryByType(String typeOfCategoryPath) {
        DocumentReference typeOfCategory = getTypeOfCategory(typeOfCategoryPath);

        if (categories.size() != 0) {
            return categoriesLiveData;
        }
        repo.getCategoriesByType(typeOfCategory).addOnSuccessListener(task -> {
            List<DocumentSnapshot> snapshots = task.getDocuments();
            for (DocumentSnapshot snapshot : snapshots) {
                CategoryModel categoryModel = snapshot.toObject(CategoryModel.class);
                categories.add(categoryModel);
            }
            categoriesLiveData.setValue(categories);
        }).addOnFailureListener(e -> {
            Log.d(TAG, "getCategoryList:failure: " + e.getLocalizedMessage());
        });
        return categoriesLiveData;
    }

    private DocumentReference getTypeOfCategory(String id) {
        return typeOfCategoryRepo.getById(id);
    }

    public LiveData<List<CategoryModel>> getAllCategory() {
        if (queryCategoryList.size() != 0) {
            return categoriesLiveData;
        }
        repo.getAllCategories().addOnSuccessListener(task -> {
            List<DocumentSnapshot> snapshots = task.getDocuments();
            for (DocumentSnapshot document : snapshots) {
                try {
                    CategoryModel categoryModel = document.toObject(CategoryModel.class);
                    queryCategoryList.add(categoryModel);
                } catch (Exception e) {
                }


            }
            categoriesLiveData.setValue(queryCategoryList);
        });
        return categoriesLiveData;
    }

}
