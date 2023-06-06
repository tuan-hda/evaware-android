package com.example.evaware.data.repo;

import android.util.Log;

import com.example.evaware.data.model.CategoryModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference categoryRef;
    public CategoryRepository(){
        categoryRef = db.collection("category");
    }

    public Task<QuerySnapshot> getCategoriesByType(DocumentReference documentReference) {
        return categoryRef.whereEqualTo("type_ref", documentReference).get();
    }
    public DocumentReference getCategoriesById(String id){
        return categoryRef.document(id);
    }

}
