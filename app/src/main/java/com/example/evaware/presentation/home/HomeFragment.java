package com.example.evaware.presentation.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.evaware.databinding.FragmentHomeBinding;
import com.example.evaware.presentation.search_product.SearchProductActivity;
import com.example.evaware.data.model.TypeofCategory;
import com.example.evaware.presentation.wishlist.WishViewModel;
import com.example.evaware.utils.GlobalStore;
import com.example.evaware.utils.LoadingDialog;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private RecyclerView suggestList;
    private FragmentHomeBinding binding;
    private RecyclerView homeItemList;
    private List<TypeofCategory> types = new ArrayList<>();
    private LoadingDialog dialog;
    private WishViewModel wishViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        dialog = new LoadingDialog(getActivity());
        return binding.getRoot();
    }

    private void init() {
        suggestList = binding.horizontalList;
        homeItemList = binding.verticalList;
        suggestList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        homeItemList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            wishViewModel = new ViewModelProvider(this).get(WishViewModel.class);
    }


    public void loadData() {
        HomeViewModel viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        viewModel.getListTypeOfCategories().observe(requireActivity(), typeofCategories -> {
            if (typeofCategories.size() == 0) {
//                binding.verticalList.setVisibility(View.INVISIBLE);
                Toast.makeText(requireActivity(), "Empty", Toast.LENGTH_SHORT).show();
            } else {
                TypeOfCategoryAdapter adapter2 = new TypeOfCategoryAdapter(typeofCategories, getActivity(), HomeFragment.this);
                homeItemList.setAdapter(adapter2);
                dialog.dismissDialog();
            }
        });

        if (wishViewModel != null)
            wishViewModel.getWishList().observe(getActivity(), wishItemModels -> {
                GlobalStore.getInstance().setData("wishList", wishItemModels);
            });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog.showDialog();
        init();
        loadData();
        setUpBtn();

    }

    private void setUpBtn() {
        binding.cvSearchProduct.setOnClickListener(view -> {
            Intent intent1 = new Intent(requireActivity(), SearchProductActivity.class);
            requireActivity().startActivity(intent1);
        });
    }
}