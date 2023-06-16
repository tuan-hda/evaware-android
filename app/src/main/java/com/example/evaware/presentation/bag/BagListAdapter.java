package com.example.evaware.presentation.bag;

import static com.example.evaware.utils.CurrencyFormat.getFormattedPrice;
import static com.example.evaware.utils.SnackBar.showSnackDisable;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.evaware.R;
import com.example.evaware.data.model.BagItemModel;
import com.example.evaware.data.model.ImageModel;
import com.example.evaware.data.repo.BagRepository;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BagListAdapter extends BaseAdapter {
    private Activity context;
    private List<BagItemModel> bagList;
    Boolean isPlain = false;
    private BagViewModel vm;
    private static final String TAG = "BagListAdapter";

    public BagListAdapter(Activity context, List<BagItemModel> bagList, BagViewModel vm) {
        this.context = context;
        this.bagList = bagList;
        this.vm = vm;
    }

    public BagListAdapter(Activity context, List<BagItemModel> bagList, Boolean isPlain) {
        this.context = context;
        this.bagList = bagList;
        this.isPlain = isPlain;
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
    public View getView(int i, @Nullable View view, ViewGroup viewGroup) {
        ViewHolder mViewHolder = null;
        if (view == null) {
            mViewHolder = new ViewHolder();
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.bag_item, viewGroup, false);

            mViewHolder.itemImage = view.findViewById(R.id.img);
            mViewHolder.textPrice = view.findViewById(R.id.text_price);
            mViewHolder.textDesc = view.findViewById(R.id.text_desc);
            mViewHolder.btnDelete = view.findViewById(R.id.btn_delete);
            mViewHolder.textQtyAlt = view.findViewById(R.id.text_qty_alt);

            LinearLayout stepper = view.findViewById(R.id.stepper);
            mViewHolder.stepper = stepper;
            mViewHolder.textQty = stepper.findViewById(R.id.text_qty);
            mViewHolder.btnMinus = stepper.findViewById(R.id.btn_minus);
            mViewHolder.btnPlus = stepper.findViewById(R.id.btn_plus);

            view.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) view.getTag();
        }

//        Set value
        BagItemModel item = bagList.get(i);
        String img = "https://www.universalfurniture.com/images/pages/home/2022/U030501_RM.jpg?v=fcYu7oTsSUAyVfxWs5MbR2L0Jpeg0JzIiZ4Eaa_BqPw";
        if (item.variation.images != null) {
            String imgUrl = item.variation.images.get(0).img_url;
            if (imgUrl != null) {
                img = imgUrl;
            }
        }
        Picasso.with(context).load(img).into(mViewHolder.itemImage);
//        mViewHolder.itemImage.setImageResource(R.drawable.evaware_icon);
        mViewHolder.textDesc.setText(item.product.desc);
        mViewHolder.textPrice.setText(getFormattedPrice(item.product.price));
        mViewHolder.textQty.setText(item.qty + "");
//        End set value

        checkQty(mViewHolder);

//        Set events
        ViewHolder finalMViewHolder = mViewHolder;
        mViewHolder.btnMinus.setOnClickListener(view1 -> {
            if (checkQty(finalMViewHolder) == 1) return;
            vm.changeQty(i, -1);
            checkQty(finalMViewHolder);
        });
        mViewHolder.btnPlus.setOnClickListener(view1 -> {
            vm.changeQty(i, 1);
            checkQty(finalMViewHolder);
        });

        View finalView = view;
        mViewHolder.btnDelete.setOnClickListener(view1 -> {
            Snackbar snackbar = showSnackDisable(context, finalView, viewGroup);
            vm.removeItem(i, snackbar);
        });
        mViewHolder.textQtyAlt.setText("Qty: " + item.qty);

        if (isPlain) {
            mViewHolder.btnDelete.setVisibility(View.GONE);
            mViewHolder.stepper.setVisibility(View.GONE);
        } else {
            mViewHolder.textQtyAlt.setVisibility(View.GONE);
        }


        return view;
    }

    private int checkQty(ViewHolder holder) {
        int qty = Integer.parseInt(holder.textQty.getText().toString());

        if (qty == 1) {
            holder.btnMinus.setColorFilter(context.getResources().getColor(R.color.giratina_200, context.getTheme()));
        } else {
            holder.btnMinus.setColorFilter(0x000);
        }

        return qty;
    }

    static class ViewHolder {
        ImageView itemImage;
        TextView textPrice, textDesc, textQty, textQtyAlt;
        ImageButton btnDelete, btnMinus, btnPlus;
        LinearLayout stepper;
    }
}
