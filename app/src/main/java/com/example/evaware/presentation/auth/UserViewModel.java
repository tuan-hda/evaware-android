package com.example.evaware.presentation.auth;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.evaware.data.model.ProductModel;
import com.example.evaware.data.model.UserModel;
import com.example.evaware.data.repo.UserRepository;
import com.example.evaware.utils.ConvertString;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserViewModel extends AndroidViewModel {
    private static final String TAG = "UserViewModel";

    private UserRepository userRep;
    private MutableLiveData<UserModel> userLiveData = new MutableLiveData<UserModel>();
    private MutableLiveData<DocumentReference> docLiveData = new MutableLiveData<DocumentReference>();

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRep = new UserRepository();
    }

    public void createUser(String userId, String email) {
        String username = email.substring(0, email.indexOf("@"));
        UserModel user = new UserModel(userId, email, username, "https://i.pinimg.com/originals/01/17/65/01176528b26e5b24da0aa0d85b8c41ef.jpg");
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("email", user.getEmail());
        userMap.put("name", user.getName());
        userMap.put("img_url", user.getImg_url());

        userRep.createUser(userMap, userId);
    }


    public LiveData<DocumentReference> getUserRefById() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        userRep.getUserById(userId).addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                DocumentReference userRef = documentSnapshot.getReference();
                docLiveData.setValue(userRef);
            } else {
                // Document doesn't exist
            }
        });
        return docLiveData;
    }

}
