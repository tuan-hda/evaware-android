package com.example.evaware.presentation.auth;

import static com.example.evaware.presentation.auth.LoginHandler.startMainActivity;
import static com.example.evaware.presentation.auth.TestLoginActivity.GOOGLE_LOGIN;
import static com.example.evaware.presentation.auth.TestLoginActivity.REQ_ONE_TAP;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.util.Log;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import com.example.evaware.R;
import com.example.evaware.presentation.base.MainActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;

public class ThirdPartiesLogin {
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final String TWIT_TAG = "loginTwitter";
    Activity activity;
    CallbackManager callbackManager;
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    SignInClient client;
    BeginSignInRequest request;
    AuthViewModel viewModel;

    public ThirdPartiesLogin(Activity activity, CallbackManager callbackManager,
                             GoogleSignInClient gsc, GoogleSignInOptions gso, SignInClient client
            , BeginSignInRequest request, AuthViewModel viewModel) {
        this.callbackManager = callbackManager;
        this.activity = activity;
        this.gsc = gsc;
        this.gso = gso;
        this.client = client;
        this.request = request;
        this.viewModel = viewModel;
    }

    public void loginWithFacebook(LoginButton loginButton) {
        String TAG = "FacebookLogin";
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess: " + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel ");
            }

            @Override
            public void onError(@NonNull FacebookException e) {
                Log.d(TAG, "facebook:onError: " + e);
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        String TAG = "FacebookLogin";
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(activity, task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithCredential:success");
                FirebaseUser user = mAuth.getCurrentUser();
                startMainActivity(activity);
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithCredential:failure", task.getException());
            }
        });
    }



    public void loginWithGoogle() {
        Intent intent = gsc.getSignInIntent();
        activity.startActivityForResult(intent, GOOGLE_LOGIN);
    }

    public void oneTapGoogle(ImageButton button) {
        final String TAG = "oneTapGoogle";

        SignInClient client = Identity.getSignInClient(activity);
        BeginSignInRequest request =
                BeginSignInRequest.builder()
                        .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions
                                .builder()
                                .setSupported(true)
                                .setServerClientId(activity.getString(R.string.web_client_id))
                                .setFilterByAuthorizedAccounts(false)
                                .build())
                        .build();

        button.setOnClickListener(view -> {
            if (!viewModel.isShowOneTap()) {
                loginWithGoogle();
                return;
            }

            client.beginSignIn(request).addOnSuccessListener(activity, result -> {
                try {
                    activity.startIntentSenderForResult(
                            result.getPendingIntent().getIntentSender(), REQ_ONE_TAP,
                            null, 0, 0, 0);
                } catch (IntentSender.SendIntentException e) {
                    Log.e(TAG, "Couldn't start One Tap UI: " + e.getLocalizedMessage());
                    viewModel.setShowOneTap(false);
                }
            }).addOnFailureListener(activity, e -> {
                Log.d(TAG, "oneTapGoogle:failure: " + e.getLocalizedMessage());
                viewModel.setShowOneTap(false);
            });
        });
    }

    public void loginWithTwitter(ImageButton button) {
        OAuthProvider.Builder provider = OAuthProvider.newBuilder("twitter.com");

        button.setOnClickListener(view -> {
            if (checkPendingLogin()) {
                return;
            }

            mAuth.startActivityForSignInWithProvider(activity, provider.build())
                    .addOnSuccessListener(res -> {
                        startMainActivity(activity);
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TWIT_TAG, "loginWithTwitter:buttonClick: " + e.getLocalizedMessage());
                    });
        });
    }

    private boolean checkPendingLogin() {

        Task<AuthResult> task = mAuth.getPendingAuthResult();
        if (task != null) {
            task.addOnSuccessListener(result -> {
                startMainActivity(activity);
            }).addOnFailureListener(e -> {
                Log.e(TWIT_TAG, e.getLocalizedMessage());
            });
            return true;
        }

        return false;
    }
}
