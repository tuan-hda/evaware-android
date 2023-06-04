package com.example.evaware.presentation.new_review;

import android.app.Application;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.evaware.data.model.ImageModel;
import com.example.evaware.data.model.ProductModel;
import com.example.evaware.data.model.ReviewModel;
import com.example.evaware.data.model.UserModel;
import com.example.evaware.data.repo.ReviewRepository;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReviewViewModel extends AndroidViewModel {
    private ReviewRepository repository;
    private static final String TAG = "ReviewViewModel";
    private MutableLiveData<List<ReviewModel>> reviewModelLiveData = new MutableLiveData<>();

    public ReviewViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<ReviewModel>> getAllReviewByProductId(String productId) {
        List<ReviewModel> reviews = new ArrayList<ReviewModel>();
        List<Task> tasks = new ArrayList<>();
        repository.getAllReviewByProduct(productId).addOnSuccessListener(task->{
            List<DocumentSnapshot> documents = task.getDocuments();
            for(DocumentSnapshot doc : documents) {
                try{
                    ReviewModel reviewModel = doc.toObject(ReviewModel.class);
                    Task<DocumentSnapshot> newTask = reviewModel.getUser_ref().get();
                    tasks.add(newTask);
                    reviews.add(reviewModel);
                }catch (Exception e){
                    Log.e(TAG, e.getMessage());
                }
            }
            Tasks.whenAllSuccess(tasks).addOnSuccessListener(objects -> {
                for (int i = 0; i < objects.size(); i++) {
                    UserModel userModel = ((DocumentSnapshot) objects.get(i)).toObject(UserModel.class);
                    reviews.get(i).setUser(userModel);
                }
                reviewModelLiveData.setValue(reviews);
            });
        });
        return reviewModelLiveData;
    }

    public LiveData<List<ImageModel>> getImagesByReview(String reviewId){
        List<ImageModel> images = new ArrayList<>();
        MutableLiveData<List<ImageModel>> imageModelLiveData = new MutableLiveData<>();
        repository.getImagesByReview(reviewId).addOnSuccessListener(task->{
            List<DocumentSnapshot> docs = task.getDocuments();
            for(DocumentSnapshot doc : docs){
                ImageModel image = doc.toObject(ImageModel.class);
                images.add(image);
            }
            imageModelLiveData.setValue(images);
        });
        return imageModelLiveData;
    }

    public void postReview(ReviewModel model, List<Uri> uriList, String productName) {
        repository.postReview(model).addOnSuccessListener(documentReference -> {
            if(uriList.size() > 0){
                uploadImageToFireStorage(uriList, productName, documentReference.getId());
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Failed to post the review", e);
        });
    }

    public void setReviewRef(String productId) {
        repository = new ReviewRepository(productId);
    }

    public void uploadImageToFireStorage(List<Uri> uriList, String productName, String reviewId) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("review/product/");

        for (int i = 0; i < uriList.size(); i++) {
            Uri imageUri = uriList.get(i);
            String fileName = "review_" + reviewId + "_image_" + i + ".jpg";
            StorageReference imageRef = storageRef.child(fileName);

            UploadTask uploadTask = imageRef.putFile(imageUri);
            uploadTask.addOnSuccessListener(taskSnapshot -> {
                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String downloadUrl = uri.toString();

                    Log.d(TAG, "Image upload successful. Download URL: " + downloadUrl);
                    ImageModel imageModel = new ImageModel();
                    imageModel.setImg_url(downloadUrl);
                    repository.addImage(imageModel, reviewId);
                }).addOnFailureListener(e -> {

                    Log.e(TAG, "Failed to retrieve download URL for image", e);
                });
            }).addOnFailureListener(e -> {
                Log.e(TAG, "Image upload failed", e);
            });
        }
    }
}
