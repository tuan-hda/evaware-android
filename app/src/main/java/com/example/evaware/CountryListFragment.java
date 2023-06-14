package com.example.evaware;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.evaware.data.model.CountryModel;
import com.example.evaware.databinding.FragmentCountryListBinding;
import com.example.evaware.presentation.adapter.CountryListAdapter;

import java.util.ArrayList;
import java.util.List;


public class CountryListFragment extends Fragment {

    private FragmentCountryListBinding binding;
    private ListView mCountryListView;
    private CountryListAdapter mCountryListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCountryListBinding.inflate(inflater, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        // Get a reference to the ListView
        mCountryListView = binding.countryListView;

        // Create a list of countries
        List<CountryModel> countries = new ArrayList<>();
        countries.add(new CountryModel(R.drawable.ic_country_usa, "United States"));
        countries.add(new CountryModel(R.drawable.ic_country_uk, "United Kingdom"));
        countries.add(new CountryModel(R.drawable.ic_country_canada, "Canada"));
        countries.add(new CountryModel(R.drawable.ic_country_australia, "Australia"));

        // Create a new adapter for the ListView
        mCountryListAdapter = new CountryListAdapter(getContext(), countries);

        // Set the adapter on the ListView
        mCountryListView.setAdapter(mCountryListAdapter);

        // Set a click listener on the ListView
        mCountryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle click on a country item
                CountryModel selectedCountry = (CountryModel) parent.getItemAtPosition(position);
                Toast.makeText(getContext(), "You selected " + selectedCountry.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
