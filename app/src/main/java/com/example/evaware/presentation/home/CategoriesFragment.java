package com.example.evaware.presentation.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.evaware.R;
import com.example.evaware.data.model.CategoryModel;
import com.example.evaware.databinding.FragmentCategoriesBinding;

import java.util.ArrayList;
import java.util.List;


public class CategoriesFragment extends Fragment {
    private RecyclerView recyclerView;
    private FragmentCategoriesBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCategoriesBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        recyclerView = binding.rcvCategories;
        List<CategoryModel> categories = new ArrayList<CategoryModel>();
        categories.add(new CategoryModel("sofas", "https://firebasestorage.googleapis.com/v0/b/evaware-893a5.appspot.com/o/categories%2Fsofas%20%26%20sectionals%2Fchaise-longues-57527.webp?alt=media&token=97d5ab2c-cbda-4563-b3e7-b0e4d6c4ff7f"));
        categories.add(new CategoryModel("sofas", "https://firebasestorage.googleapis.com/v0/b/evaware-893a5.appspot.com/o/categories%2Fsofas%20%26%20sectionals%2Fchaise-longues-57527.webp?alt=media&token=97d5ab2c-cbda-4563-b3e7-b0e4d6c4ff7f"));
        categories.add(new CategoryModel("sofas", "https://firebasestorage.googleapis.com/v0/b/evaware-893a5.appspot.com/o/categories%2Fsofas%20%26%20sectionals%2Fchaise-longues-57527.webp?alt=media&token=97d5ab2c-cbda-4563-b3e7-b0e4d6c4ff7f"));
        categories.add(new CategoryModel("sofas", "https://firebasestorage.googleapis.com/v0/b/evaware-893a5.appspot.com/o/categories%2Fsofas%20%26%20sectionals%2Fchaise-longues-57527.webp?alt=media&token=97d5ab2c-cbda-4563-b3e7-b0e4d6c4ff7f"));
        categories.add(new CategoryModel("sofas", "https://firebasestorage.googleapis.com/v0/b/evaware-893a5.appspot.com/o/categories%2Fsofas%20%26%20sectionals%2Fchaise-longues-57527.webp?alt=media&token=97d5ab2c-cbda-4563-b3e7-b0e4d6c4ff7f"));
        CategoryAdapter adapter = new CategoryAdapter(categories, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }
}