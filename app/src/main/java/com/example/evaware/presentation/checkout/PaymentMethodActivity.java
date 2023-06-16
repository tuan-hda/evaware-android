package com.example.evaware.presentation.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.evaware.data.model.PaymentMethodModel;
import com.example.evaware.databinding.ActivityPaymentMethodBinding;
import com.example.evaware.presentation.payment.PaymentListActivity;
import com.example.evaware.presentation.payment.PaymentListAdapter;
import com.example.evaware.presentation.payment.PaymentViewModel;
import com.example.evaware.utils.LinearScrollListView;
import com.example.evaware.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethodActivity extends AppCompatActivity {
    private ActivityPaymentMethodBinding binding;
    private PaymentViewModel viewModel;
    private LoadingDialog dialog;
    private Observer<List<PaymentMethodModel>> paymentMethodObserver;
    private  PaymentListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPaymentMethodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog = new LoadingDialog(this);

        setUpAppBar();
        setUpContinueButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpPaymentMethodList();
        viewModel.forceGet();
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewModel.getData().removeObserver(paymentMethodObserver);
    }

    private void setUpPaymentMethodList() {
        dialog.showDialog();
        viewModel = new ViewModelProvider(this).get(PaymentViewModel.class);

        paymentMethodObserver = paymentMethodModels -> {
            if (paymentMethodModels.size() == 0) {
                binding.btnContinue.setVisibility(View.GONE);
            } else {
                binding.btnContinue.setVisibility(View.VISIBLE);
            }
            adapter = new PaymentListAdapter(this, paymentMethodModels);
            binding.listPaymentMethod.setAdapter(adapter);
            binding.listPaymentMethod.setLayoutManager(new LinearLayoutManager(this));

            dialog.dismissDialog();
        };

        viewModel.getData(false).observe(this, paymentMethodObserver);
    }

    private void setUpAppBar() {
        binding.btnBack.setOnClickListener(view -> {
            finish();
        });
    }

    private void setUpContinueButton() {
        binding.btnContinue.setOnClickListener(view -> {
            Intent intent = new Intent(this, ConfirmOrderActivity.class);
            intent.putExtra("payment", adapter.getCurrentSelect());
            intent.putExtra("address", getIntent().getSerializableExtra("address"));
            startActivity(intent);
        });

        binding.btnAddPaymentMethod.setOnClickListener(v -> {
            Intent intent = new Intent(this, PaymentListActivity.class);
            startActivity(intent);
        });
    }
}