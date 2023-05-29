package com.example.evaware.data.repo;

import com.example.evaware.data.model.CategoryModel;
import com.example.evaware.data.model.TypeofCategory;
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
    private CollectionReference categoryReference;
    String typeRefValue;
    public CategoryRepository(String id){
        typeRefValue = String.format("type_of_category/%s", id);
    }


    public void loadDataByTypeRef(final OnDataFetchListener<List<CategoryModel>> listener) {
        Query query = db.collection("category").whereEqualTo("type_ref", getDocumentReference());

        // Perform the query
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                List<CategoryModel> categoryModels = new ArrayList<>();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    // Documents with matching type_ref found`

                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        // Retrieve the data
//                        String des = document.getString("des");
//                        String name = document.getString("name");
//                        String imgUrl = document.getString("img_url");
                        CategoryModel categoryModel = document.toObject(CategoryModel.class);
                        categoryModels.add(categoryModel);

                        // Process the retrieved data as needed
                    }
                    listener.onSuccess(categoryModels);
                } else {
                    // No documents with matching type_ref found;
                    listener.onSuccess(categoryModels);
                }
            } else {
                listener.onFailure(task.getException());
                // Error occurred while querying the data
            }
        });
    }

    private DocumentReference getDocumentReference() {
        String[] path = typeRefValue.split("/");

        // Create a reference to the document using the provided typeRefValue
        DocumentReference documentRef = db.collection(path[0]).document(path[1]);
        return documentRef;
    }
}
