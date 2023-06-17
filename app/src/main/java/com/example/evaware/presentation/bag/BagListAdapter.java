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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evaware.R;
import com.example.evaware.data.model.BagItemModel;
import com.example.evaware.data.model.ImageModel;
import com.example.evaware.data.repo.BagRepository;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BagListAdapter extends RecyclerView.Adapter<BagListAdapter.ViewHolder> {
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


    public List<BagItemModel> getBagList() {
        return bagList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder mViewHolder = null;
        mViewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.bag_item, parent, false));

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Set value
        BagItemModel item = bagList.get(position);
        String img = "https://www.universalfurniture.com/images/pages/home/2022/U030501_RM.jpg?v=fcYu7oTsSUAyVfxWs5MbR2L0Jpeg0JzIiZ4Eaa_BqPw";
        if (item.variation.images != null) {
            String imgUrl = item.variation.images.get(0).img_url;
            if (imgUrl != null) {
                img = imgUrl;
            }
        }
        Picasso.with(context).load(img).into(holder.itemImage);
//        holder.itemImage.setImageResource(R.drawable.evaware_icon);
        holder.textDesc.setText(item.product.desc);
        holder.textPrice.setText(getFormattedPrice(item.product.price));
        holder.textQty.setText(item.qty + "");
//        End set value

        checkQty(holder);

//        Set events
        ViewHolder finalMViewHolder = holder;
        holder.btnMinus.setOnClickListener(view1 -> {
            if (checkQty(finalMViewHolder) == 1) return;
            vm.changeQty(position, -1);
            checkQty(finalMViewHolder);
        });
        holder.btnPlus.setOnClickListener(view1 -> {
            vm.changeQty(position, 1);
            checkQty(finalMViewHolder);
        });

        View finalView = holder.itemView;
        holder.btnDelete.setOnClickListener(view1 -> {
            Snackbar snackbar = showSnackDisable(context, finalView, (ViewGroup) holder.itemView.getParent());
            vm.removeItem(position, snackbar);
        });
        holder.textVariation.setText("Variation: " + item.variation.name);
        holder.textQtyAlt.setText("Qty: " + item.qty);

        if (isPlain) {
            holder.btnDelete.setVisibility(View.GONE);
            holder.stepper.setVisibility(View.GONE);
        } else {
            holder.textQtyAlt.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return bagList.size();
    }

//    @Override
//    public View getView(int position, @Nullable View view, ViewGroup viewGroup) {
//        ViewHolder holder = null;
//        if (view == null) {
//            holder = new ViewHolder();
//            LayoutInflater inflater = context.getLayoutInflater();
//            view = inflater.inflate(R.layout.bag_item, viewGroup, false);
//
//            mViewHolder.itemImage = view.findViewById(R.id.img);
//            mViewHolder.textPrice = view.findViewById(R.id.text_price);
//            mViewHolder.textDesc = view.findViewById(R.id.text_desc);
//            mViewHolder.btnDelete = view.findViewById(R.id.btn_delete);
//            mViewHolder.textQtyAlt = view.findViewById(R.id.text_qty_alt);
//            mViewHolder.textVariation = view.findViewById(R.id.text_variation);
//
//            LinearLayout stepper = view.findViewById(R.id.stepper);
//            mViewHolder.stepper = stepper;
//            mViewHolder.textQty = stepper.findViewById(R.id.text_qty);
//            mViewHolder.btnMinus = stepper.findViewById(R.id.btn_minus);
//            mViewHolder.btnPlus = stepper.findViewById(R.id.btn_plus);
//
//            view.setTag(mViewHolder);
//        } else {
//            mViewHolder = (ViewHolder) view.getTag();
//        }
//
//
//        return view;
//    }

    private int checkQty(ViewHolder holder) {
        int qty = Integer.parseInt(holder.textQty.getText().toString());

        if (qty == 1) {
            holder.btnMinus.setColorFilter(context.getResources().getColor(R.color.giratina_200, context.getTheme()));
        } else {
            holder.btnMinus.setColorFilter(0x000);
        }

        return qty;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView textPrice, textDesc, textQty, textQtyAlt, textVariation;
        ImageButton btnDelete, btnMinus, btnPlus;
        LinearLayout stepper;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.img);
            textPrice = itemView.findViewById(R.id.text_price);
            textDesc = itemView.findViewById(R.id.text_desc);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            textQtyAlt = itemView.findViewById(R.id.text_qty_alt);
            textVariation = itemView.findViewById(R.id.text_variation);

            LinearLayout stepper = itemView.findViewById(R.id.stepper);
            stepper = stepper;
            textQty = stepper.findViewById(R.id.text_qty);
            btnMinus = stepper.findViewById(R.id.btn_minus);
            btnPlus = stepper.findViewById(R.id.btn_plus);
        }
    }
}
