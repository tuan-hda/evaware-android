package com.example.evaware.presentation.filter;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.evaware.R;

import java.util.ArrayList;
import java.util.List;


public class Filter extends Fragment {
    List<Category> categories = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        categories.add(new Category("Food", "Selected"));
//        categories.add(new Category("Clothing", "Selected"));
//        categories.add(new Category("Electronics", "Selected"));
//        categories.add(new Category("Books", "Selected"));
//        categories.add(new Category("Home Goods", "Selected"));
//        ListView listView = (ListView) findViewById(R.id.filter_lv_listCategories);
//        CategoryAdapter adapter = new CategoryAdapter(getApplicationContext(), categories);
//        listView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }
}