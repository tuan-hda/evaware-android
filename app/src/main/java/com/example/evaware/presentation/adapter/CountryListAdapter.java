package com.example.evaware.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.evaware.R;
import com.example.evaware.data.model.CountryModel;

import java.util.List;

public class CountryListAdapter extends BaseAdapter {

    private Context mContext;
    private List<CountryModel> mCountries;

    public CountryListAdapter(Context context, List<CountryModel> countries) {
        mContext = context;
        mCountries = countries;
    }

    @Override
    public int getCount() {
        return mCountries.size();
    }

    @Override
    public Object getItem(int position) {
        return mCountries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the layout file for each row
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_country, parent, false);
        }

        // Get references to the ImageView and TextView
        ImageView countryImage = convertView.findViewById(R.id.country_image);
        TextView countryName = convertView.findViewById(R.id.country_name);

        // Set the country image and name
        CountryModel country = mCountries.get(position);
        countryImage.setImageResource(country.getImageResourceId());
        countryName.setText(country.getName());

        return convertView;
    }
}
