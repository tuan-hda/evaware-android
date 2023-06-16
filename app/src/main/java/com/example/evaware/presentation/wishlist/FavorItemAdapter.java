package com.example.evaware.presentation.wishlist;

import static android.content.Intent.getIntent;
import static com.example.evaware.utils.SnackBar.showSnackDisable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.evaware.R;
import com.example.evaware.data.model.BagItemModel;
import com.example.evaware.data.model.SavedModel;
import com.example.evaware.presentation.bag.BagListAdapter;
import com.example.evaware.presentation.product.ProductActivity;
import com.example.evaware.presentation.product.ProductViewModel;
import com.example.evaware.utils.GlobalStore;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class FavorItemAdapter extends BaseAdapter {
    private static final String TAG = "FavorItemAdapter";
    Activity activity;
    List<SavedModel> itemList;
    WishViewModel vm;
    ChooseVariationDialogListener dialogListener;

    public FavorItemAdapter(Activity activity, List<SavedModel> itemList, WishViewModel vm) {
        this.activity = activity;
        this.itemList = itemList;
        this.vm = vm;
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
        ViewHolder mViewHolder = null;
        if (view == null) {
            mViewHolder = new ViewHolder();
            LayoutInflater inflater = activity.getLayoutInflater();
            view = inflater.inflate(R.layout.item_small_card, null);

            mViewHolder.image = view.findViewById(R.id.itemSmallCard_iv_product);
            mViewHolder.price = view.findViewById(R.id.itemSmallCard_tv_price);
            mViewHolder.desc = view.findViewById(R.id.itemSmallCard_tv_desc);
            mViewHolder.btMoveToBag = view.findViewById(R.id.itemSmallCard_bt_moveToBag);
            mViewHolder.ivRemove = view.findViewById(R.id.itemSmallCard_iv_remove);
            mViewHolder.llItem = view.findViewById(R.id.itemSmallCard_ll_item);


            view.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) view.getTag();
        }

//       Set value
        SavedModel item = itemList.get(i);

        Picasso.with(activity)
                .load(item.imageUrl)
                .into(mViewHolder.image);

        mViewHolder.price.setText("$" + item.price);
        mViewHolder.desc.setText(item.desc);
//      Set event
        View finalView = view;
        //Remove item
        mViewHolder.ivRemove.setOnClickListener(view1 -> {
            Snackbar snackbar = showSnackDisable(activity, finalView, viewGroup);
            removeSavedItem(item, snackbar);
        });

        //Go to product screen
        mViewHolder.llItem.setOnClickListener(view1 -> {
            Intent intent = new Intent(activity, ProductActivity.class);
            intent.putExtra("productModelId", item.productRef.getId());
            activity.startActivity(intent);
        });

        //Move to bag
        mViewHolder.btMoveToBag.setOnClickListener(view1 -> {
            if (dialogListener != null) {
                dialogListener.onMoveToBagClicked(item);
            }
        });

        return view;
    }

    public interface ChooseVariationDialogListener {
        void onMoveToBagClicked(SavedModel item);
    }

    public void setChooseVariationDialogListener(ChooseVariationDialogListener listener) {
        this.dialogListener = listener;
    }

    public void removeSavedItem(SavedModel item) {
        removeSavedItem(item, null);
    }

    public void removeSavedItem(SavedModel item, Snackbar snackbar) {
        if (activity instanceof ViewModelStoreOwner) {
            ViewModelProvider viewModelProvider = new ViewModelProvider((ViewModelStoreOwner) activity);
            WishViewModel viewModel = viewModelProvider.get(WishViewModel.class);
            viewModel.remove(item.wishListRef.getId(), snackbar).observe((LifecycleOwner) activity, message -> {
                itemList.remove(item);
                notifyDataSetChanged();
                Log.e(TAG, message);
            });
        } else {
            Log.e(TAG, "Activity does not implement ViewModelStoreOwner");
        }
    }

    public void updateItemList(List<SavedModel> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        ImageView image;
        TextView price;
        TextView desc;

        Button btMoveToBag;
        ImageView ivRemove;
        LinearLayout llItem;
    }
}
