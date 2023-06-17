package com.example.evaware.presentation.order;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.evaware.R;
import com.example.evaware.data.model.BagItemModel;
import com.example.evaware.data.model.OrderModel;
import com.example.evaware.data.model.OrderProduct;
import com.example.evaware.data.model.ProductModel;
import com.example.evaware.databinding.FragmentOrderBinding;
import com.example.evaware.presentation.adapter.OrderListProductAdapter;
import com.example.evaware.presentation.bag.BagViewModel;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class OrderFragment extends Fragment {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    FragmentOrderBinding binding;
    OrderListProductAdapter adapter;
    RecyclerView listView;
    BagViewModel bagViewModel;
    MyOrderViewModel orderViewModel;
    OrderModel orderItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderBinding.inflate(inflater, container, false);
        bagViewModel = new ViewModelProvider(this).get(BagViewModel.class);
        orderViewModel = new ViewModelProvider(this).get(MyOrderViewModel.class);
        getData();
        setUpButton();
        return binding.getRoot();
    }

    private void setUpButton() {
        binding.orderDetailBack.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });
        binding.orderDetailBtCancel.setOnClickListener(view -> {
            orderViewModel.cancelOrder(db.collection("orders").document(orderItem.id));
            orderItem.status = 3;
            getStatus(orderItem);
            Toast.makeText(getContext(), "Cancel successfully!", Toast.LENGTH_SHORT).show();
        });
    }

    private void getData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            orderItem = (OrderModel) bundle.getSerializable("orderItem");
            if (orderItem != null) {
                //ID
                binding.orderDetailTvId.setText("Order #" + orderItem.id);
                //Date
                DateFormat dateFormat = new SimpleDateFormat("EEEE, dd/MM/yyyy, hh:mm a");
                binding.orderDetailDate.setText(dateFormat.format(orderItem.created_at.toDate()));
                getStatus(orderItem);
                binding.orderDetailAddress.setText(orderItem.getAddress());
                binding.orderDetailContact.setText(orderItem.name+ ", "+orderItem.phone);
                binding.orderDetailTotal.setText("$"+orderItem.total);
                binding.orderDetailPromocode.setText("-$"+orderItem.total*orderItem.voucher_percent/100);
                binding.orderDetailPaymentMethod.setText(orderItem.payment_method);

                initView(orderItem);
            }
        }
    }
    private void getStatus(OrderModel item) {
        binding.orderDetailBtCancel.setVisibility(View.GONE);

        if(item.status == 0){
            binding.orderDetailStatus.setText("In progress");
            binding.orderDetailBtCancel.setVisibility(View.VISIBLE);
            binding.orderDetailStatus.setTextColor(Color.parseColor("#fbc403"));
        }
        else if(item.status == 1){
            binding.orderDetailStatus.setText("Delivering");
            binding.orderDetailStatus.setTextColor(Color.parseColor("#2929cc"));
        }
        else if(item.status == 2){
            binding.orderDetailStatus.setText("Success");
            binding.orderDetailStatus.setTextColor(Color.parseColor("#388e3c"));
        }
        else if(item.status == 3){
            binding.orderDetailStatus.setText("Cancelled");
            binding.orderDetailStatus.setTextColor(Color.parseColor("#EF5350"));
        }
    }

    private void initView(OrderModel item) {
        listView = binding.orderDetailLvOrder;
        ArrayList<ProductModel> orders = new ArrayList<>();
        adapter = new OrderListProductAdapter(getContext(), bagViewModel, item.order_items, orders);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setAdapter(adapter);

        for(BagItemModel bagItemModel : item.order_items){
            bagItemModel.product_ref.get().addOnSuccessListener(task ->{
                ProductModel product = task.toObject(ProductModel.class);
                orders.add(product);
                adapter.notifyDataSetChanged();
            });
        }
    }
}