package com.example.evaware.presentation.filter;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.evaware.data.model.CategoryModel;
import com.example.evaware.data.model.VariationModel;

import java.util.List;

public class FilterViewModel extends AndroidViewModel {
    public MutableLiveData<List<VariationModel>> selectVariations = new MutableLiveData<>();
    public MutableLiveData<List<CategoryModel>> selectCategories = new MutableLiveData<>();

    public FilterViewModel(@NonNull Application application) {
        super(application);
    }

    public void setCategories(List<CategoryModel> categoryModels) {
        selectCategories.setValue(categoryModels);
    }

    public void setVariations(List<VariationModel> variations) {
        selectVariations.setValue(variations);
    }
}

