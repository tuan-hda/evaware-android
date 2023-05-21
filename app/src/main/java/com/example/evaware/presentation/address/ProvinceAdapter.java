package com.example.evaware.presentation.address;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evaware.R;
import com.example.evaware.databinding.ProvinceItemBinding;

import java.util.List;

public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.ViewHolder> {
    private Activity context;
    private List<String> list;
    private int selected = 0;

    public ProvinceAdapter(Activity context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProvinceItemBinding binding = ProvinceItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = list.get(position);
        holder.binding.province.setText(item);

        if (selected == position) {
            holder.binding.indicator.getRoot().setBackgroundResource(R.drawable.select_indicator);
        } else {
            holder.binding.indicator.getRoot().setBackgroundResource(R.drawable.unselect_indicator);
        }

        holder.binding.getRoot().setOnClickListener(v -> {
            int temp = selected;
            selected = position;
            notifyItemChanged(temp);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ProvinceItemBinding binding;

        public ViewHolder(@NonNull ProvinceItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
