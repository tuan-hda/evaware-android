package com.example.evaware;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.evaware.data.model.OrderProduct;
import com.example.evaware.databinding.FragmentOrderBinding;
import com.example.evaware.presentation.adapter.OrderListProductAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class OrderFragment extends Fragment {

    private FragmentOrderBinding binding;
    private OrderListProductAdapter adapter;
    private RecyclerView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderBinding.inflate(inflater, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        listView = binding.lvOrder;
        ArrayList<OrderProduct> orders = new ArrayList<>();
        orders.add(new OrderProduct(R.drawable.bedside_table_img, "$150.00", "Wooden beside table featuring a raised design"));
        orders.add(new OrderProduct(R.drawable.img_table, "$280.50", "Square beside table with legs, a rattan shelf and a..."));
        adapter = new OrderListProductAdapter(getContext(), orders);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setAdapter(adapter);
    }
}