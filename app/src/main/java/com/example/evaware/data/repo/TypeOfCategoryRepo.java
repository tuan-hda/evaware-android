package com.example.evaware.data.repo;

import androidx.annotation.NonNull;

import com.example.evaware.data.model.TypeofCategory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TypeOfCategoryRepo {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference typeOfCategories;

    public TypeOfCategoryRepo() {
        typeOfCategories = db.collection("type_of_category");
    }
    public void getTypeOfCategories(final OnDataFetchListener<List<TypeofCategory>> listener) {
        typeOfCategories.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<TypeofCategory> typeOfCategoryList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        TypeofCategory typeOfCategory = document.toObject(TypeofCategory.class);
                        typeOfCategoryList.add(typeOfCategory);
                    }
                    listener.onSuccess(typeOfCategoryList); // Pass the retrieved data to the listener
                } else {
                    listener.onFailure(task.getException()); // Pass the exception to the listener
                }
            }
        });
    }
}
