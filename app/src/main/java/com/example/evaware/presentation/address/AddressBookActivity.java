package com.example.evaware.presentation.address;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.evaware.data.model.AddressModel;
import com.example.evaware.databinding.ActivityAddressBookBinding;
import com.example.evaware.utils.LoadingDialog;

public class AddressBookActivity extends AppCompatActivity {
    ActivityAddressBookBinding binding;
    private AddressViewModel viewModel;
    private LoadingDialog dialog;
    private ActivityResultLauncher<Intent> launcherAdd, launcherUpdate;
    private AddressListAdapter adapter;

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

        setUpLaunchers();
        setUpRecycler();
        setUpButtons();
    }

    private void setUpLaunchers() {
        launcherAdd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                assert result.getData() != null;
                AddressModel item = (AddressModel) result.getData().getSerializableExtra("data");
                viewModel.add(item);
            }
        });

        launcherUpdate = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                assert result.getData() != null;
                if (result.getData().getBooleanExtra("delete", false)) {
                    viewModel.delete(adapter.getSelectedIndex());
                } else {
                    AddressModel item = (AddressModel) result.getData().getSerializableExtra("data");
                    viewModel.update(adapter.getSelectedIndex(), item);
                }
            }
        });
    }

    private void setUpButtons() {
        binding.btnAddAddress.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddAddressActivity.class);
            intent.putExtra("type", "add");
            launcherAdd.launch(intent);
        });
    }

    private void setUpRecycler() {
        dialog.showDialog();

        viewModel.getData().observe(this, addressModels -> {
            adapter = new AddressListAdapter(this, addressModels, true, launcherUpdate);
            binding.listAddress.setAdapter(adapter);
            binding.listAddress.setLayoutManager(new LinearLayoutManager(this));

            dialog.dismissDialog();
        });
    }
}