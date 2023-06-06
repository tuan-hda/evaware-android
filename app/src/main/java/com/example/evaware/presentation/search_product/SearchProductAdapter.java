package com.example.evaware.presentation.search_product;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evaware.R;
import com.example.evaware.data.model.ProductModel;
import com.example.evaware.presentation.product.ProductActivity;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SearchProductAdapter extends RecyclerView.Adapter<SearchProductAdapter.ViewHolder> {
    private List<ProductModel> productModelList;
    private Context context;

    public SearchProductAdapter(List<ProductModel> productModelList, Context context) {
        this.productModelList = productModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout for the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel productModel = productModelList.get(position);
        holder.mTextView.setText(productModel.getName());
        Picasso.with(context)
                .load(productModel.getImg_url())
                .into(holder.mImageView);
        holder.container.setOnClickListener(view->{
            Intent intent = new Intent(context, ProductActivity.class);
            intent.putExtra("productModelId", productModel.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTextView;
        private LinearLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views within the ViewHolder
            mImageView = itemView.findViewById(R.id.img_product_item);
            mTextView = itemView.findViewById(R.id.tv_product_name);
            container = itemView.findViewById(R.id.container);
        }
    }
}