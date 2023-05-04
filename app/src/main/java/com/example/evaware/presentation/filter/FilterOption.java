package com.example.evaware.presentation.filter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.evaware.R;

import java.util.ArrayList;
import java.util.List;

public class FilterOption extends Fragment {
    List<Option> options = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        options.add(new Option("Furniture", false));
        options.add(new Option("Lighting", false));
        options.add(new Option("Rugs", true));
        options.add(new Option("Mirrors", false));

        ListView listView = (ListView) view.findViewById(R.id.filterOption_lv_listOption);
        OptionAdapter adapter = new OptionAdapter(getContext(), options);
        listView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter_option, container, false);
    }
}