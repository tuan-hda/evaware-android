package com.example.evaware.presentation.new_review;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.evaware.R;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private ArrayList<Uri> imageList;

    public ImageAdapter(ArrayList<Uri> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_review_img, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Uri imageUri = imageList.get(position);
        holder.bindImage(imageUri);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private CardView closeCardView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_img_from_gallery);
            closeCardView = itemView.findViewById(R.id.cv_close);

            closeCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        imageList.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Uri imageUri = imageList.get(position);
                        openFullScreenImage(imageUri);
                    }
                }
            });
        }

        public void bindImage(Uri imageUri) {
            Glide.with(itemView.getContext())
                    .load(imageUri)
                    .into(imageView);
        }

        private void openFullScreenImage(Uri imageUri) {
            Intent intent = new Intent(itemView.getContext(), FullScreenImageActivity.class);
            intent.putExtra("imageUri", imageUri.toString());
            itemView.getContext().startActivity(intent);
        }
    }
}