package com.example.evaware.presentation.review;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evaware.R;
import com.example.evaware.data.model.ReviewDetail;
import com.example.evaware.data.model.ReviewModel;
import com.example.evaware.presentation.product.VariationProductAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private Context context;
    private List<ReviewDetail> reviews;
    public ReviewAdapter(Context context, List<ReviewDetail> reviews){
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);
        return new ReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewDetail item = reviews.get(position);
        holder.bind(item, context);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        RatingBar rating;
        TextView userName;
        TextView content;
        TextView time;
        ImageView userImage;
        private RecyclerView reviewImages;
        private ImageReviewAdapter imageReviewAdapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rating = itemView.findViewById(R.id.ratingbar);
            userName = itemView.findViewById(R.id.tv_user_name);
            content = itemView.findViewById(R.id.tv_content);
            time = itemView.findViewById(R.id.tv_time);
            userImage = itemView.findViewById(R.id.iv_user_image);
            reviewImages = itemView.findViewById(R.id.rv_images);
            reviewImages.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
        private void bind(ReviewDetail item, Context context){
            rating.setRating(item.getReview().getRating_star());
            userName.setText(item.getReview().getUser().getName());
            content.setText(item.getReview().getContent());
            time.setText(item.getDayCreate());
            Picasso.with(context)
                    .load(item.getReview().getUser().getImg_url())
                    .into(userImage);
            imageReviewAdapter = new ImageReviewAdapter(item.getImgUrls(), context);
            reviewImages.setAdapter(imageReviewAdapter);
        }
    }
}
