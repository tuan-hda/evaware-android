package com.example.evaware.presentation.auth;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.evaware.R;
import com.example.evaware.databinding.FragmentSignUpBinding;
import com.example.evaware.utils.PasswordUtils;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpFragment extends Fragment {

    private static final String TAG = "LoginFragment";
    ThirdPartiesLogin thirdPartiesLogin;
    CallbackManager callbackManager;
    AuthViewModel viewModel;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    SignInClient client;
    BeginSignInRequest request;
    private FragmentSignUpBinding binding;
    private TextView tvNavToLogin;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private MaterialButton btnSignup;
    private ImageView mEmailIconImageView;
    private UserViewModel userViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(getActivity()).get(AuthViewModel.class);
        View view = binding.getRoot();

        checkNeverLogIn();
        initView(view);
        setUpThirdParties();
        return view;
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

    private void checkNeverLogIn() {
        SharedPreferences pref = getActivity().getPreferences(MODE_PRIVATE);
        boolean neverLogin = pref.getBoolean("neverLogin", true);
        if (!neverLogin) {
            binding.btnBackToStart.setVisibility(View.GONE);
        }
    }

    private void initView(View view) {
        // initialize views
        tvNavToLogin = binding.tvNavToLogin;
        etEmail = binding.etEmail;
        etPassword = binding.etPassword;
        etConfirmPassword = binding.etConfirmPassword;
        btnSignup = binding.btnSignup;
        mEmailIconImageView = binding.emailIconImageview;
        PasswordUtils.setupPasswordVisibilityToggle(etPassword);
        PasswordUtils.setupPasswordVisibilityToggle(etConfirmPassword);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        Drawable checkmark = getResources().getDrawable(R.drawable.ic_checked);
        checkmark.setBounds(0, 0, checkmark.getIntrinsicWidth(), checkmark.getIntrinsicHeight());

        binding.btnBackToStart.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(this).navigateUp();
        });
// Add a TextChangedListener to the EditText to listen for changes in text input
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Check if the email input matches the expected format using a regular expression
                String emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String email = etEmail.getText().toString().trim();
                if (email.matches(emailRegex)) {
                    // If the input matches the expected format, set the checkmark icon as the
                    // end compound drawable
                    etEmail.setCompoundDrawables(null, null, checkmark, null);
                } else {
                    // If the input does not match the expected format, set the warning icon as
                    // the end compound drawable
                    etEmail.setCompoundDrawables(null, null, null, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });


        // set click listener for sign-up button
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get email and password from text fields
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();

                // validate email and password
                if (TextUtils.isEmpty(email)) {
                    etEmail.setError("Email is required");
                    etEmail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etEmail.setError("Invalid email address");
                    etEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    etPassword.setError("Password is required");
                    etPassword.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    etPassword.setError("Password must be at least 6 characters long");
                    etPassword.requestFocus();
                    return;
                }
                if (!confirmPassword.equals(password)) {
                    etConfirmPassword.setError("Passwords do not match");
                    etConfirmPassword.requestFocus();
                    return;
                }

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                user.sendEmailVerification()
                                        .addOnCompleteListener(emailTask -> {
                                            if (emailTask.isSuccessful()) {
                                                String userId = user.getUid();
                                                userViewModel.createUser(userId, email);

                                                Toast.makeText(getActivity(), "Verification email" +
                                                        " sent to " + email, Toast.LENGTH_LONG).show();
                                                NavHostFragment.findNavController(SignUpFragment.this).navigate(R.id.action_sign_up_fragment_to_login_fragment);
                                            } else {
                                                Toast.makeText(getActivity(), "Failed to send " +
                                                        "verification email", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                Toast.makeText(getActivity(),
                                        "Failed to create account: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // set click listener for navigate to login button
        tvNavToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SignUpFragment.this).navigate(R.id.action_sign_up_fragment_to_login_fragment);
            }
        });
    }
}