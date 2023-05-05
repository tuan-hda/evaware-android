package com.example.evaware.presentation.bag;

import static android.view.View.GONE;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.evaware.databinding.FragmentBagBinding;
import com.example.evaware.presentation.checkout.DeliveryAddressActivity;
import com.example.evaware.utils.LinearScrollListView;


public class BagFragment extends Fragment {
    private final boolean isEmpty = false;
    private FragmentBagBinding binding;
    private BagViewModel viewModel;
    private BagListAdapter adapter;
    private FragmentActivity activity;

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
        activity = requireActivity();
        viewModel = new ViewModelProvider(activity).get(BagViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (!isEmpty) {
            binding.layoutEmptyBag.setVisibility(GONE);

            binding.loadingIndicator.getRoot().setVisibility(View.VISIBLE);
            binding.scrollView.setVisibility(GONE);

            viewModel.getBagList().observe(activity, bagItemModels -> {
                binding.loadingIndicator.getRoot().setVisibility(GONE);
                binding.scrollView.setVisibility(View.VISIBLE);

                adapter = new BagListAdapter(activity, bagItemModels);
                binding.listBagItem.setAdapter(adapter);
                LinearScrollListView.justifyListViewHeightBasedOnChildren(binding.listBagItem);
            });

            setUpCheckoutButton();
        } else {
            binding.scrollView.setVisibility(GONE);
            binding.layoutEmptyBag.setVisibility(View.VISIBLE);
        }
    }


    private void setUpCheckoutButton() {
        binding.btnCheckout.setOnClickListener(view -> {
            Intent intent = new Intent(activity, DeliveryAddressActivity.class);
            startActivity(intent);
        });
    }
}