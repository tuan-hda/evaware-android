package com.example.evaware.presentation.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.evaware.databinding.ActivityConfirmOrderBinding;
import com.example.evaware.presentation.bag.BagListAdapter;
import com.example.evaware.presentation.bag.BagViewModel;

public class ConfirmOrderActivity extends AppCompatActivity {
    ActivityConfirmOrderBinding binding;
    BagListAdapter adapter;
    private BagViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityConfirmOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(BagViewModel.class);

        setUpAppBar();
        setUpBagList();
        setUpDeliveryAddress();
        setUpPaymentMethod();
        setUpButtons();
    }

    private void setUpButtons() {
        binding.btnClearPromo.setOnClickListener(view -> {
            binding.edtPromo.setText("");
        });
        binding.btnPay.setOnClickListener(view -> {
            Intent intent = new Intent(this, SuccessActivity.class);
            startActivity(intent);
        });
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
            adapter = new BagListAdapter(this, bagItemModels, true);
            binding.listBagItem.removeAllViews();
            for (int i = 0; i < bagItemModels.size(); i++) {
                binding.listBagItem.addView(adapter.getView(i, null, binding.listBagItem));
            }
        });
    }
}