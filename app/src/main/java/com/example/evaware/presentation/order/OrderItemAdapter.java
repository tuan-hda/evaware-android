package com.example.evaware.presentation.order;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evaware.R;
import com.example.evaware.data.model.BagItemModel;
import com.example.evaware.data.model.OrderModel;
import com.example.evaware.data.model.SavedModel;
import com.example.evaware.presentation.other.Setting;
import com.example.evaware.presentation.wishlist.FavorItemAdapter;
import com.example.evaware.presentation.wishlist.WishViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class OrderItemAdapter extends BaseAdapter {
    FragmentActivity activity;
    List<OrderModel> itemList;
    MyOrderViewModel vm;
    ViewHolder mViewHolder;

    public OrderItemAdapter(FragmentActivity activity, List<OrderModel> itemList, MyOrderViewModel vm) {
        this.activity = activity;
        this.itemList = itemList;
        this.vm = vm;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        mViewHolder = null;
        if(view == null){
            mViewHolder = new ViewHolder();
            LayoutInflater inflater = activity.getLayoutInflater();
            view = inflater.inflate(R.layout.item_order, null);

            mViewHolder.date = view.findViewById(R.id.itemOrder_tv_time);
            mViewHolder.price = view.findViewById(R.id.itemOrder_tv_price);
            mViewHolder.status = view.findViewById(R.id.itemOrder_tv_status);
            mViewHolder.id = view.findViewById(R.id.itemOrder_tv_id);
            mViewHolder.images = view.findViewById(R.id.itemOrder_rv_image);
            mViewHolder.llItem = view.findViewById(R.id.itemOrder_ll_item);

            view.setTag(mViewHolder);
        }
        else{
            mViewHolder = (ViewHolder) view.getTag();
        }

        setData(i);
        setUpButton();

        return view;
    }

    private void setUpButton() {
        mViewHolder.llItem.setOnClickListener(view -> {
            Fragment fragment = new OrderFragment();
            Bundle bundle = new Bundle();
            bundle.putString("orderId", "tét");
            fragment.setArguments(bundle);

            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new OrderFragment())
                    .addToBackStack("orderDetail")
                    .commit();
        });
    }

    private void setData(int i) {
        OrderModel item = itemList.get(i);
        OrderImageAdapter imageAdapter = new OrderImageAdapter(activity, item.order_items);

        DateFormat dateFormat = new SimpleDateFormat("EEEE, dd/MM/yyyy, hh:mm a");
        mViewHolder.date.setText(dateFormat.format(item.created_at.toDate()));
        mViewHolder.price.setText("$"+item.total);
        mViewHolder.id.setText(item.id);
        getStatus(item);

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        mViewHolder.images.setLayoutManager(layoutManager);
        mViewHolder.images.setAdapter(imageAdapter);
    }

    private void getStatus(OrderModel item) {
        if(item.status == 0){
            mViewHolder.status.setText("Waiting for payment");
        }else{
            mViewHolder.status.setText("Delivered");
            mViewHolder.status.setTextColor(Color.parseColor("#66BB6A"));
        }
    }


    static class ViewHolder {
        TextView date;
        TextView price;
        TextView status;
        TextView id;
        RecyclerView images;
        LinearLayout llItem;
    }
}
