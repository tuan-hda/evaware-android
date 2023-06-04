package com.example.evaware.presentation.catalog;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.evaware.data.model.ProductModel;
import com.example.evaware.presentation.product.ProductActivity;
import com.example.evaware.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CatalogAdapter extends ArrayAdapter<ProductModel> {

    private Context context;
    private List<ProductModel> dataList;

    public CatalogAdapter(@NonNull Context context, int resource, @NonNull List<ProductModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.dataList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.tvName = convertView.findViewById(R.id.tv_product_name);
            viewHolder.tvDescription = convertView.findViewById(R.id.tv_product_description);
            viewHolder.imvProduct = convertView.findViewById(R.id.imv_product_img);
            viewHolder.imvLoveLike = convertView.findViewById(R.id.iv_love_like);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final ProductModel item = dataList.get(position);
        viewHolder.tvName.setText(item.getName());
        viewHolder.tvDescription.setText(item.getDesc());
        Picasso.with(context)
                .load(item.getImg_url())
                .into(viewHolder.imvProduct);

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductActivity.class);
            intent.putExtra("productModelId", item.getId());
            context.startActivity(intent);
        });
        viewHolder.imvLoveLike.setOnClickListener(view->{
            Log.d("dcm", "love imgv");
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView tvName;
        TextView tvDescription;
        ImageView imvProduct;
        ImageView imvLoveLike;
    }
}

