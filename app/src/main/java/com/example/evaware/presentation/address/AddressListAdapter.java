package com.example.evaware.presentation.address;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.evaware.R;
import com.example.evaware.data.model.AddressModel;

import java.util.List;

public class AddressListAdapter extends BaseAdapter {
    private final Activity context;
    private final List<AddressModel> addresses;
    private int selectedIndex = 0;

    public AddressListAdapter(Activity context, List<AddressModel> addresses) {
        this.context = context;
        this.addresses = addresses;
    }

    @Override
    public int getCount() {
        return addresses.size();
    }

    @Override
    public Object getItem(int i) {
        return addresses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.delivery_address_item, viewGroup, false);

            viewHolder.textAddress = view.findViewById(R.id.text_address);
            viewHolder.textContact = view.findViewById(R.id.text_contact);
            viewHolder.indicator = view.findViewById(R.id.indicator);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        AddressModel address = addresses.get(i);
        String addressStr = address.province +  ", " + address.district + ", " + address.ward + "," +
                " " + address.street;
        String contactStr = address.fullName + ", " + address.phone;
        viewHolder.textAddress.setText(addressStr);
        viewHolder.textContact.setText(contactStr);

        if (selectedIndex == i) {
            viewHolder.indicator.setBackgroundResource(R.drawable.select_indicator);
        } else {
            viewHolder.indicator.setBackgroundResource(R.drawable.unselect_indicator);
        }

        view.setOnClickListener(view1 -> {
            selectedIndex = i;
            notifyDataSetChanged();
        });

        return view;
    }

    static class ViewHolder {
        TextView textAddress, textContact;
        CardView indicator;
    }
}
