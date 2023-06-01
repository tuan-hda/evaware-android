package com.example.evaware.presentation.home;

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

import com.example.evaware.data.model.Suggestion;
import com.example.evaware.data.model.TypeofCategory;
import com.example.evaware.databinding.FragmentHomeBinding;
import com.example.evaware.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView suggestList;
    private FragmentHomeBinding binding;
    private RecyclerView homeItemList;
    private List<TypeofCategory> types = new ArrayList<>();
    private LoadingDialog dialog;


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
        List<Suggestion> dataList = new ArrayList<Suggestion>();
        dataList.add(new Suggestion("hot news", "https://assets-amberstudent.imgix.net/inventories/211754/cb3c651f.jpg?w=480&h=360&fit=crop&auto=format&trim=auto"));
        dataList.add(new Suggestion("hot news", "https://assets-amberstudent.imgix.net/inventories/211754/cb3c651f.jpg?w=480&h=360&fit=crop&auto=format&trim=auto"));
        dataList.add(new Suggestion("hot news", "https://assets-amberstudent.imgix.net/inventories/211754/cb3c651f.jpg?w=480&h=360&fit=crop&auto=format&trim=auto"));
        dataList.add(new Suggestion("hot news 2023", "https://assets-amberstudent.imgix.net/inventories/211754/cb3c651f.jpg?w=480&h=360&fit=crop&auto=format&trim=auto"));
        dataList.add(new Suggestion("hot news 2023", "https://assets-amberstudent.imgix.net/inventories/211754/cb3c651f.jpg?w=480&h=360&fit=crop&auto=format&trim=auto"));
        SuggestAdapter adapter = new SuggestAdapter(dataList, getActivity());
//
        suggestList.setAdapter(adapter);
        loadData();
    }


    public void loadData() {
        HomeViewModel viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        viewModel.getListTypeOfCategories().observe(requireActivity(), typeofCategories -> {
            Log.d("aaaa", String.valueOf(typeofCategories.size()));
            if (typeofCategories.size() == 0) {
//                binding.verticalList.setVisibility(View.INVISIBLE);
                Toast.makeText(requireActivity(), "Empty", Toast.LENGTH_SHORT).show();
            } else {
                TypeOfCategoryAdapter adapter2 = new TypeOfCategoryAdapter(typeofCategories, getActivity(), HomeFragment.this);
                homeItemList.setAdapter(adapter2);
                dialog.dismissDialog();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog.showDialog();
        init();
    }

}