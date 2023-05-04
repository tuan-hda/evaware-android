package com.example.evaware.presentation.checkout;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.evaware.data.model.PaymentMethodModel;
import com.example.evaware.databinding.ActivityPaymentMethodBinding;
import com.example.evaware.presentation.payment.PaymentListAdapter;
import com.example.evaware.utils.LinearScrollListView;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethodActivity extends AppCompatActivity {
    private ActivityPaymentMethodBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPaymentMethodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpPaymentMethodList();
        setUpAppBar();
        setUpContinueButton();
    }

    private void setUpPaymentMethodList() {
        List<PaymentMethodModel> paymentMethods = new ArrayList<>();
        paymentMethods.add(new PaymentMethodModel("123", "https://upload.wikimedia" + ".org" +
                "/wikipedia/commons/thumb/a/a4/Mastercard_2019_logo" + ".svg/800px" +
                "-Mastercard_2019_logo.svg.png", "9893", "Tuan", "Mastercard", "12/29", true));
        paymentMethods.add(new PaymentMethodModel("124", "https://upload.wikimedia" +
                ".org/wikipedia/commons/thumb/5/5e/Visa_Inc._logo.svg/2560px-Visa_Inc._logo.svg" +
                ".png", "7233", "Tho", "Visa", "12/30", true));
        paymentMethods.add(new PaymentMethodModel("125", "https://upload.wikimedia" +
                ".org/wikipedia/commons/thumb/1/15/Apple_logo_hollow.svg/640px-Apple_logo_hollow" +
                ".svg.png", "", "Tuan", "Apple pay", "", false));

        PaymentListAdapter adapter = new PaymentListAdapter(this, paymentMethods);
        binding.listPaymentMethod.setAdapter(adapter);
        LinearScrollListView.justifyListViewHeightBasedOnChildren(binding.listPaymentMethod);
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