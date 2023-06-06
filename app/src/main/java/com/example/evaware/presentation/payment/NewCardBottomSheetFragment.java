package com.example.evaware.presentation.payment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.evaware.databinding.FragmentNewCardBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class NewCardBottomSheetFragment extends BottomSheetDialogFragment {


    private FragmentNewCardBottomSheetBinding binding;
    private BottomSheetDialog dialog;
    private ImageButton closeBts;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewCardBottomSheetBinding.inflate(inflater, container, false);
        closeBts = binding.btnClose;
        closeBts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return binding.getRoot();
    }
}