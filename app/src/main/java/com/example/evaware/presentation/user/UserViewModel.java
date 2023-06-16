package com.example.evaware.presentation.user;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.evaware.data.model.BagItemModel;
import com.example.evaware.data.model.UserModel;
import com.example.evaware.data.repo.BagRepository;
import com.example.evaware.data.repo.UserRepository;
import com.example.evaware.presentation.auth.AuthViewModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private static final String TAG = "UserViewModel";
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private UserRepository repo = new UserRepository();
    private MutableLiveData<UserModel> user = new MutableLiveData<>();

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<UserModel> getUserInfo(){
        repo.getUserById(auth.getUid()).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                UserModel queryUser = task.getResult().toObject(UserModel.class);
                user.setValue(queryUser);
            }
        });
        return user;
    }



    public void updateUser(UserModel user){
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("img_url", user.img_url);
        userMap.put("name", user.name);
        userMap.put("phone", user.phone);
        userMap.put("email", user.email);
        userMap.put("dob", user.dob);

        repo.updateUser(userMap);
    }

    public Task<Uri> uploadAndGetUri(String uri){
        return repo.uploadAvt(Uri.parse(uri)).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return repo.avtRef.getDownloadUrl();
            }
        });
    }
}
