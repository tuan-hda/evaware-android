package com.example.evaware.presentation.auth;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.evaware.R;
import com.example.evaware.databinding.FragmentLoginBinding;
import com.example.evaware.utils.PasswordUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private TextView navToSignup;
    private EditText edtEmail;
    private EditText edtPassword;
    private MaterialButton btnLogin;
    private ImageButton btnBackToPrevious;
    Drawable eyeIcon;
    Drawable eyeSlashIcon;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eyeIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_eye);
        eyeIcon.setBounds(0, 0, eyeIcon.getIntrinsicWidth(), eyeIcon.getIntrinsicHeight());
        eyeSlashIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_eye_slash);
        eyeSlashIcon.setBounds(0, 0, eyeSlashIcon.getIntrinsicWidth(), eyeSlashIcon.getIntrinsicHeight());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initView(view);
        return view;
    }

    private void initView(View view) {

        navToSignup = binding.navToSignup;
        btnBackToPrevious = binding.btnBackToPrevious;
        edtEmail = binding.edtEmail;
        edtPassword = binding.edtPassword;
        btnLogin = binding.btnLogin;
        PasswordUtils.setupPasswordVisibilityToggle(edtPassword);

        // Set the icon to the right of the EditText

        btnBackToPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginFragment.this).navigateUp();
            }
        });
        navToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_login_fragment_to_sign_up_fragment);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    edtEmail.setError("Email is required");
                    edtEmail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    edtEmail.setError("Invalid email address");
                    edtEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    edtPassword.setError("Password is required");
                    edtPassword.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    edtPassword.setError("Password must be at least 6 characters long");
                    edtPassword.requestFocus();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    System.out.println("login successful");
                                    // Navigate to the home screen or perform any other action
                                } else {
                                    // If sign in fails, display a message to the user.
                                    edtEmail.setError("Email or password is incorrect");
                                    edtEmail.requestFocus();
                                }
                            }
                        });
            }
        });
    }
}