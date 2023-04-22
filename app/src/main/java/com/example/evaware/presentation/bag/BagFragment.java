package com.example.evaware.presentation.bag;

import static android.view.View.GONE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.evaware.databinding.FragmentBagBinding;
import com.example.evaware.presentation.checkout.ContactInfoActivity;
import com.example.evaware.presentation.checkout.DeliveryAddressActivity;
import com.example.evaware.utils.LinearScrollListView;


public class BagFragment extends Fragment {
    private FragmentBagBinding binding;
    private BagViewModel viewModel;
    private final boolean isEmpty = false;
    private BagListAdapter adapter;
    private FragmentActivity activity;
    private ProgressDialog progressDialog;

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
            initProgressDialog();
            progressDialog.show();

            binding.layoutEmptyBag.setVisibility(GONE);
            binding.btnClearPromo.setOnClickListener(view1 -> {
                binding.edtPromo.setText("");
            });

            viewModel.getBagList().observe(activity, bagItemModels -> {
                adapter = new BagListAdapter(activity, bagItemModels);
                binding.listBagItem.setAdapter(adapter);
                LinearScrollListView.justifyListViewHeightBasedOnChildren(binding.listBagItem);
                progressDialog.dismiss();
            });

            setUpCheckoutButton();
        } else {
            binding.layoutBag.setVisibility(GONE);
        }
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMax(100);
        progressDialog.setMessage("Loading...");
//        progressDialog.setTitle("ProgressDialog bar example");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    private void setUpCheckoutButton() {
        binding.btnCheckout.setOnClickListener(view -> {
            Intent intent = new Intent(activity, DeliveryAddressActivity.class);
            startActivity(intent);
        });
    }
}