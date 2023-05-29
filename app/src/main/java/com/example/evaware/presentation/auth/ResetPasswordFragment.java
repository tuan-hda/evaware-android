package com.example.evaware.presentation.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.evaware.R;
import com.example.evaware.databinding.FragmentResetPasswordBinding;
import com.google.android.material.button.MaterialButton;


public class ResetPasswordFragment extends Fragment {

    private FragmentResetPasswordBinding binding;
    private MaterialButton btnResetPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        btnResetPassword = binding.btnResetPassword;
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ResetPasswordFragment.this).navigate(R.id.action_reset_password_fragment_to_password_change_fragment);
            }
        });
    }
}