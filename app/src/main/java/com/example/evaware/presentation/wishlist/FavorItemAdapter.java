package com.example.evaware.presentation.wishlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.evaware.R;
import com.example.evaware.presentation.filter.Category;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class FavorItemAdapter extends BaseAdapter {
    Context context;
    List<FavorItem> itemList;
    LayoutInflater inflater;

    public FavorItemAdapter(Context context, List<FavorItem> itemList) {
        this.context = context;
        this.itemList = itemList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        FavorItem item = itemList.get(i);
        view = inflater.inflate(R.layout.item_small_card, null);

        ImageView image = view.findViewById(R.id.itemSmallCard_iv_product);
        TextView price = view.findViewById(R.id.itemSmallCard_tv_price);
        TextView desc = view.findViewById(R.id.itemSmallCard_tv_desc);

        image.setImageResource(item.getImage());
        price.setText("$"+item.getPrice());
        desc.setText(item.getDesc());

        return view;
    }
}
