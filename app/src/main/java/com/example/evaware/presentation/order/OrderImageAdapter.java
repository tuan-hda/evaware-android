package com.example.evaware.presentation.order;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evaware.R;
import com.example.evaware.data.model.BagItemModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OrderImageAdapter extends RecyclerView.Adapter<OrderImageAdapter.ViewHolder> {
    public static final String TAG = "OrderImageAdapter";
    Context context;
    List<BagItemModel> order_items;
    List<String> images;

    public OrderImageAdapter(Context context, List<BagItemModel> order_items) {
        this.context = context;
        this.order_items = order_items;
        images = new ArrayList<>();

        for (BagItemModel item : order_items) {
            item.product_ref.get().addOnSuccessListener(task -> {
                        images.add(task.getString("img_url"));
                        notifyDataSetChanged();
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "get product image Url: failure" + e.getLocalizedMessage());
                    });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(images.size() > position){
            String image_url = images.get(position);
            Picasso.with(context)
                    .load(image_url)
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return order_items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.order_image);
        }
    }
}
