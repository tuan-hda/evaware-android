package com.example.evaware.presentation.auth;

import static android.content.Context.MODE_PRIVATE;
import static com.example.evaware.presentation.auth.LoginHandler.startMainActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.evaware.R;
import com.example.evaware.databinding.FragmentLoginBinding;
import com.example.evaware.presentation.base.MainActivity;
import com.example.evaware.utils.PasswordUtils;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    Drawable eyeIcon;
    Drawable eyeSlashIcon;
    ThirdPartiesLogin thirdPartiesLogin;
    CallbackManager callbackManager;
    AuthViewModel viewModel;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    SignInClient client;
    BeginSignInRequest request;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FragmentLoginBinding binding;
    private TextView navToSignup;
    private EditText edtEmail;
    private EditText edtPassword;
    private MaterialButton btnLogin;
    private ImageButton btnBackToPrevious;
    private TextView navToForgetPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eyeIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_eye);
        eyeIcon.setBounds(0, 0, eyeIcon.getIntrinsicWidth(), eyeIcon.getIntrinsicHeight());
        eyeSlashIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_eye_slash);
        eyeSlashIcon.setBounds(0, 0, eyeSlashIcon.getIntrinsicWidth(),
                eyeSlashIcon.getIntrinsicHeight());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(getActivity()).get(AuthViewModel.class);
        View view = binding.getRoot();

        checkNeverLogIn();
        initView(view);
        setUpThirdParties();
        return view;
    }

    private void checkNeverLogIn() {
        SharedPreferences pref = getActivity().getPreferences(MODE_PRIVATE);
        boolean neverLogin = pref.getBoolean("neverLogin", true);
        if (!neverLogin) {
            binding.btnBackToPrevious.setVisibility(View.GONE);
        }
    }

    public void setUpThirdParties() {
        callbackManager = viewModel.getCallbackManager();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(getActivity(), gso);
        client = Identity.getSignInClient(getActivity());
        request =
                BeginSignInRequest.builder()
                        .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions
                                .builder()
                                .setSupported(true)
                                .setServerClientId(getActivity().getString(R.string.web_client_id))
                                .build())
                        .build();

        thirdPartiesLogin = new ThirdPartiesLogin(getActivity(), callbackManager, gsc, gso,
                client, request, viewModel);
        thirdPartiesLogin.loginWithFacebook(binding.thirdParties.loginFacebookReal);
        thirdPartiesLogin.oneTapGoogle(binding.thirdParties.loginGoogle);
        thirdPartiesLogin.loginWithTwitter(binding.thirdParties.loginTwitter);

        binding.thirdParties.loginFacebook.setOnClickListener(view -> {
            binding.thirdParties.loginFacebookReal.performClick();
        });
    }

    private void initView(View view) {

        navToSignup = binding.navToSignup;
        btnBackToPrevious = binding.btnBackToPrevious;
        edtEmail = binding.edtEmail;
        edtPassword = binding.edtPassword;
        btnLogin = binding.btnLogin;
        navToForgetPassword = binding.navToForgetPassword;
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
        navToForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_login_fragment_to_forgot_password_fragment);
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
                                    // Sign in success, update UI with the signed-in user's
                                    // information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Log.d(TAG, "Logged in successfully!");
                                    startMainActivity(getActivity());
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