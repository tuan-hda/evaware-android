package com.example.evaware.presentation.address;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evaware.R;
import com.example.evaware.data.model.AddressBase;
import com.example.evaware.data.model.Province;
import com.example.evaware.databinding.ProvinceItemBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.ViewHolder> implements Filterable {
    private Activity context;
    private List<AddressBase> list;
    private List<AddressBase> listFiltered;
    private AddressBase selected;

    public ProvinceAdapter(Activity context, List<AddressBase> list) {
        this.context = context;
        this.list = list;
        listFiltered = list;
        selected = null;
    }

    public ProvinceAdapter(Activity context, List<AddressBase> list, AddressBase def) {
        this.context = context;
        this.list = list;
        listFiltered = list;
        selected = def;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProvinceItemBinding binding = ProvinceItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddressBase item = listFiltered.get(position);
        holder.binding.province.setText(item.getName());

        if (selected != null && selected.getCode() == item.getCode()) {
            holder.binding.indicator.getRoot().setBackgroundResource(R.drawable.select_indicator);
        } else {
            holder.binding.indicator.getRoot().setBackgroundResource(R.drawable.unselect_indicator);
        }

        holder.binding.getRoot().setOnClickListener(v -> {
            selected = item;
            notifyDataSetChanged();
            Intent intent = new Intent();
            intent.putExtra("res", selected);
            context.setResult(Activity.RESULT_OK, intent);
            context.finish();
        });
    }

    @Override
    public int getItemCount() {
        return listFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<AddressBase> finRes;
                String query = constraint.toString();
                if (query.isEmpty()) {
                    finRes = list;
                } else {
                    List<AddressBase> filtered = new ArrayList<>();
                    for (AddressBase item : list) {
                        if (item.getName().toLowerCase().contains(query.toLowerCase())) {
                            filtered.add(item);
                        }
                    }
                    finRes = filtered;
                }
                FilterResults results = new FilterResults();
                results.values = finRes;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listFiltered = (List<AddressBase>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ProvinceItemBinding binding;

        public ViewHolder(@NonNull ProvinceItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
