package com.example.evaware.presentation.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evaware.R;
import com.example.evaware.data.model.CategoryModel;
import com.example.evaware.databinding.ItemCategoryBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    Context context;
    List<CategoryModel> caterogyList;
    LayoutInflater inflater;
    List<Boolean> selectCategories;
    List<Boolean> loaded;
    FilterViewModel viewModel;
    boolean isPlain;

    public CategoryAdapter(Context context, List<CategoryModel> caterogyList, FilterViewModel viewModel) {
        this.context = context;
        this.caterogyList = caterogyList;
        this.selectCategories = new ArrayList<>();
        loaded = new ArrayList<>();
        for (int i = 0; i < caterogyList.size(); i++) {
            selectCategories.add(false);
        }
        for (int i = 0; i < caterogyList.size(); i++) {
            loaded.add(false);
        }
        this.viewModel = viewModel;
    }

    public CategoryAdapter(Context context, List<CategoryModel> caterogyList, FilterViewModel viewModel, boolean isPlain) {
        this.context = context;
        this.caterogyList = caterogyList;
        this.selectCategories = new ArrayList<>();
        loaded = new ArrayList<>();
        for (int i = 0; i < caterogyList.size(); i++) {
            selectCategories.add(false);
        }
        for (int i = 0; i < caterogyList.size(); i++) {
            loaded.add(false);
        }
        this.viewModel = viewModel;
        this.isPlain = isPlain;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(LayoutInflater.from(context), parent, false);
        return new CategoryAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel item = caterogyList.get(position);

        Picasso.with(context).load(item.getImgUrl()).into(holder.binding.imgCategoryItem);
        holder.binding.tvCategoryName.setText(item.getName());


        holder.binding.getRoot().setOnClickListener(v -> {
            selectCategories.set(position, !selectCategories.get(position));
            if (selectCategories.get(position)) {
                holder.binding.indicator.setBackgroundResource(R.drawable.select_rect_indicator);
            } else {
                holder.binding.indicator.setBackgroundResource(R.drawable.unselect_rect_indicator);
            }
            setSelectCategories();
        });
    }

    private void setSelectCategories() {
        List<CategoryModel> list = new ArrayList<>();
        for (int i = 0; i < caterogyList.size(); i++){
            if (selectCategories.get(i)) {
                list.add(caterogyList.get(i));
            }
        }
        viewModel.setCategories(list);
    }

    @Override
    public int getItemCount() {
        return caterogyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCategoryBinding binding;

        public ViewHolder(@NonNull ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

//    public void setSelectCategoriesAlt(List categor) {
//
//    }
}
