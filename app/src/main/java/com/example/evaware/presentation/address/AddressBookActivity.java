package com.example.evaware.presentation.address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.evaware.R;
import com.example.evaware.data.model.AddressModel;
import com.example.evaware.databinding.ActivityAddressBookBinding;
import com.example.evaware.utils.LoadingDialog;

import java.util.List;

public class AddressBookActivity extends AppCompatActivity {
    ActivityAddressBookBinding binding;
    private AddressViewModel viewModel;
    private LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddressBookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(AddressViewModel.class);
        dialog = new LoadingDialog(this);

        binding.appbar.appbarTitle.setText("Address book");
        binding.appbar.btnBack.setOnClickListener(v -> {
            finish();
        });

        setUpRecycler();
        setUpButtons();
    }

    private void setUpButtons() {
        binding.btnAddAddress.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddAddressActivity.class);
            startActivity(intent);
        });
    }

    private void setUpRecycler() {
        dialog.showDialog();

        viewModel.getData().observe(this, addressModels -> {
            AddressListAdapter adapter = new AddressListAdapter(this, addressModels);
            binding.listAddress.setAdapter(adapter);
            binding.listAddress.setLayoutManager(new LinearLayoutManager(this));

            dialog.dismissDialog();
        });
    }
}