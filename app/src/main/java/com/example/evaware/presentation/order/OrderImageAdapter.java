package com.example.evaware.presentation.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evaware.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderImageAdapter extends RecyclerView.Adapter<OrderImageAdapter.ViewHolder>{
    Context context;
    List<String> images;

    public OrderImageAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String image_url = images.get(position);
        Picasso.with(context)
                .load(image_url)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.order_image);
        }
    }
}
