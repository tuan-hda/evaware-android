package com.example.evaware.presentation.review;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evaware.R;
import com.example.evaware.data.model.ImageModel;
import com.example.evaware.presentation.new_review.FullScreenImageActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageReviewAdapter extends RecyclerView.Adapter<ImageReviewAdapter.ChildViewHolder> {
    private List<ImageModel> data;
    private Context context;

    public ImageReviewAdapter(List<ImageModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review_image, parent, false);
        return new ChildViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder holder, int position) {
        ImageModel item = data.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItem;

        public ChildViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.iv_review);
            imgItem.setOnClickListener(view->{
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    ImageModel item = data.get(position);
                    openFullScreenImageWithUrl(item.getImg_url());
                }
            });
        }

        public void bind(ImageModel item) {
            Picasso.with(context)
                    .load(item.getImg_url())
                    .into(imgItem);

        }
        private void openFullScreenImageWithUrl(String imageUrl) {
            Intent intent = new Intent(context, FullScreenImageActivity.class);
            intent.putExtra("imageUrl", imageUrl);
            context.startActivity(intent);
        }
    }
}