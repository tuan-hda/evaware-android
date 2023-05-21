package com.example.evaware.presentation.address;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evaware.R;
import com.example.evaware.data.model.AddressModel;
import com.example.evaware.databinding.DeliveryAddressItemBinding;

import java.util.List;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ViewHolder> {
    private final Activity context;
    private final List<AddressModel> addresses;
    private int selectedIndex = 0;
    private boolean isPlain = false;

    public AddressListAdapter(Activity context, List<AddressModel> addresses) {
        this.context = context;
        this.addresses = addresses;
    }

    public AddressListAdapter(Activity context, List<AddressModel> addresses, boolean isPlain) {
        this.context = context;
        this.addresses = addresses;
        this.isPlain = isPlain;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DeliveryAddressItemBinding binding = DeliveryAddressItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddressModel address = addresses.get(position);
        String addressStr = address.city + ", " + address.district + ", " + address.ward + "," +
                " " + address.street;
        String contactStr = address.name + ", " + address.phone;
        holder.binding.textCard.setText(addressStr);
        holder.binding.textContact.setText(contactStr);

        if (isPlain) {
            holder.binding.indicator.setVisibility(View.GONE);
        } else {
            if (selectedIndex == position) {
                holder.binding.indicator.setBackgroundResource(R.drawable.select_indicator);
            } else {
                holder.binding.indicator.setBackgroundResource(R.drawable.unselect_indicator);
            }
        }

        holder.binding.getRoot().setOnClickListener(v -> {
            int old = selectedIndex;
            selectedIndex = position;
            notifyItemChanged(old);
            notifyItemChanged(position);
        });
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        ViewHolder viewHolder = null;
//        if (view == null) {
//            viewHolder = new ViewHolder();
//            LayoutInflater inflater = context.getLayoutInflater();
//            view = inflater.inflate(R.layout.delivery_address_item, viewGroup, false);
//
//            viewHolder.textAddress = view.findViewById(R.id.text_card);
//            viewHolder.textContact = view.findViewById(R.id.text_exp);
//            viewHolder.indicator = view.findViewById(R.id.indicator);
//            view.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) view.getTag();
//        }
//
//        AddressModel address = addresses.get(i);
//        String addressStr = address.city +  ", " + address.district + ", " + address.ward + "," +
//                " " + address.street;
//        String contactStr = address.name + ", " + address.phone;
//        viewHolder.textAddress.setText(addressStr);
//        viewHolder.textContact.setText(contactStr);
//
//        if (selectedIndex == i) {
//            viewHolder.indicator.setBackgroundResource(R.drawable.select_indicator);
//        } else {
//            viewHolder.indicator.setBackgroundResource(R.drawable.unselect_indicator);
//        }
//
//        view.setOnClickListener(view1 -> {
//            selectedIndex = i;
//            notifyDataSetChanged();
//        });
//
//        return view;
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        DeliveryAddressItemBinding binding;

        public ViewHolder(@NonNull DeliveryAddressItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
