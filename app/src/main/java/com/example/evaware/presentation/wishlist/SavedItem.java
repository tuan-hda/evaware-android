package com.example.evaware.presentation.wishlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.evaware.R;
import com.example.evaware.data.model.SavedModel;
import com.example.evaware.databinding.FragmentSavedItemBinding;
import com.example.evaware.presentation.home.HomeFragment;
import com.example.evaware.presentation.other.Setting;
import com.example.evaware.utils.GlobalStore;
import com.example.evaware.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

public class SavedItem extends Fragment implements FavorItemAdapter.ChooseVariationDialogListener {
    FragmentSavedItemBinding binding;
    FragmentActivity activity;
    WishViewModel viewModel;

    FavorItemAdapter adapter;
    private LoadingDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = requireActivity();
        viewModel = new ViewModelProvider(activity).get(WishViewModel.class);
        binding = FragmentSavedItemBinding.inflate(inflater, container, false);

        dialog = new LoadingDialog(getActivity());


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new FavorItemAdapter(activity, new ArrayList<>(), viewModel);
        adapter.setChooseVariationDialogListener(this);
        binding.savedItemLvListProduct.setAdapter(adapter);

        loadSavedItem();
        setUpButton();
    }

    private void setUpButton() {
//        binding.savedItemEmptyBtStartShopping.setOnClickListener(view -> {
//            getActivity()
//                    .getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.nav_host_fragment, new HomeFragment())
//                    .addToBackStack("homeFragment")
//                    .commit();
//        });
    }

    private void loadSavedItem() {
        dialog.showDialog();
        viewModel.getSavedList().observe(activity, saveModels->{
            if(saveModels.size() == 0){
                showSavedItem(false);
                showSavedEmpty(true);
            }else{
                showSavedItem(true);
                showSavedEmpty(false);
                adapter.updateItemList(saveModels);
            }

            dialog.dismissDialog();
        });
    }

    private void showSavedEmpty(boolean b) {
        if(b){
            binding.savedItemEmptyView1.setVisibility(View.VISIBLE);
            binding.savedItemEmptyView2.setVisibility(View.VISIBLE);
            binding.savedItemEmptyText1.setVisibility(View.VISIBLE);
            binding.savedItemEmptyText2.setVisibility(View.VISIBLE);
            binding.savedItemEmptyImage.setVisibility(View.VISIBLE);
            binding.savedItemEmptyButton.setVisibility(View.VISIBLE);
        }else{
            binding.savedItemEmptyView1.setVisibility(View.GONE);
            binding.savedItemEmptyView2.setVisibility(View.GONE);
            binding.savedItemEmptyText1.setVisibility(View.GONE);
            binding.savedItemEmptyText2.setVisibility(View.GONE);
            binding.savedItemEmptyImage.setVisibility(View.GONE);
            binding.savedItemEmptyButton.setVisibility(View.GONE);
        }
    }

    private void showSavedItem(boolean b) {
        if(b){
            binding.savedItemSearchBar.setVisibility(View.VISIBLE);
            binding.savedItemSortFilter.setVisibility(View.VISIBLE);
            binding.savedItemLvListProduct.setVisibility(View.VISIBLE);
        }else{
            binding.savedItemSearchBar.setVisibility(View.GONE);
            binding.savedItemSortFilter.setVisibility(View.GONE);
            binding.savedItemLvListProduct.setVisibility(View.GONE);
        }
    }


    @Override
    public void onMoveToBagClicked(SavedModel item) {
        ChooseVariationDialog dialog = new ChooseVariationDialog(item, adapter);
        dialog.show(getParentFragmentManager(), "ChooseVariationDialog");
    }
}