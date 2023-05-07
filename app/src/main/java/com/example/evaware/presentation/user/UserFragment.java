package com.example.evaware.presentation.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.evaware.databinding.FragmentUserBinding;
import com.example.evaware.presentation.auth.TestLoginActivity;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class UserFragment extends Fragment {
    FragmentUserBinding binding;
    FirebaseAuth auth;

    public UserFragment() {
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
        binding = FragmentUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setUpButtons();
    }

    private void setUpButtons() {
        binding.btnLogout.setOnClickListener(view -> {
            auth.signOut();
            LoginManager.getInstance().logOut();
            GoogleSignInOptions gso =
                    new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
            GoogleSignInClient gsc = GoogleSignIn.getClient(getActivity(), gso);
            gsc.signOut();

            Intent intent = new Intent(getActivity(), TestLoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(intent);
            getActivity().finish();
        });
    }
}