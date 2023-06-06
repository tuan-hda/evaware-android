package com.example.evaware.presentation.home;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evaware.R;
import com.example.evaware.data.model.Suggestion;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SuggestAdapter extends RecyclerView.Adapter<SuggestAdapter.MyViewHolder> {
    private List<Suggestion> dataList;
    private static Activity context;
    public SuggestAdapter(List<Suggestion> dataList, Activity context) {
        this.dataList = dataList;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Suggestion suggestion = dataList.get(position);
        holder.bindData(suggestion);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName;
        private ImageView imgBackground;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            imgBackground = itemView.findViewById(R.id.img_background);

        }
        public void bindData(Suggestion data) {
            tvName.setText(data.getName());
            Picasso.with(context)
                    .load(data.getImgUrl())
                    .into(imgBackground);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
