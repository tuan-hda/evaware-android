package com.example.evaware.presentation.auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.facebook.CallbackManager;

public class AuthViewModel extends AndroidViewModel {
    private CallbackManager callbackManager;
    private boolean showOneTap = true;

    public AuthViewModel(@NonNull Application application) {
        super(application);
    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    public void setCallbackManager(CallbackManager callbackManager) {
        this.callbackManager = callbackManager;
    }

    public boolean isShowOneTap() {
        return showOneTap;
    }

    public void setShowOneTap(boolean showOneTap) {
        this.showOneTap = showOneTap;
    }
}
