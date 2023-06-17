package com.example.evaware.presentation.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.evaware.data.model.CategoryModel;
import com.example.evaware.databinding.FragmentHomeBinding;
import com.example.evaware.presentation.catalog.CatalogActivity;
import com.example.evaware.presentation.category.SearchCategoryActivity;
import com.example.evaware.presentation.search_product.SearchProductActivity;
import com.example.evaware.data.model.TypeofCategory;
import com.example.evaware.presentation.wishlist.WishViewModel;
import com.example.evaware.utils.GlobalStore;
import com.example.evaware.utils.LoadingDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private RecyclerView suggestList;
    private FragmentHomeBinding binding;
    private RecyclerView homeItemList;
    private List<TypeofCategory> types = new ArrayList<>();
    private LoadingDialog dialog;
    private WishViewModel wishViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        dialog = new LoadingDialog(getActivity());
        return binding.getRoot();
    }

    private void init() {
        suggestList = binding.horizontalList;
        homeItemList = binding.verticalList;
        suggestList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        homeItemList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            wishViewModel = new ViewModelProvider(this).get(WishViewModel.class);
    }


    public void loadData() {
        HomeViewModel viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        try {
            viewModel.getAllCategory().observe(requireActivity(), categoryModels -> {
                if (categoryModels.size() == 0) {
                    Toast.makeText(requireActivity(), "Empty", Toast.LENGTH_SHORT).show();
                } else {
                    List<CategoryModel> firstFourItems = categoryModels.subList(0, Math.min(categoryModels.size(), 4));
                    TypeOfCategoryAdapter adapter2 = new TypeOfCategoryAdapter(firstFourItems, getActivity(), HomeFragment.this);
                    homeItemList.setAdapter(adapter2);
                    dialog.dismissDialog();
                }
            });

        }catch (Exception e) {
            Log.d(TAG, "loadData: " + e.getMessage());
        }



        if (wishViewModel != null)
            wishViewModel.getWishList().observe(getActivity(), wishItemModels -> {
                GlobalStore.getInstance().setData("wishList", wishItemModels);
            });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog.showDialog();
        init();
        loadData();
        setUpBtn();

    }

    private void handlePopular() {
        // Step 1: Create a Map to store the total sales for each product
        Map<String, Integer> productSales;
        productSales = new HashMap<>();

        // Step 2: Query the orders collection
        FirebaseFirestore.getInstance()
                .collection("orders")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    // Step 3: Iterate over the orders
                    for (DocumentSnapshot orderDoc : querySnapshot.getDocuments()) {
                        // Step 4: Iterate over the order items
                        List<Map<String, Object>> orderItems = (List<Map<String, Object>>) orderDoc.get("order_items");
                        if (orderItems != null) {
                            for (Map<String, Object> orderItem : orderItems) {
                                DocumentReference productRef = (DocumentReference) orderItem.get("product");

                                // Step 5: Update the total sales for each product
                                if (productRef != null) {
                                    String productId = productRef.getId();
                                    int sales = productSales.getOrDefault(productId, 0) + 1;
                                    productSales.put(productId, sales);
                                }
                            }
                        }
                    }

                    // Sort the products based on total sales in descending order
                    List<Map.Entry<String, Integer>> sortedProducts = new ArrayList<>(productSales.entrySet());
                    sortedProducts.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

                    // Get the top-selling products (e.g., top 10)
                    int topN = 10;
                    List<String> topSellingProducts = new ArrayList<>();
                    for (int i = 0; i < Math.min(topN, sortedProducts.size()); i++) {
                        topSellingProducts.add(sortedProducts.get(i).getKey());
                    }

                    // Handle the top-selling products
                    // ...
                })
                .addOnFailureListener(e -> {
                    // Handle any errors
                    // ...
                });

    }

    private void setUpBtn() {
        binding.btnShowAll.setOnClickListener(view -> {
            Intent intent1 = new Intent(requireActivity(), SearchProductActivity.class);
            requireActivity().startActivity(intent1);
        });
        binding.cvSearchProduct.setOnClickListener(view -> {
            Intent intent1 = new Intent(requireActivity(), SearchProductActivity.class);
            requireActivity().startActivity(intent1);
        });
        binding.llSeeAll.setOnClickListener(view ->{
            Intent intent = new Intent(requireActivity(), SearchCategoryActivity.class);
            requireActivity().startActivity(intent);
        });
    }
}