package com.example.evaware.presentation.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.evaware.data.model.PaymentMethodModel;
import com.example.evaware.databinding.ActivityPaymentMethodBinding;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPaymentMethodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(PaymentViewModel.class);
        dialog = new LoadingDialog(this);

        setUpPaymentMethodList();
        setUpAppBar();
        setUpContinueButton();
    }

    private void setUpPaymentMethodList() {
        dialog.showDialog();

        viewModel.getData().observe(this, paymentMethodModels -> {
            if (paymentMethodModels.size() == 0) {
                binding.btnContinue.setVisibility(View.GONE);
            } else {
                binding.btnContinue.setVisibility(View.VISIBLE);
            }
            PaymentListAdapter adapter = new PaymentListAdapter(this, paymentMethodModels);
            binding.listPaymentMethod.setAdapter(adapter);
            binding.listPaymentMethod.setLayoutManager(new LinearLayoutManager(this));

            dialog.dismissDialog();
        });
    }

    private void setUpAppBar() {
        binding.btnBack.setOnClickListener(view -> {
            finish();
        });
    }

    private void setUpContinueButton() {
        binding.btnContinue.setOnClickListener(view -> {
            Intent intent = new Intent(this, ConfirmOrderActivity.class);
            startActivity(intent);
        });
    }
}