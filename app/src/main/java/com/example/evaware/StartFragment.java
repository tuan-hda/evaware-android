package com.example.evaware;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.evaware.databinding.FragmentStartBinding;
import com.google.android.material.button.MaterialButton;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public class StartFragment extends Fragment {

    private MaterialButton loginBtn;
    private MaterialButton createAccBtn;
    private FragmentStartBinding binding;

    public StartFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStartBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initView(view);
        return view;
    }

    private void initView(View view) {
        loginBtn = binding.btnLoginMethod;
        createAccBtn =binding.btnCreateAccountMethod;

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(StartFragment.this).navigate(R.id.action_start_fragment_to_login_fragment);
            }
        });
        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(StartFragment.this).navigate(R.id.action_start_fragment_to_sign_up_fragment);
            }
        });
    }
}