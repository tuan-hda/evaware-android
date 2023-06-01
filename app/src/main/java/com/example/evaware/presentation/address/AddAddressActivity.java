package com.example.evaware.presentation.address;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.evaware.data.model.AddressBase;
import com.example.evaware.data.model.AddressModel;
import com.example.evaware.databinding.ActivityAddAddressBinding;
import com.example.evaware.utils.GlobalStore;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

public class AddAddressActivity extends AppCompatActivity {
    private ActivityAddAddressBinding binding;
    private ActivityResultLauncher<Intent> getProvince, getDistrict, getWard;
    private AddressBase province, district, ward;
    private AddressModel value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.appbar.appbarTitle.setText("Add address");
        binding.appbar.btnBack.setOnClickListener(v -> {
            finish();
        });


        handleIntent();
        setUpButtons();
    }

    private void handleIntent() {
        String type = getIntent().getStringExtra("type");
        if (Objects.equals(type, "add")) {
            binding.btnDelete.setVisibility(View.GONE);
        } else {
            value = GlobalStore.addressModel;
            binding.edtName.setText(value.name);
            binding.edtPhone.setText(value.phone);
            binding.edtEmail.setText(value.email);
            binding.edtStreet.setText(value.street);
            province = new AddressBase(value.province, value.province_id);
            binding.textProvince.setText(value.province);
            district = new AddressBase(value.district, value.district_id);
            binding.textDistrict.setText(value.district);
            ward = new AddressBase(value.ward, value.ward_id);
            binding.textWard.setText(value.ward);
            binding.btnDelete.setVisibility(View.VISIBLE);
        }
    }

    private void setUpButtons() {
        getProvince = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                assert result.getData() != null;
                province = (AddressBase) result.getData().getSerializableExtra("res");
                binding.textProvince.setText(province.getName());
                district = null;
                ward = null;
                binding.textDistrict.setText("District");
                binding.textWard.setText("Ward");
            }
        });

        binding.province.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChooseProvinceActivity.class);
            intent.putExtra("type", "province");
            if (province != null) {
                intent.putExtra("value", province);
            }
            getProvince.launch(intent);
        });


//       District
        getDistrict = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                assert result.getData() != null;
                district = (AddressBase) result.getData().getSerializableExtra("res");
                binding.textDistrict.setText(district.getName());
                ward = null;
                binding.textWard.setText("Ward");
            }
        });


        binding.district.setOnClickListener(v -> {
            if (province != null) {
                Intent intent = new Intent(this, ChooseProvinceActivity.class);
                intent.putExtra("type", "district");
                intent.putExtra("dep", province);
                if (district != null) {
                    intent.putExtra("value", district);
                }
                getDistrict.launch(intent);
            }
        });

//        Ward
        getWard = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                assert result.getData() != null;
                ward = (AddressBase) result.getData().getSerializableExtra("res");
                binding.textWard.setText(ward.getName());
            }
        });

        binding.ward.setOnClickListener(v -> {
            if (district != null) {
                Intent intent = new Intent(this, ChooseProvinceActivity.class);
                intent.putExtra("type", "ward");
                intent.putExtra("dep", district);
                if (ward != null) {
                    intent.putExtra("value", ward);
                }
                getWard.launch(intent);
            }
        });

        binding.btnSave.setOnClickListener(v -> {
            saveAddress();
        });

        binding.btnDelete.setOnClickListener(v -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
            builder.setCancelable(true);
            builder.setTitle("Delete this address?");
            builder.setMessage("You can't rollback this action");
            builder.setPositiveButton("Confirm",
                    (dialog, which) -> {
                        Intent intent = new Intent();
                        intent.putExtra("delete", true);
                        setResult(RESULT_OK, intent);
                        finish();
                    });
            builder.setNegativeButton("Cancel",
                    (dialog, which) -> {
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    private void saveAddress() {
        String err = "";
        boolean acp = true;

        String name = binding.edtName.getText().toString();
        String phone = binding.edtPhone.getText().toString();
        String email = binding.edtEmail.getText().toString();
        String textPro = province.getName();
        String textDis = district.getName();
        String textWar = ward.getName();
        String street = binding.edtStreet.getText().toString();

        if (ward == null) {
            err = "You must choose ward";
        }
        if (district == null) {
            err = "You must choose district";
        }
        if (province == null) {
            err = "You must choose province";
        }
        if (TextUtils.isEmpty(binding.edtName.getText())) {
            binding.edtName.setError("Full name is required");
            acp = false;
        }

        if (TextUtils.isEmpty(phone)) {
            binding.edtPhone.setError("Phone is required");
            acp = false;
        }
        if (!Patterns.PHONE.matcher(phone).matches()) {
            binding.edtPhone.setError("Invalid phone number");
            acp = false;
        }

        if (TextUtils.isEmpty(binding.edtEmail.getText())) {
            binding.edtEmail.setError("Email is required");
            acp = false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtEmail.setError("Invalid Email");
        }


        if (TextUtils.isEmpty(binding.edtStreet.getText())) {
            binding.edtStreet.setError("Street is required");
            acp = false;
        }

        if (!err.isEmpty()) {
            Toast.makeText(this, err, Toast.LENGTH_SHORT).show();
        }

        if (err.isEmpty() && acp) {
            AddressModel item = new AddressModel(name, phone, email, textPro, province.getCode(), textDis, district.getCode(), textWar, ward.getCode(), street);
            if (value != null) {
                item.path = value.path;
            }
            Intent intent = new Intent();
            intent.putExtra("data", item);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}