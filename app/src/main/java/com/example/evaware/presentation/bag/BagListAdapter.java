package com.example.evaware.presentation.bag;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.evaware.R;
import com.example.evaware.data.model.BagItemModel;

import java.util.List;

public class BagListAdapter extends BaseAdapter {
    private Activity context;
    private List<BagItemModel> bagList;

    public BagListAdapter(Activity context, List<BagItemModel> bagList) {
        this.context = context;
        this.bagList = bagList;
    }

    @Override
    public int getCount() {
        return bagList.size();
    }

    @Override
    public Object getItem(int i) {
        return bagList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder mViewHolder = null;
        if (view == null) {
            mViewHolder = new ViewHolder();
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.bag_item, viewGroup, false);

            mViewHolder.itemImage = view.findViewById(R.id.img);
            mViewHolder.textPrice = view.findViewById(R.id.text_price);
            mViewHolder.textDesc = view.findViewById(R.id.text_desc);
            mViewHolder.btnDelete = view.findViewById(R.id.btn_delete);
            LinearLayout stepper = view.findViewById(R.id.stepper);
            mViewHolder.textQty = stepper.findViewById(R.id.text_qty);
            mViewHolder.btnMinus = stepper.findViewById(R.id.btn_minus);
            mViewHolder.btnPlus = stepper.findViewById(R.id.btn_plus);

            view.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) view.getTag();
        }

        BagItemModel item = bagList.get(i);
        mViewHolder.itemImage.setImageResource(R.drawable.evaware_icon);
        mViewHolder.textDesc.setText("Wooden product using one and ninety objects from Sing");

        checkQty(mViewHolder);

        ViewHolder finalMViewHolder = mViewHolder;
        mViewHolder.btnMinus.setOnClickListener(view1 -> {
            int qty = checkQty(finalMViewHolder);
            if (qty == 1) {
                return;
            }
            finalMViewHolder.textQty.setText(qty - 1 + "");
            checkQty(finalMViewHolder);
        });
        mViewHolder.btnPlus.setOnClickListener(view1 -> {
            int qty = checkQty(finalMViewHolder);
            finalMViewHolder.textQty.setText(qty + 1 + "");
            checkQty(finalMViewHolder);
        });

        return view;
    }

    private int checkQty(ViewHolder holder) {
        int qty = Integer.parseInt(holder.textQty.getText().toString());

        if (qty == 1) {
            holder.btnMinus.setColorFilter(context.getResources().getColor(R.color.giratina_200,
                    context.getTheme()));
        } else {
            holder.btnMinus.setColorFilter(0x000);
        }

        return qty;
    }

    static class ViewHolder {
        ImageView itemImage;
        TextView textPrice, textDesc, textQty;
        ImageButton btnDelete, btnMinus, btnPlus;
    }
}
