package com.example.evaware.presentation.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.evaware.data.model.Suggestion;
import com.example.evaware.data.model.TypeofCategory;
import com.example.evaware.data.repo.OnDataFetchListener;
import com.example.evaware.data.repo.TypeOfCategoryRepo;
import com.example.evaware.databinding.FragmentHomeBinding;
import com.example.evaware.presentation.bottomSheet.Dialog;
import com.example.evaware.R;
import com.example.evaware.presentation.bottomSheet.ProductInfo;
import com.example.evaware.presentation.bottomSheet.Sort;
import com.example.evaware.presentation.filter.Category;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnDataFetchListener<List<TypeofCategory>> {

    private RecyclerView suggestList;
    private FragmentHomeBinding binding;
    private RecyclerView homeItemList;
    private TypeOfCategoryRepo typeOfCategoryRepo;
    private List<TypeofCategory> types = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        typeOfCategoryRepo = new TypeOfCategoryRepo();
        typeOfCategoryRepo.getTypeOfCategories(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        init();
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

        TypeOfCategoryAdapter adapter2 = new TypeOfCategoryAdapter(types, getActivity(), HomeFragment.this);
        suggestList.setAdapter(adapter);
        homeItemList.setAdapter(adapter2);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void showDialog() {
        ProductInfo prodInfoFragment = new ProductInfo();
        Dialog dialog = new Dialog(prodInfoFragment);

        dialog.show(getChildFragmentManager(), "dialog_prodInfo");
    }

    @Override
    public void onSuccess(List<TypeofCategory> data) {
        for (TypeofCategory item : data) {
           types.add(new TypeofCategory(item.getId(), item.getName(), item.getDesc(), item.getImg_url()));
        }

        TypeOfCategoryAdapter adapter2 = new TypeOfCategoryAdapter(types, getActivity(), HomeFragment.this);
        homeItemList.setAdapter(adapter2);
    }

    @Override
    public void onFailure(Exception e) {
        Toast.makeText(getActivity(), "Failed to fetch data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}