package com.example.evaware.presentation.product;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evaware.R;
import com.example.evaware.utils.ConvertString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductInfoAdapter extends RecyclerView.Adapter<ProductInfoAdapter.ViewHolder> {

    private List<HashMap<String, Object>> list;

    public ProductInfoAdapter(List<HashMap<String, Object>> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ProductInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductInfoAdapter.ViewHolder holder, int position) {
        HashMap<String, Object> compositionMap = list.get(position);
        for (Map.Entry<String, Object> entry : compositionMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            holder.tvValue.setText(value.toString());
            holder.tvType.setText(ConvertString.convertToTitleCase(key));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvValue;
        TextView tvType;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvValue = itemView.findViewById(R.id.tv_value);
            tvType = itemView.findViewById(R.id.tv_type);
        }
    }

}
