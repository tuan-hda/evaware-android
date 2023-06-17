package com.example.evaware.presentation.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evaware.R;
import com.example.evaware.data.model.BagItemModel;
import com.example.evaware.data.model.OrderProduct;
import com.example.evaware.data.model.ProductModel;
import com.example.evaware.presentation.bag.BagViewModel;
import com.example.evaware.presentation.product.ProductViewModel;
import com.example.evaware.presentation.wishlist.FavorItemAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderListProductAdapter extends RecyclerView.Adapter<OrderListProductAdapter.ViewHolder> {

    Context mContext;
    BagViewModel bagViewModel;
    List<BagItemModel> orderList;
    ArrayList<ProductModel> mOrderItemList;

    public OrderListProductAdapter(Context context, BagViewModel viewModel, List<BagItemModel> order_items, ArrayList<ProductModel> orderItemList) {
        mContext = context;
        bagViewModel = viewModel;
        orderList = order_items;
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
        ProductModel orderItem = mOrderItemList.get(position);

        Picasso.with(mContext).load(orderItem.image_thumbnail).into(holder.imageView);
        holder.name.setText(orderItem.name);
        holder.priceTextView.setText("$" + orderItem.price);
        holder.qty.setText("x"+orderList.get(position).qty);
        holder.descriptionTextView.setText(orderItem.desc);

        holder.orderAgain.setOnClickListener(view -> {
            BagItemModel bagItemModel = new BagItemModel();
            bagItemModel.product_ref = orderList.get(position).product_ref;
            bagItemModel.variation_ref = orderList.get(position).variation_ref;
            bagItemModel.qty = 1;
            bagItemModel.created_at = (new Timestamp(new Date()));
            bagItemModel.updated_at = (new Timestamp(new Date()));

            bagViewModel.addItem(bagItemModel);
            Toast.makeText(mContext, "Added to cart", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return mOrderItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        TextView priceTextView;
        TextView descriptionTextView;
        TextView qty;
        MaterialButton orderAgain;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_product_img);
            name = itemView.findViewById(R.id.tv_order_product_name);
            priceTextView = itemView.findViewById(R.id.tv_order_product_price);
            qty = itemView.findViewById(R.id.tv_order_product_qty);
            descriptionTextView = itemView.findViewById(R.id.tv_order_product_description);
            orderAgain = itemView.findViewById(R.id.btn_order_again);
        }
    }
}