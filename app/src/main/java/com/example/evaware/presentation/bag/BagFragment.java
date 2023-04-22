package com.example.evaware.presentation.bag;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.evaware.R;
import com.example.evaware.databinding.FragmentBagBinding;

public class BagFragment extends Fragment {
    private FragmentBagBinding binding;

    public BagFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBagBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}