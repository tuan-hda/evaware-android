package com.example.evaware.presentation.product;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evaware.R;
import com.example.evaware.data.model.VariationModelsDetail;

import java.util.List;

public class VariationProductAdapter extends RecyclerView.Adapter<VariationProductAdapter.ViewHolder> {

    private List<VariationModelsDetail> dataList;
    private int selectedItem = 0;
    private OnVariationProductListener onVariationProductListener;

    public VariationProductAdapter(List<VariationModelsDetail> dataList) {
        this.dataList = dataList;
    }

    public void setOnVariationProductListener(OnVariationProductListener listener) {
        this.onVariationProductListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_variation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String data = dataList.get(position).getModel().getName();
        holder.bind(data);
        if (selectedItem == position) {
            holder.llContainer.setBackgroundResource(R.drawable.round_button_black);
            holder.textView.setTextColor(Color.WHITE);
        } else {
            holder.llContainer.setBackgroundResource(R.drawable.round_button_white);
            holder.textView.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private LinearLayout llContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_variation_name);
            llContainer = itemView.findViewById(R.id.ll_container);

            itemView.setOnClickListener(v -> {
                int previousSelectedItem = selectedItem;
                selectedItem = getAdapterPosition();
                notifyItemChanged(previousSelectedItem);
                notifyItemChanged(selectedItem);

                if (onVariationProductListener != null) {
                    onVariationProductListener.onVariationProductClick(selectedItem);
                }
            });
        }

        public void bind(String data) {
            textView.setText(data);
        }
    }

    public interface OnVariationProductListener {
        void onVariationProductClick(int position);
    }
}