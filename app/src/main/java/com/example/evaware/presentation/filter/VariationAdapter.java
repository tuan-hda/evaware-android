package com.example.evaware.presentation.filter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evaware.R;
import com.example.evaware.data.model.VariationModel;
import com.example.evaware.databinding.VariationItemBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VariationAdapter extends RecyclerView.Adapter<VariationAdapter.ViewHolder> {
    Context context;
    public List<VariationModel> variationModels;
    List<Boolean> selectVariations;
    private static final String TAG = "VariationAdapter";
    LayoutInflater inflater;
    FilterViewModel viewModel;

    public VariationAdapter(Context context, List<VariationModel> variationModels, FilterViewModel viewModel) {
        this.context = context;
        this.variationModels = new ArrayList<>();
        for (int i = 0; i < variationModels.size(); i++) {
            boolean flag = false;
            for (int j = 0; j < this.variationModels.size(); j++) {
                if (Objects.equals(this.variationModels.get(j).name.toLowerCase(), variationModels.get(i).name.toLowerCase())) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                this.variationModels.add(variationModels.get(i));
            }
        }
        this.selectVariations = new ArrayList<>();
        for (int i = 0; i < variationModels.size(); i++) {
            selectVariations.add(false);
        }
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VariationItemBinding binding = VariationItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new VariationAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VariationModel item = variationModels.get(position);
        holder.binding.textColor.setText(item.name);


        if (selectVariations.get(position)) {
            holder.binding.indicator.setBackgroundResource(R.drawable.select_rect_indicator);
        } else {
            holder.binding.indicator.setBackgroundResource(R.drawable.unselect_rect_indicator);
        }

        holder.binding.getRoot().setOnClickListener(v -> {
            selectVariations.set(position, !selectVariations.get(position));
            notifyItemChanged(position);
            setSelectVariations();
        });
    }

    public void setSelectVariations() {
        List<VariationModel> list = new ArrayList<>();
        for (int i = 0; i < variationModels.size(); i++) {
            if (selectVariations.get(i)) {
                list.add(variationModels.get(i));
            }
        }
        viewModel.setVariations(list);
    }

    @Override
    public int getItemCount() {
        return variationModels.size();
    }

    public void setVariationAlt(List<VariationModel> models) {
        List<Boolean> list = new ArrayList<>();
        for (int i = 0; i < variationModels.size(); i++) {
            boolean flag = false;
            for (int j = 0; j < models.size(); j++) {
                if (variationModels.get(i).name == models.get(j).name) {
                    flag = true;
                    break;
                }
            }
            list.add(flag);
        }
        this.selectVariations = list;
        Log.e(TAG, "setVariationAlt: " + list.size() );
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        VariationItemBinding binding;

        public ViewHolder(@NonNull VariationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
