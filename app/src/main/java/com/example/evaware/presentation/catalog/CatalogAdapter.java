package com.example.evaware.presentation.catalog;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.evaware.data.model.ProductModel;
import com.example.evaware.data.model.WishItemModel;
import com.example.evaware.presentation.product.ProductActivity;
import com.example.evaware.R;
import com.example.evaware.presentation.product.ProductViewModel;
import com.example.evaware.presentation.wishlist.WishViewModel;
import com.example.evaware.utils.CurrencyFormat;
import com.example.evaware.utils.GlobalStore;
import com.google.firebase.Timestamp;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class CatalogAdapter extends ArrayAdapter<ProductModel> {

    private Context context;
    private List<ProductModel> dataList;
    private WishViewModel wishViewModel;
    private ProductViewModel productViewModel;

    public CatalogAdapter(@NonNull Context context, int resource, @NonNull List<ProductModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.dataList = objects;
        wishViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(WishViewModel.class);
        productViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(ProductViewModel.class);
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
            viewHolder.tvPrice = convertView.findViewById(R.id.text_price);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final ProductModel item = dataList.get(position);
        viewHolder.tvName.setText(item.getName());
        viewHolder.tvDescription.setText(item.getDesc());
        viewHolder.tvPrice.setText(CurrencyFormat.getFormattedPrice(item.getPrice()));
        Picasso.with(context)
                .load(item.image_thumbnail)
                .into(viewHolder.imvProduct);
        if(item.saved){
            viewHolder.imvLoveLike.setBackgroundResource(R.drawable.heart_filled);
        }else{
            viewHolder.imvLoveLike.setBackgroundResource(R.drawable.heart);
        }

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductActivity.class);
            intent.putExtra("productModelId", item.getId());
            context.startActivity(intent);
        });

        viewHolder.imvLoveLike.setOnClickListener(view->{
            List<WishItemModel> list = (List<WishItemModel>) GlobalStore.getInstance().getData("wishList");
            if (list == null) {
                return;
            }
            if(item.saved){
                for (WishItemModel listItem : list) {
                    if (listItem.getProduct_ref().getId().equals(item.getId())) {
                        wishViewModel.remove(listItem.getId()).observe((LifecycleOwner) context, message->{
                            list.remove(listItem);
                            viewHolder.imvLoveLike.setBackgroundResource(R.drawable.heart);
                        });

                    }
                }
                item.saved = false;
            }else{
                WishItemModel wishItemModel = new WishItemModel();
                wishItemModel.setCreate_at(new Timestamp(new Date()));
                wishItemModel.setUpdate_at((new Timestamp(new Date())));
                productViewModel.getProductRefBbyId(item.getId()).observe((LifecycleOwner) context, productRef->{
                    wishItemModel.setProduct_ref(productRef);
                    wishViewModel.addWishList(wishItemModel).observe((LifecycleOwner) context, wishId -> {
                        wishItemModel.setId(wishId);
                        list.add(wishItemModel);
                        viewHolder.imvLoveLike.setBackgroundResource(R.drawable.heart_filled);
                        item.saved = true;
                    });
                });
            }
            GlobalStore.getInstance().setData("wishList", list);
        });
        List<WishItemModel> list = (List<WishItemModel>) GlobalStore.getInstance().getData("wishList");
        if (list == null) {
            return convertView;
        }
        for(WishItemModel wishItemModel : list) {
            if(wishItemModel.getProduct_ref().getId().equals(item.getId())) {
                viewHolder.imvLoveLike.setBackgroundResource(R.drawable.heart_filled);
                item.saved = true;
            }
        }

        return convertView;
    }


    private static class ViewHolder {
        TextView tvName, tvPrice;
        TextView tvDescription;
        ImageView imvProduct;
        ImageButton imvLoveLike;
    }
}

