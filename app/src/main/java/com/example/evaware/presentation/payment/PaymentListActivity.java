package com.example.evaware.presentation.payment;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.evaware.R;
import com.example.evaware.data.model.AddressModel;
import com.example.evaware.data.model.PaymentMethodModel;
import com.example.evaware.databinding.ActivityPaymentListBinding;
import com.example.evaware.presentation.address.AddAddressActivity;
import com.example.evaware.presentation.address.AddressListAdapter;
import com.example.evaware.presentation.address.AddressViewModel;
import com.example.evaware.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

public class PaymentListActivity extends AppCompatActivity {
    private ActivityPaymentListBinding binding;
    private PaymentViewModel viewModel;
    private LoadingDialog dialog;
    private ActivityResultLauncher<Intent> launcherAdd, launcherUpdate;
    private PaymentListAdapter adapter;
    private NewCardBottomSheetFragment bottomSheetFragment;
    private static final String TAG = "PaymentListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(PaymentViewModel.class);
        dialog = new LoadingDialog(this);

        binding.appbar.appbarTitle.setText("Payment methodss");
        binding.appbar.btnBack.setOnClickListener(v -> {
            finish();
        });

//        setUpLaunchers();
        setUpButtons();
        setUpRecycler();
    }

//    private void setUpLaunchers() {
//        launcherAdd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//            if (result.getResultCode() == RESULT_OK) {
//                assert result.getData() != null;
//                AddressModel item = (AddressModel) result.getData().getSerializableExtra("data");
//                viewModel.add(item);
//            }
//        });
//
//        launcherUpdate = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//            if (result.getResultCode() == RESULT_OK) {
//                assert result.getData() != null;
//                if (result.getData().getBooleanExtra("delete", false)) {
//                    viewModel.delete(adapter.getSelectedIndex());
//                } else {
//                    AddressModel item = (AddressModel) result.getData().getSerializableExtra("data");
//                    viewModel.update(adapter.getSelectedIndex(), item);
//                }
//            }
//        });
//    }

    private void setUpButtons() {
        bottomSheetFragment = NewCardBottomSheetFragment.newInstance(viewModel);
        binding.btnAddPaymentMethod.setOnClickListener(v -> {
            bottomSheetFragment.show(getSupportFragmentManager(), "NewCardBottomSheetFragment");
        });
    }

    private void setUpRecycler() {
        dialog.showDialog();

        viewModel.getData().observe(this, addressModels -> {
            adapter = new PaymentListAdapter(this, addressModels, true, bottomSheetFragment, getSupportFragmentManager());
            binding.listItems.setAdapter(adapter);
            binding.listItems.setLayoutManager(new LinearLayoutManager(this));

            dialog.dismissDialog();
        });
    }
}