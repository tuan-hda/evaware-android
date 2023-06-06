package com.example.evaware.data.repo;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class TypeOfCategoryRepo {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference typeOfCategories;

    public TypeOfCategoryRepo() {
        typeOfCategories = db.collection("type_of_category");
    }

    public Task<QuerySnapshot> getTypeOfCategoryList(){
        return typeOfCategories.get();
    }
    public DocumentReference getById(String id){
        return typeOfCategories.document(id);
    }
}
