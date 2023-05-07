package com.example.evaware.presentation.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.evaware.presentation.bottomSheet.Dialog;
import com.example.evaware.R;
import com.example.evaware.presentation.bottomSheet.ProductInfo;
import com.example.evaware.presentation.bottomSheet.Sort;
import com.google.android.material.button.MaterialButton;

public class HomeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialButton buttonSheet = view.findViewById(R.id.test);
        buttonSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        ProductInfo prodInfoFragment = new ProductInfo();
        Dialog dialog = new Dialog(prodInfoFragment);
        dialog.show(getChildFragmentManager(), "dialog_prodInfo");
    }
}