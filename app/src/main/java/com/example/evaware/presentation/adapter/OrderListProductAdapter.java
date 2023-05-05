package com.example.evaware.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evaware.R;
import com.example.evaware.data.model.OrderProduct;

import java.util.ArrayList;
import java.util.List;

public class OrderListProductAdapter extends RecyclerView.Adapter<OrderListProductAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<OrderProduct> mOrderItemList;

    public OrderListProductAdapter(Context context, ArrayList<OrderProduct> orderItemList) {
        mContext = context;
        mOrderItemList = orderItemList;
    }
    @NonNull
    @Override
    public OrderListProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListProductAdapter.ViewHolder holder, int position) {
        OrderProduct orderItem = mOrderItemList.get(position);

        holder.imageView.setImageResource(orderItem.getmImageResourceId());
        holder.priceTextView.setText(orderItem.getPrice());
        holder.descriptionTextView.setText(orderItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return mOrderItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView priceTextView;
        TextView descriptionTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_product_img);
            priceTextView = itemView.findViewById(R.id.tv_order_product_price);
            descriptionTextView = itemView.findViewById(R.id.tv_order_product_description);
        }
    }
}