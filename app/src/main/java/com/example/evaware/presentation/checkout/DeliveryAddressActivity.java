package com.example.evaware.presentation.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.evaware.data.model.AddressModel;
import com.example.evaware.databinding.ActivityDeliveryAddressBinding;
import com.example.evaware.presentation.address.AddressListAdapter;
import com.example.evaware.presentation.address.AddressViewModel;
import com.example.evaware.utils.LoadingDialog;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class DeliveryAddressActivity extends AppCompatActivity {
    private ActivityDeliveryAddressBinding binding;
    private AddressViewModel viewModel;
    private LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDeliveryAddressBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(AddressViewModel.class);
        setContentView(binding.getRoot());
        dialog = new LoadingDialog(this);

        setUpAppBar();
        setUpContinueButton();
        setUpAddresses();
    }

    private void setUpAddresses() {
        dialog.showDialog();
        viewModel.getData().observe(this, addressModels -> {
            AddressListAdapter adapter = new AddressListAdapter(this, addressModels);
            binding.listAddress.setAdapter(adapter);
            binding.listAddress.setLayoutManager(new LinearLayoutManager(this));

            if (addressModels.size() == 0) {
                binding.btnContinue.setVisibility(View.GONE);
            } else {
                binding.btnContinue.setVisibility(View.VISIBLE);
            }
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
            Intent intent = new Intent(this, PaymentMethodActivity.class);
            startActivity(intent);
        });
    }
}