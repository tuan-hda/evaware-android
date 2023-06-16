package com.example.evaware.presentation.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.evaware.R;
import com.example.evaware.data.model.UserModel;
import com.example.evaware.databinding.FragmentAccountBinding;
import com.example.evaware.presentation.address.AddressBookActivity;
import com.example.evaware.presentation.auth.AuthUserViewModel;
import com.example.evaware.presentation.auth.AuthViewModel;
import com.example.evaware.presentation.auth.TestLoginActivity;
import com.example.evaware.presentation.checkout.PaymentMethodActivity;
import com.example.evaware.presentation.order.MyOrders;
import com.example.evaware.presentation.other.Setting;
import com.example.evaware.presentation.payment.PaymentListActivity;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class Account extends Fragment {
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    FragmentAccountBinding binding;
    FragmentActivity activity;
    UserViewModel viewModel;
    AuthUserViewModel authViewModel;
    private static final String TAG = "Account";

    public Account() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = requireActivity();
        viewModel = new ViewModelProvider(activity).get(UserViewModel.class);
        authViewModel = new ViewModelProvider(activity).get(AuthUserViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpButtons();
        setUpUserInfo();
    }

    private void setUpUserInfo() {

        LifecycleOwner lifecycleOwner = getActivity();
        Observer<UserModel> userInfoObserver = null;

        userInfoObserver = userModel -> {
            if (userModel != null) {
                String phoneNum = userModel.phone;

                Picasso.with(getContext())
                        .load(userModel.img_url)
                        .placeholder(R.drawable.button_round)
                        .into(binding.accountIbAvt);

                binding.accountTvName.setText(userModel.name);
                binding.accountTvPhoneNum.setText(phoneNum == null ? "Add your phone number" : phoneNum);

            } else {
                authViewModel.createUser(FirebaseAuth.getInstance().getCurrentUser().getUid(), FirebaseAuth.getInstance().getCurrentUser().getEmail())
                        .addOnSuccessListener(unused -> {
                            viewModel.getUserInfo().removeObservers(lifecycleOwner);
                            setUpUserInfo();
                        });
            }
        };

        viewModel.getUserInfo().observe(lifecycleOwner, userInfoObserver);
    }

    private void setUpButtons() {
        binding.accountIbSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, new Setting())
                        .addToBackStack("Setting")
                        .commit();
            }
        });

        binding.accountLlUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, new MyDetail())
                        .addToBackStack("myDetail")
                        .commit();
            }
        });

        binding.accountLlPaymentMethod.setOnClickListener(view -> {
            Intent intent = new Intent(activity, PaymentListActivity.class);
            startActivity(intent);
        });

        binding.accountLlOder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, new MyOrders())
                        .addToBackStack("myOrders")
                        .commit();
            }
        });

        binding.accountLlMyDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, new MyDetail())
                        .addToBackStack("myDetail")
                        .commit();
            }
        });



        binding.accountLlSignOut.setOnClickListener(view -> {
            auth.signOut();
            LoginManager.getInstance().logOut();
            GoogleSignInOptions gso =
                    new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
            GoogleSignInClient gsc = GoogleSignIn.getClient((Activity) getContext(), gso);
            gsc.signOut();

            Intent intent = new Intent(getActivity(), TestLoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            ((Activity) getContext()).startActivity(intent);
            ((Activity) getContext()).finish();
        });

        binding.accountLlAddress.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), AddressBookActivity.class);
            requireActivity().startActivity(intent);
        });
    }
}