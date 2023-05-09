package com.example.evaware.presentation.wishlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.evaware.R;
import com.example.evaware.presentation.filter.Category;
import com.example.evaware.presentation.filter.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class SavedItem extends Fragment {
    List<FavorItem> itemList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemList.add(new FavorItem());
        itemList.add(new FavorItem());
        itemList.add(new FavorItem());
        itemList.add(new FavorItem());
        itemList.add(new FavorItem());
        itemList.add(new FavorItem());


        ListView listView = (ListView) view.findViewById(R.id.savedItem_lv_listProduct);
        FavorItemAdapter adapter = new FavorItemAdapter(getContext(), itemList);
        listView.setAdapter(adapter);
    }
}