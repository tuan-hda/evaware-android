package com.example.evaware.presentation.wishlist;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.evaware.R;
import com.example.evaware.data.model.VariationProductModel;

import java.util.ArrayList;
import java.util.List;

public class GripVariationAdapter extends BaseAdapter {
    Activity activity;
    List<VariationProductModel> dataList;
    public String selectedId;

    public GripVariationAdapter(Activity activity, List<VariationProductModel> dataList) {
        this.activity = activity;
        this.dataList = dataList;
        selectedId = dataList.get(0).getId();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder mViewHolder = null;
        if (view == null) {
            mViewHolder = new GripVariationAdapter.ViewHolder();
            LayoutInflater inflater = activity.getLayoutInflater();
            view = inflater.inflate(R.layout.item_product_variation, null);

            mViewHolder.textView = view.findViewById(R.id.tv_variation_name);
            mViewHolder.llContainer = view.findViewById(R.id.ll_container);
            mViewHolder.imIconVariation = view.findViewById(R.id.im_icon_variation);


            view.setTag(mViewHolder);
        } else {
            mViewHolder = (GripVariationAdapter.ViewHolder) view.getTag();
        }

        VariationProductModel detail = dataList.get(i);
        mViewHolder.textView.setText(detail.getName());
        if (selectedId == detail.getId()) {
            mViewHolder.llContainer.setBackgroundResource(R.drawable.round_button_black);
            mViewHolder.textView.setTextColor(Color.WHITE);
        } else {
            mViewHolder.llContainer.setBackgroundResource(R.drawable.round_button_white);
            mViewHolder.textView.setTextColor(Color.BLACK);
        }

        mViewHolder.llContainer.setOnClickListener(view1 -> {
            selectedId = detail.getId();
            notifyDataSetChanged();
        });

        return view;
    }

    static class ViewHolder{
        TextView textView;
        LinearLayout llContainer;
        ImageView imIconVariation;
    }
}
