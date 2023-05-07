package com.example.evaware.presentation.auth;

import static com.example.evaware.presentation.auth.LoginHandler.startMainActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.evaware.R;
import com.example.evaware.databinding.ActivityTestLoginBinding;
import com.example.evaware.presentation.base.MainActivity;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class TestLoginActivity extends AppCompatActivity {
    public static final int GOOGLE_LOGIN = 100;
    public static final int REQ_ONE_TAP = 101;
    private static final String TAG = "TestLoginActivity";
    CallbackManager callbackManager;
    AuthViewModel viewModel;
    SignInClient client;
    FirebaseAuth auth;
    private ActivityTestLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkNeverLogIn();
        checkLoggedIn();
        callbackManager = CallbackManager.Factory.create();
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        viewModel.setCallbackManager(callbackManager);
    }

    private void checkNeverLogIn() {
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        boolean neverLogin = pref.getBoolean("neverLogin", true);
        Log.d(TAG, "checkNeverLogIn: " + neverLogin);
        if (!neverLogin) {
            NavHostFragment navHostFragment =
                    (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
            NavController navController = navHostFragment.getNavController();
            NavOptions options =
                    new NavOptions.Builder().setPopUpTo(R.id.start_fragment, true).build();
            navController.navigate(R.id.action_start_fragment_to_login_fragment, new Bundle(),
                    options);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case GOOGLE_LOGIN: {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    task.getResult(ApiException.class);
                    startMainActivity(this);
                } catch (ApiException e) {
                    Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                }
                break;
            }

            case REQ_ONE_TAP: {
                final String TAG = "reqOneTap";
                try {
                    client = Identity.getSignInClient(this);
                    SignInCredential credential = client.getSignInCredentialFromIntent(data);
                    String idToken = credential.getGoogleIdToken();
                    if (idToken != null) {
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(idToken,
                                null);
                        auth.signInWithCredential(authCredential)
                                .addOnCompleteListener(this, task -> {
                                    if (task.isSuccessful()) {
                                        startMainActivity(this);
                                    } else {
                                        Log.d(TAG, "onActivityResult:isNotSuccessful");
                                    }
                                });
                    }
                } catch (ApiException e) {
                    Log.e(TAG, "onActivityResult:exception: " + e.getLocalizedMessage());
                    if (e.getStatusCode() == CommonStatusCodes.CANCELED) {
                        Log.d(TAG, "User cancelled, temporarily disable one tap");
                        viewModel.setShowOneTap(false);
                    }
                }
                break;
            }

            default:
                break;
        }

    }

    private void checkLoggedIn() {
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startMainActivity(this);
        }
    }

}