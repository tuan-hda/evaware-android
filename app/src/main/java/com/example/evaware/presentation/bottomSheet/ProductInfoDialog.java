package com.example.evaware.presentation.bottomSheet;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evaware.R;
import com.example.evaware.data.model.CompositionModel;
import com.example.evaware.data.model.MeasurementModel;
import com.example.evaware.presentation.product.ProductInfoAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductInfoDialog extends BottomSheetDialogFragment {
    private List<HashMap<String, Object>> composition;
    private List<HashMap<String, Object>>  measurements;

    // Set the composition data
    public void setComposition(List<HashMap<String, Object>>  composition) {
        this.composition = composition;
    }

    // Set the measurements data
    public void setMeasurements(List<HashMap<String, Object>>  measurements) {
        this.measurements = measurements;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_info, container, false);
        ImageView closeButton = view.findViewById(R.id.ibtn_close);

        RecyclerView rvMeasurements = view.findViewById(R.id.rv_measurements);
        RecyclerView rvComposition = view.findViewById(R.id.rv_composition);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        // Create a HashMap for composition data

        // Access the composition and measurements data and do something with it
        if (composition != null) {
            ProductInfoAdapter adapter = new ProductInfoAdapter(composition);
            rvComposition.setLayoutManager(new LinearLayoutManager(requireContext()));
            rvComposition.setAdapter(adapter);
        }

        if (measurements != null) {
            ProductInfoAdapter adapter = new ProductInfoAdapter(measurements);
            rvMeasurements.setLayoutManager(new LinearLayoutManager(requireContext()));
            rvMeasurements.setAdapter(adapter);
        }

        return view;
    }
}