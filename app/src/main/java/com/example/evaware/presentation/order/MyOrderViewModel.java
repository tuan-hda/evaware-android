package com.example.evaware.presentation.order;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.evaware.data.model.AddressModel;
import com.example.evaware.data.model.BagItemModel;
import com.example.evaware.data.model.OrderModel;
import com.example.evaware.data.model.PaymentMethodModel;
import com.example.evaware.data.model.ProductModel;
import com.example.evaware.data.model.VariationModel;
import com.example.evaware.data.model.VoucherModel;
import com.example.evaware.data.repo.OrderRepository;
import com.example.evaware.data.repo.UserRepository;
import com.example.evaware.presentation.bag.BagViewModel;
import com.example.evaware.presentation.checkout.ConfirmOrderActivity;
import com.example.evaware.presentation.checkout.SuccessActivity;
import com.example.evaware.utils.LoadingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyOrderViewModel extends AndroidViewModel {
    private static final String TAG = "MyOrderViewModel";
    private final OrderRepository repo = new OrderRepository();
    private final UserRepository user_repo;
    private Application activity;

    public MyOrderViewModel(@NonNull Application application) {
        super(application);
        this.activity = application;
        user_repo = new UserRepository();
    }

    private MutableLiveData<List<OrderModel>> data = new MutableLiveData<>();
    private final List<OrderModel> list = new ArrayList<>();

    public void makeOrder(List<BagItemModel> bagItemModels, AddressModel addressModel, PaymentMethodModel paymentMethodModel, VoucherModel voucherModel, Double total, BagViewModel bagViewModel, LoadingDialog dialog) {
        String payment = paymentMethodModel.account_no.substring(12, 16) + " " + paymentMethodModel.provider + " " + paymentMethodModel.name;

        final Boolean[] flag = {true};

        List<Task<?>> tasks = new ArrayList<>();
        for (BagItemModel model : bagItemModels) {
            Task task = model.variation_ref.get().addOnSuccessListener(documentSnapshot -> {
                VariationModel variationModel = documentSnapshot.toObject(VariationModel.class);
                if (variationModel.inventory < model.qty) {
                    model.qty = variationModel.inventory;
                    FirebaseFirestore.getInstance().document(model.path).set(model).addOnSuccessListener(unused -> {
                        bagViewModel.forceGet();
                    });
                    Toast.makeText(activity, "Not enough inventory. Reset qty.", Toast.LENGTH_SHORT).show();
                    flag[0] = false;
                    dialog.dismissDialog();
                }
            });
            tasks.add(task);
        }

        Tasks.whenAll(tasks).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismissDialog();
            }
        }).addOnSuccessListener(objects -> {
            if (!flag[0]) {
                return;
            }

            OrderModel orderModel = new OrderModel(
                    user_repo.userDocRef,
                    addressModel.name,
                    addressModel.phone,
                    addressModel.email,
                    addressModel.province,
                    addressModel.province_id,
                    addressModel.district,
                    addressModel.district_id,
                    addressModel.ward,
                    addressModel.ward_id,
                    addressModel.street,
                    payment,
                    0,
                    total
            );

            if (voucherModel != null) {
                orderModel.voucher_code = voucherModel.code;
                orderModel.voucher_percent = voucherModel.percent;
            }

            repo.addOrder(orderModel).addOnSuccessListener(documentReference -> {
                documentReference.get().addOnSuccessListener(documentSnapshot -> {
                    OrderModel model = documentSnapshot.toObject(OrderModel.class);
                    addOrderItems(model, bagItemModels).addOnCompleteListener(obj -> {
                        list.add(model);
                        data.setValue(list);
                        Log.e(TAG, "makeOrder: " + " triggered here");
                        dialog.dismissDialog();
                        Intent intent = new Intent(activity, SuccessActivity.class);
                        intent.putExtra("fragment", "orders");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);

                        if (voucherModel != null) {
                            user_repo.userDocRef.collection("used_vouchers").add(voucherModel);
                        }

                        bagViewModel.clearBag();
                    });
                });
            });
        });
    }

    private Task<List<Object>> addOrderItems(OrderModel orderModel, List<BagItemModel> bagItemModels) {
        List<Task> tasks = new ArrayList<>();
        List<BagItemModel> bagItemModelList = new ArrayList<>();

        for (BagItemModel model : bagItemModels) {
            model.price = model.product.getPrice();
            Task<DocumentReference> task = repo.addOrderItem(orderModel.id, model)
                    .addOnSuccessListener(documentReference -> {
                        Task<DocumentSnapshot> task1 = documentReference.get()
                                .addOnSuccessListener(documentSnapshot -> {
                                    BagItemModel bagItemModel = documentSnapshot.toObject(BagItemModel.class);
                                    bagItemModelList.add(bagItemModel);
                                });
                        tasks.add(task1);
                    });

            VariationModel variationModel = model.variation;
            variationModel.inventory -= model.qty;
            if (variationModel.inventory < 0) {
                variationModel.inventory = 0;
            }
            ProductModel productModel = model.product;
            FirebaseFirestore.getInstance().collection("products").document(productModel.id).collection("variations").document(variationModel.id)
                    .set(variationModel);
            tasks.add(task);
        }

        return Tasks.whenAllSuccess(tasks).addOnSuccessListener(objects -> {
            orderModel.order_items = bagItemModelList;
        });
    }

    public LiveData<List<OrderModel>> getOrderOfUser(String userId) {
        repo.getOrdersOfUser(userId)
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    list.clear();

                    for (DocumentSnapshot orderDoc : queryDocumentSnapshots.getDocuments()) {
                        OrderModel order = orderDoc.toObject(OrderModel.class);

                        repo.getOrderItem(orderDoc.getReference())
                                .addOnSuccessListener(task -> {
                                    List<BagItemModel> itemModels = new ArrayList<>();
                                    for (DocumentSnapshot itemDoc : task.getDocuments()) {
                                        BagItemModel bagItem = itemDoc.toObject(BagItemModel.class);
                                        itemModels.add(bagItem);
                                    }
                                    order.order_items = itemModels;
                                    list.add(order);
                                    data.setValue(list);
                                })
                                .addOnFailureListener(e -> {
                                    Log.e(TAG, "getOrderItem:failed", e);
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "getOrderOfUser: Failed to get orders", e);
                });

        return data;
    }

}
