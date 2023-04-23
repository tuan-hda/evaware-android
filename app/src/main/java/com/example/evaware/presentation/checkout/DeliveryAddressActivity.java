package com.example.evaware.presentation.checkout;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.evaware.data.model.AddressModel;
import com.example.evaware.databinding.ActivityDeliveryAddressBinding;
import com.example.evaware.presentation.address.AddressListAdapter;

import java.util.ArrayList;

public class DeliveryAddressActivity extends AppCompatActivity {
    private ActivityDeliveryAddressBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDeliveryAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpAppBar();
        setUpContinueButton();
        setUpAddresses();
    }

    private void setUpAddresses() {
        ArrayList<AddressModel> addresses = new ArrayList<>();
        addresses.add(new AddressModel("123", "Hoàng Đình Anh Tuấn", "0123456789", "hdatdragon2" +
                "@gmail.com", "Quảng Trị", "Triệu Phong", "Triệu Tài", "An Trú"));
        addresses.add(new AddressModel("123", "Hoàng Đình Anh Tuấn", "0123456789", "hdatdragon2" +
                "@gmail.com", "Quảng Trị", "Triệu Phong", "Triệu Tài", "An Trú"));
        addresses.add(new AddressModel("123", "Hoàng Đình Anh Tuấn", "0123456789", "hdatdragon2" +
                "@gmail.com", "Quảng Trị", "Triệu Phong", "Triệu Tài", "An Trú"));
        addresses.add(new AddressModel("123", "Hoàng Đình Anh Tuấn", "0123456789", "hdatdragon2" +
                "@gmail.com", "Quảng Trị", "Triệu Phong", "Triệu Tài", "An Trú"));
        addresses.add(new AddressModel("123", "Hoàng Đình Anh Tuấn", "0123456789", "hdatdragon2" +
                "@gmail.com", "Quảng Trị", "Triệu Phong", "Triệu Tài", "An Trú"));
        addresses.add(new AddressModel("123", "Hoàng Đình Anh Tuấn", "0123456789", "hdatdragon2" +
                "@gmail.com", "Quảng Trị", "Triệu Phong", "Triệu Tài", "An Trú"));
        addresses.add(new AddressModel("123", "Hoàng Đình Anh Tuấn", "0123456789", "hdatdragon2" +
                "@gmail.com", "Quảng Trị", "Triệu Phong", "Triệu Tài", "An Trú"));

        AddressListAdapter adapter = new AddressListAdapter(DeliveryAddressActivity.this,
                addresses);
        binding.listAddress.setAdapter(adapter);
        justifyListViewHeightBasedOnChildren(48);
    }

    private void justifyListViewHeightBasedOnChildren(int offset) {
        BaseAdapter adapter = (BaseAdapter) binding.listAddress.getAdapter();

        if (adapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, binding.listAddress);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight() - offset;
        }

        ViewGroup.LayoutParams par = binding.listAddress.getLayoutParams();
        int[] location1 = {0, 0};
        int[] location2 = {0, 0};
        binding.textContactInfo.getLocationOnScreen(location1);
        binding.btnAddAddress.getLocationOnScreen(location2);
        int minHeight = location2[1] - location1[1] - binding.textContactInfo.getHeight() - 24;

        par.height =
                totalHeight + (binding.listAddress.getDividerHeight() * (adapter.getCount() - 1));
        if (par.height < minHeight) {
            par.height = minHeight;
        }

        binding.listAddress.setLayoutParams(par);
        binding.listAddress.requestLayout();
    }

    private void setUpAppBar() {
        binding.btnBack.setOnClickListener(view -> {
            finish();
        });
    }

    private void setUpContinueButton() {
        binding.btnContinue.setOnClickListener(view -> {

        });
    }
}