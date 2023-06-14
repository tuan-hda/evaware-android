package com.example.evaware.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.evaware.R;
import com.example.evaware.data.model.CountryModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CountryListAdapter extends BaseAdapter implements Filterable {

    private Context mContext;
    private List<CountryModel> mCountries;
    private List<CountryModel> mFilteredCountries;

    public CountryListAdapter(Context context, List<CountryModel> countries) {
        mContext = context;
        mCountries = countries;
        mFilteredCountries = new ArrayList<>(mCountries); // Copy original list
    }

    @Override
    public int getCount() {
        return mFilteredCountries.size();
    }

    @Override
    public Object getItem(int position) {
        return mFilteredCountries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the layout file for each row
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.country_item, parent, false);
        }

        // Get references to the ImageView and TextView
        ImageView countryImage = convertView.findViewById(R.id.country_image);
        TextView countryName = convertView.findViewById(R.id.country_name);

        // Set the country image and name
        CountryModel country = mFilteredCountries.get(position);
        countryImage.setImageResource(country.getImageResourceId());
        countryName.setText(country.getName());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<CountryModel> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    // If the search query is empty, return the original list
                    filteredList.addAll(mCountries);
                } else {
                    // Filter the list based on the search query
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (CountryModel country : mCountries) {
                        if (country.getName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(country);
                        }
                    }
                }

                filterResults.values = filteredList;
                filterResults.count = filteredList.size();

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilteredCountries.clear();
                mFilteredCountries.addAll((List<CountryModel>) results.values);
                notifyDataSetChanged();
            }
        };

        return filter;
    }
}
