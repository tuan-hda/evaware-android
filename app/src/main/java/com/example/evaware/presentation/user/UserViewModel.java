package com.example.evaware.presentation.user;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.evaware.data.model.BagItemModel;
import com.example.evaware.data.model.UserModel;
import com.example.evaware.data.repo.BagRepository;
import com.example.evaware.data.repo.UserRepository;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private static final String TAG = "UserViewModel";
    private UserRepository repo = new UserRepository();
    private MutableLiveData<UserModel> user = new MutableLiveData<>();

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<UserModel> getUserInfo(){
        repo.getUserInfo().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserModel queryUser = documentSnapshot.toObject(UserModel.class);
                user.setValue(queryUser);
            }
        });
        return user;
    }

    public void updateRank(String value){
        repo.updateUserStringField("rank", value);
    }
    public void updateFirstName(String value){
        repo.updateUserStringField("first_name", value);
    }
    public void updateLastName(String value){
        repo.updateUserStringField("last_name", value);
    }
}
