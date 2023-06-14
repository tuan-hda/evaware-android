package com.example.evaware;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.evaware.data.model.CountryModel;
import com.example.evaware.databinding.DeliveryAddressItemBinding;
import com.example.evaware.databinding.FragmentDeliveryCountryBinding;
import com.example.evaware.presentation.adapter.CountryListAdapter;

import java.util.ArrayList;
import java.util.List;

public class DeliveryCountryFragment extends Fragment {

    private FragmentDeliveryCountryBinding binding;
    private ListView lvCountry;
    private CountryListAdapter mCountryListAdapter;
    private EditText edtSearch;
    private int lastSelectedPosition = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDeliveryCountryBinding.inflate(inflater, container, false);
        lvCountry = binding.lvCountry;
        edtSearch = binding.edtSearch;
        initView();

        return binding.getRoot();
    }

    private void initView() {
        List<CountryModel> countries = new ArrayList<>();
        countries.add(new CountryModel(R.drawable.ic_country_usa, "United States"));
        countries.add(new CountryModel(R.drawable.ic_country_uk, "United Kingdom"));
        countries.add(new CountryModel(R.drawable.ic_country_canada, "Canada"));
        countries.add(new CountryModel(R.drawable.ic_country_australia, "Australia"));

        // Create a new adapter for the ListView
        mCountryListAdapter = new CountryListAdapter(getContext(), countries);

        // Set the adapter on the ListView
        lvCountry.setAdapter(mCountryListAdapter);

        // Set a click listener on the ListView
        lvCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle click on a country item
                CountryModel selectedCountry = (CountryModel) parent.getItemAtPosition(position);
                Toast.makeText(getContext(), "You selected " + selectedCountry.getName(), Toast.LENGTH_SHORT).show();

                // Show checkmark on selected item
                ImageView ivCheckmark = view.findViewById(R.id.iv_checkmark);
                ivCheckmark.setVisibility(View.VISIBLE);

                // Hide checkmark on previously selected item, if any
                if (lastSelectedPosition != -1) {
                    View lastSelectedView = lvCountry.getChildAt(lastSelectedPosition - lvCountry.getFirstVisiblePosition());
                    if (lastSelectedView != null) {
                        ImageView ivLastCheckmark = lastSelectedView.findViewById(R.id.iv_checkmark);
                        ivLastCheckmark.setVisibility(View.INVISIBLE);
                    }
                }

                lastSelectedPosition = position;
            }
        });

        // Set a text change listener on the EditText for filtering the country list
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCountryListAdapter.getFilter().filter(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}

