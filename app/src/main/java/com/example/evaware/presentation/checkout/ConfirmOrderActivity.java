package com.example.evaware.presentation.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.evaware.data.model.AddressModel;
import com.example.evaware.data.model.PaymentMethodModel;
import com.example.evaware.data.model.VoucherModel;
import com.example.evaware.data.repo.UserRepository;
import com.example.evaware.databinding.ActivityConfirmOrderBinding;
import com.example.evaware.presentation.bag.BagListAdapter;
import com.example.evaware.presentation.bag.BagViewModel;
import com.example.evaware.presentation.order.MyOrderViewModel;
import com.example.evaware.utils.CurrencyFormat;
import com.example.evaware.utils.LoadingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ConfirmOrderActivity extends AppCompatActivity {
    ActivityConfirmOrderBinding binding;
    BagListAdapter adapter;
    private BagViewModel viewModel;
    private MyOrderViewModel myOrderViewModel;
    private VoucherModel voucher;
    private FirebaseFirestore firestore;
    private Double subtotal;
    private Boolean applied = false;
    private AddressModel address;
    private PaymentMethodModel payment;
    private LoadingDialog dialog;
    private UserRepository user_repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityConfirmOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(BagViewModel.class);
        myOrderViewModel = new ViewModelProvider(this).get(MyOrderViewModel.class);
        firestore = FirebaseFirestore.getInstance();
        user_repo = new UserRepository();

        dialog = new LoadingDialog(this);
        address = (AddressModel) getIntent().getSerializableExtra("address");
        payment = (PaymentMethodModel) getIntent().getSerializableExtra("payment");

        setUpSelected();
        setUpAppBar();
        setUpBagList();
        setUpDeliveryAddress();
        setUpPaymentMethod();
        setUpButtons();
    }

    private void setUpButtons() {

        binding.btnPay.setOnClickListener(view -> {
            dialog.showDialog();

            Double total = Double.parseDouble(binding.textTotalPrice.getText().toString().substring(1));
            myOrderViewModel.makeOrder(adapter.getBagList(), address, payment, voucher, total, viewModel, dialog);
        });

        updateClickApply();
    }

    private void setUpSelected() {
        String addressStr = address.province + ", " + address.district + ", " + address.ward + "," +
                " " + address.street;
        String contactStr = address.name + ", " + address.phone;
        binding.deliveryAddress.textCard.setText(addressStr);
        binding.deliveryAddress.textContact.setText(contactStr);

        binding.paymentMethod.textCard.setText(payment.account_no.substring(12, 16) + " " + payment.provider);
        Picasso.with(this).load(payment.logo).into(binding.paymentMethod.logo);
        binding.paymentMethod.textExp.setText(payment.exp);
    }

    private void updateClickApply() {
        if (!applied) {
            binding.btnApply.setOnClickListener(view -> {
                checkVoucher();
            });
        } else {
            binding.btnApply.setOnClickListener(v -> {
                voucher = null;
                applied = false;
                applyVoucher();
                binding.edtPromo.setError(null);
                binding.btnApply.setText("Apply");
            });
        }
    }

    private void setUpAppBar() {
        binding.btnBack.setOnClickListener(view -> {
            finish();
        });
    }

    private void setUpPaymentMethod() {
        binding.paymentMethod.indicator.setVisibility(View.GONE);
    }

    private void setUpDeliveryAddress() {
        binding.deliveryAddress.indicator.setVisibility(View.GONE);
    }

    private void setUpBagList() {
        viewModel.getBagList().observe(this, bagItemModels -> {
            Double calcSubtotal = 0.0;

            adapter = new BagListAdapter(this, bagItemModels, true);
            binding.listBagItem.removeAllViews();
            for (int i = 0; i < bagItemModels.size(); i++) {
                binding.listBagItem.addView(adapter.getView(i, null, binding.listBagItem));
                calcSubtotal += bagItemModels.get(i).getDiscountPrice();
            }
            binding.textNumSub.setText(CurrencyFormat.getFormattedPrice(calcSubtotal));
            binding.textTotalPrice.setText(CurrencyFormat.getFormattedPrice(calcSubtotal + 10));
            binding.btnPay.setText("Pay " + CurrencyFormat.getFormattedPrice(calcSubtotal + 10));
            subtotal = calcSubtotal;
        });
    }

    private void checkVoucher() {
        firestore.collection("vouchers").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Boolean flag = false;
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                    VoucherModel voucherModel = snapshot.toObject(VoucherModel.class);
                    if (Objects.equals(voucherModel.code, binding.edtPromo.getText().toString())) {
                        voucher = voucherModel;
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    applied = true;
                } else {
                    applied = false;
                }

                user_repo.userDocRef.collection("used_vouchers").get().addOnSuccessListener(queryDocumentSnapshots1 -> {
                    Boolean flag1 = false;
                    for (QueryDocumentSnapshot snapshot: queryDocumentSnapshots1) {
                        VoucherModel tempVoucher = snapshot.toObject(VoucherModel.class);
                        if (Objects.equals(tempVoucher.code, voucher.code)) {
                            flag1 = true;
                            binding.edtPromo.setError("Already used this voucher");
                            voucher = null;
                            break;
                        }
                    }
                    if (!flag1) {
                        binding.edtPromo.setError(null);
                        updateTextDiscount();
                        applyVoucher();
                    }
                });

            }
        });
    }

    private void updateTextDiscount() {
        if (applied) {
            binding.btnApply.setText("Cancel");
            binding.edtPromo.setError(null);
        } else {
            binding.btnApply.setText("Apply");
            binding.edtPromo.setError("Invalid voucher");
        }
    }

    private void applyVoucher() {
        if (voucher != null) {
            Double discountAmount = subtotal * (voucher.percent / 100.0);
            binding.textPromo.setVisibility(View.VISIBLE);
            binding.textPromoDiscount.setVisibility(View.VISIBLE);
            binding.textPromoDiscount.setText(CurrencyFormat.getFormattedPrice(discountAmount));
            binding.textTotalPrice.setText(CurrencyFormat.getFormattedPrice(subtotal - discountAmount + 10));
            binding.btnPay.setText("Pay " + CurrencyFormat.getFormattedPrice(subtotal - discountAmount + 10));

        } else {
            binding.textPromo.setVisibility(View.GONE);
            binding.textPromoDiscount.setVisibility(View.GONE);
            binding.textTotalPrice.setText(CurrencyFormat.getFormattedPrice(subtotal + 10));
            binding.btnPay.setText("Pay " + CurrencyFormat.getFormattedPrice(subtotal + 10));
        }
        updateClickApply();
    }


}