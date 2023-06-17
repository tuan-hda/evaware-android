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
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.evaware.data.model.CategoryModel;
import com.example.evaware.data.model.ProductModel;
import com.example.evaware.data.repo.ProductRepository;
import com.example.evaware.databinding.FragmentHomeBinding;
import com.example.evaware.presentation.catalog.CatalogActivity;
import com.example.evaware.presentation.catalog.CatalogAdapter;
import com.example.evaware.presentation.category.SearchCategoryActivity;
import com.example.evaware.presentation.search_product.SearchProductActivity;
import com.example.evaware.data.model.TypeofCategory;
import com.example.evaware.presentation.wishlist.WishViewModel;
import com.example.evaware.utils.GlobalStore;
import com.example.evaware.utils.LoadingDialog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private RecyclerView suggestList;
    private FragmentHomeBinding binding;
    private RecyclerView homeItemList;
    private List<TypeofCategory> types = new ArrayList<>();
    private LoadingDialog dialog;
    private WishViewModel wishViewModel;
    private ProductRepository repository;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        repository = new ProductRepository();
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

        } catch (Exception e) {
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
        handlePopular();

    }

    private void handlePopular() {
        Map<String, Integer> productSales = new HashMap<>();

        FirebaseFirestore.getInstance()
                .collection("orders")
                .whereEqualTo("status", 0)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Task<?>> tasks = new ArrayList<>();
                    for (DocumentSnapshot orderDoc : querySnapshot.getDocuments()) {
                        tasks.add(orderDoc.getReference().collection("order_items").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (DocumentSnapshot orderItem : queryDocumentSnapshots.getDocuments()) {
                                    DocumentReference productRef = (DocumentReference) orderItem.get("product_ref");
                                    if (productRef != null) {
                                        String productId = productRef.getId();
                                        int sales = productSales.getOrDefault(productId, 0) + 1;
                                        productSales.put(productId, sales);
                                    }
                                }
                            }
                        }));
                    }

                    Tasks.whenAllSuccess(tasks).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
                        @Override
                        public void onSuccess(List<Object> objects) {
                            List<Map.Entry<String, Integer>> sortedProducts = new ArrayList<>(productSales.entrySet());

                            sortedProducts.sort((e1, e2) -> {
                                int valueComparison = e2.getValue().compareTo(e1.getValue());

                                if (valueComparison != 0) {
                                    return valueComparison;
                                } else {
                                    return e1.getKey().compareTo(e2.getKey());
                                }
                            });
                            for (Map.Entry<String, Integer> item : sortedProducts) {
                                Log.e(TAG, "onSuccess: " + item.getKey() + " " + item.getValue());
                            }
                            int num = 6;
                            List<Task<?>> tasks1 = new ArrayList<>();
                            List<ProductModel> topSellingProducts = new ArrayList<>();
                            for (int i = 0; i< sortedProducts.size(); i++) {
                                topSellingProducts.add(new ProductModel());
                            }

                            for (int i = 0; i < Math.min(num, sortedProducts.size()); i++) {

                                tasks1.add(repository.getProductById(sortedProducts.get(i).getKey()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        for (int j = 0; j < sortedProducts.size();j ++) {
                                            if (Objects.equals(sortedProducts.get(j).getKey(), documentSnapshot.getId())) {
                                                topSellingProducts.set(j, documentSnapshot.toObject(ProductModel.class));
                                                break;
                                            }
                                        }
                                    }
                                }));
                            }

                            Tasks.whenAllSuccess(tasks1).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
                                @Override
                                public void onSuccess(List<Object> objects) {
                                    CatalogAdapter adapter = new CatalogAdapter(getActivity(), topSellingProducts.size(), topSellingProducts);
                                    binding.rvPopularList.setAdapter(adapter);
                                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) binding.rvPopularList.getLayoutParams();
                                    layoutParams.height = Math.min(topSellingProducts.size() / 2 * 836, 2508);
                                    binding.rvPopularList.setLayoutParams(layoutParams);

                                }
                            });
                        }
                    });


                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "handlePopular: ", e);
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
        binding.llSeeAll.setOnClickListener(view -> {
            Intent intent = new Intent(requireActivity(), SearchCategoryActivity.class);
            requireActivity().startActivity(intent);
        });
    }
}