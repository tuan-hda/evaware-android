package com.example.evaware.presentation.bag;

import static android.view.View.GONE;

import static com.example.evaware.utils.CurrencyFormat.getFormattedPrice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.evaware.data.model.BagItemModel;
import com.example.evaware.data.model.ProductModel;
import com.example.evaware.databinding.FragmentBagBinding;
import com.example.evaware.presentation.checkout.DeliveryAddressActivity;
import com.example.evaware.utils.LoadingDialog;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class BagFragment extends Fragment {
    private FragmentBagBinding binding;
    private BagViewModel viewModel;
    private BagListAdapter adapter;
    private FragmentActivity activity;
    private static final String TAG = "BagFragment";
    private LoadingDialog dialog;

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
        setUpButtons();
        dialog = new LoadingDialog(getActivity());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        dialog.showDialog();

        viewModel.getBagList().observe(activity, bagItemModels -> {

            if (bagItemModels.size() == 0) {
                binding.scrollView.setVisibility(GONE);
                binding.layoutEmptyBag.setVisibility(View.VISIBLE);
                Toast.makeText(activity, "Empty", Toast.LENGTH_SHORT).show();
            } else {
                binding.layoutEmptyBag.setVisibility(GONE);
                binding.scrollView.setVisibility(View.VISIBLE);
                adapter = new BagListAdapter(activity, bagItemModels, viewModel);
                binding.listBagItem.setAdapter(adapter);
                updateTotal(bagItemModels);
            }

            dialog.dismissDialog();
        });
    }

    private void updateTotal(List<BagItemModel> list) {
        int total = 0;
        for (BagItemModel item : list) {
            Log.d(TAG, "updateTotal: " + item.product);
            total += item.product.price * item.qty;
        }
        binding.textTotalPrice.setText(getFormattedPrice(total));
    }

    private void setUpButtons() {
        binding.btnCheckout.setOnClickListener(view -> {
            Intent intent = new Intent(activity, DeliveryAddressActivity.class);
            startActivity(intent);
        });
    }
}