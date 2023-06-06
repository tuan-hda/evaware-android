package com.example.evaware.presentation.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.evaware.R;
import com.example.evaware.databinding.FragmentAccountBinding;
import com.example.evaware.databinding.FragmentUserBinding;
import com.example.evaware.presentation.address.AddressBookActivity;
import com.example.evaware.presentation.auth.TestLoginActivity;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.User;

import java.util.Objects;

public class Account extends Fragment {
    FragmentAccountBinding binding;
    FirebaseAuth auth;

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
        auth = FirebaseAuth.getInstance();
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpButtons();
        setUpUserInfo();
    }

    private void setUpUserInfo(){
        FirebaseUser user = auth.getCurrentUser();

        String name = user.getDisplayName();
        String phoneNum = user.getPhoneNumber();

        binding.accountTvName.setText(name == "" ? user.getEmail() : name);
        binding.accountTvPhoneNum.setText(phoneNum == "" ? "Add your phone number" : phoneNum);
    }

    private void setUpButtons() {
        binding.accountLlMyDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, new MyDetail())
                        .commit();
            }
        });

        binding.accountLlSignOut.setOnClickListener(view -> {
            auth.signOut();
            LoginManager.getInstance().logOut();
            GoogleSignInOptions gso =
                    new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
            GoogleSignInClient gsc = GoogleSignIn.getClient((Activity)getContext(), gso);
            gsc.signOut();

            Intent intent = new Intent(getActivity(), TestLoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            ((Activity)getContext()).startActivity(intent);
            ((Activity)getContext()).finish();
        });

        binding.accountLlAddress.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), AddressBookActivity.class);
            requireActivity().startActivity(intent);
        });
    }
}