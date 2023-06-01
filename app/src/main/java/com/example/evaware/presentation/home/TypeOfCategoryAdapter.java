package com.example.evaware.presentation.home;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evaware.R;
import com.example.evaware.presentation.category.SearchCategoryActivity;
import com.example.evaware.data.model.TypeofCategory;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TypeOfCategoryAdapter extends RecyclerView.Adapter<TypeOfCategoryAdapter.MyViewHolder> {
    private List<TypeofCategory> dataList;
    private static Activity context;
    private static HomeFragment fragment;

    public TypeOfCategoryAdapter(List<TypeofCategory> dataList, Activity context, HomeFragment fragment ) {
        this.dataList = dataList;
        this.context = context;
        this.fragment = fragment;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TypeofCategory item = dataList.get(position);
        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName;
        private ImageView imgItem;
        private String id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            imgItem = itemView.findViewById(R.id.img_item);
            itemView.setOnClickListener(this);
        }
        public void bindData(TypeofCategory data) {
            tvName.setText(data.getName());
            Picasso.with(context)
                    .load(data.getImg_url())
                    .into(imgItem);
            id = data.getId();
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, SearchCategoryActivity.class);
            intent.putExtra("itemId", id);
            intent.putExtra("itemName", tvName.getText().toString());
            context.startActivity(intent);
        }
    }
}