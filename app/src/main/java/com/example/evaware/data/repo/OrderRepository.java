package com.example.evaware.data.repo;

import android.util.Log;

import com.example.evaware.data.model.BagItemModel;
import com.example.evaware.data.model.OrderModel;
import com.example.evaware.data.model.WishItemModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class OrderRepository {
    private static final String TAG = "OrderRepository";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference orderRef;

    public OrderRepository() {
        this.orderRef = db.collection("orders");
    }

    public Task<DocumentReference> addOrder(OrderModel orderModel) {
        return orderRef.add(orderModel).addOnFailureListener(e -> {
            Log.e(TAG, "addOrder:failed", e);
        });
    }

    public Task<DocumentReference> addOrderItem(String orderId, BagItemModel orderItemModel) {
        return orderRef.document(orderId).collection("order_items").add(orderItemModel).addOnFailureListener(e ->
                {
                    Log.e(TAG, "addOrder:failed" + e.getLocalizedMessage());
                }
        );
    }

    public Task<QuerySnapshot> getOrdersOfUser(String userId) {
        DocumentReference userRef = db.collection("users").document(userId);
        return orderRef.whereEqualTo("user_ref", userRef).get().addOnFailureListener(e->{
            Log.e(TAG, "getOrdersOfUser:failed" + e.getLocalizedMessage());
        });
    }

    public Task<QuerySnapshot> getOrderItem(DocumentReference orderRef) {
        return orderRef.collection("order_items").get().addOnFailureListener(e->{
            Log.e(TAG, "getOrderItem:failed" + e.getLocalizedMessage());
        });
    }
}
