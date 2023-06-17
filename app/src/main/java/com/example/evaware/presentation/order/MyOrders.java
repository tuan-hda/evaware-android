package com.example.evaware.presentation.order;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.evaware.R;
import com.example.evaware.databinding.FragmentMyOrdersBinding;
import com.example.evaware.databinding.FragmentSavedItemBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MyOrders extends Fragment {
    FragmentMyOrdersBinding binding;
    FragmentActivity activity;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    MyOrderViewModel viewModel;
    OrderItemAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = requireActivity();
        binding = FragmentMyOrdersBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(MyOrderViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpButtons();
    }

    private void setUpData() {
        viewModel.getOrderOfUser(auth.getCurrentUser().getUid()).observe(activity, orderModels -> {
            adapter = new OrderItemAdapter(activity, orderModels, viewModel);
            binding.myOrdersLvListProduct.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });
    }

    private void setUpButtons() {
        binding.myOrdersIbReturn.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpData();
    }
}