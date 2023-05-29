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
import com.example.evaware.SearchCategoryActivity;
import com.example.evaware.data.model.CategoryModel;
import com.example.evaware.data.model.TypeofCategory;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private List<CategoryModel> dataList;
    private static Activity context;
    public CategoryAdapter(List<CategoryModel> dataList, Activity context) {
        this.dataList = dataList;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CategoryModel item = dataList.get(position);
        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName;
        private ImageView imgItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_category_name);
            imgItem = itemView.findViewById(R.id.img_category_item);
            itemView.setOnClickListener(this);

        }
        public void bindData(CategoryModel data) {
            tvName.setText(data.getName());
            Picasso.with(context)
                    .load(data.getImgUrl())
                    .into(imgItem);
        }

        @Override
        public void onClick(View view) {
            System.out.println("hi");
            Intent intent = new Intent(context, CatalogActivity.class);
            // Pass any necessary data to the new activity
//            intent.putExtra("itemId", item.getId());
            context.startActivity(intent);
        }
    }
}