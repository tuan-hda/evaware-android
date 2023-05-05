package com.example.evaware.presentation.bottomSheet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.evaware.R;
//Cách dùng:
//Khai báo hàm này:
//private void showDialog() {
//        ProductInfo prodInfoFragment = new ProductInfo();
//        Dialog dialog = new Dialog(prodInfoFragment);
//        dialog.show(getChildFragmentManager(), "dialog_prodInfo");
//        }
//Và gọi hàm trên khi cần show

public class ProductInfo extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton btClose = view.findViewById(R.id.proInfo_ib_close);
        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = (Dialog) getParentFragment();
                dialog.dismiss();
            }
        });
    }
}