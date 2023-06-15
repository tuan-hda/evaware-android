package com.example.evaware.presentation.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.evaware.R;
import com.example.evaware.databinding.FragmentStartBinding;
import com.google.android.material.button.MaterialButton;

import org.checkerframework.checker.nullness.qual.NonNull;

public class StartFragment extends Fragment {

    private MaterialButton loginBtn;
    private MaterialButton createAccBtn;
    private MaterialButton testBtn;
    private MaterialButton btnSearchCountry;
    private FragmentStartBinding binding;
    private MaterialButton btnOrderScreen;

    public StartFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStartBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initView(view);
        return view;
    }

    private void initView(View view) {
        loginBtn = binding.btnLoginMethod;
        createAccBtn =binding.btnCreateAccountMethod;
//        testBtn = binding.btnTest;
//        btnSearchCountry = binding.btnSearchCountry;
//        btnOrderScreen = binding.btnOrder;

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(StartFragment.this).navigate(R.id.action_start_fragment_to_login_fragment);
            }
        });
        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(StartFragment.this).navigate(R.id.action_start_fragment_to_sign_up_fragment);
            }
        });
//        testBtn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
////                NavHostFragment.findNavController(StartFragment.this).navigate(R.id.action_start_fragment_to_countryListFragment);
//                NewCardBottomSheetFragment newCardBottomSheetFragment = new NewCardBottomSheetFragment();
//                newCardBottomSheetFragment.show(getChildFragmentManager(),"NewCardBottomSheetFragment");
//            }
//        });
//        btnSearchCountry.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(StartFragment.this).navigate(R.id.action_start_fragment_to_deliveryCountryFragment);
//            }
//        });
//        btnOrderScreen.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(StartFragment.this).navigate(R.id.action_start_fragment_to_orderFragment);
//            }
//        });
    }
}