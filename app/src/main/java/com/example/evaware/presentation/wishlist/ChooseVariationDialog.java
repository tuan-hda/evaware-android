package com.example.evaware.presentation.wishlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.evaware.R;
import com.example.evaware.data.model.BagItemModel;
import com.example.evaware.data.model.SavedModel;
import com.example.evaware.data.model.VariationProductModel;
import com.example.evaware.presentation.bag.BagViewModel;
import com.example.evaware.presentation.product.ProductViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChooseVariationDialog extends BottomSheetDialogFragment {
    MaterialButton button;
    GridView gridView;
    GripVariationAdapter adapter;
    ProductViewModel productViewModel;
    BagViewModel bagViewModel;
    SavedModel savedItem;
    FavorItemAdapter favorAdapter;

    public ChooseVariationDialog(SavedModel item, FavorItemAdapter favorItemAdapter) {
        savedItem = item;
        favorAdapter = favorItemAdapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_variation, container, false);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        bagViewModel = new ViewModelProvider(this).get(BagViewModel.class);

        getElement(view);
        setUpButton();

        return view;
    }

    public void getElement(View view) {
        button = view.findViewById(R.id.chooseVariation_bt_select);
        gridView = view.findViewById(R.id.chooseVariation_gv_variation);

        productViewModel.getVariations(savedItem.productRef.getId()).observe(this, variants->{
            adapter = new GripVariationAdapter(getActivity(), variants);
            gridView.setAdapter(adapter);
        });
    }

    public void setUpButton() {
        button.setOnClickListener(view1 -> {
            //add to bag
            BagItemModel bagItemModel = new BagItemModel();
            bagItemModel.product_ref = savedItem.productRef;
            bagItemModel.qty = 1;
            bagItemModel.created_at = (new Timestamp(new Date()));
            bagItemModel.updated_at = (new Timestamp(new Date()));
            productViewModel.getVariationRef(savedItem.productRef.getId(), adapter.selectedId).observe(this, variationRef -> {
                bagItemModel.variation_ref = variationRef;
                bagViewModel.addItem(bagItemModel);

                //remove from wishlist
                favorAdapter.removeSavedItem(savedItem);
                Toast.makeText(getContext(), "Moved to cart", Toast.LENGTH_SHORT).show();
                dismiss();
            });
        });
    }
}