package com.example.evaware.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.example.evaware.R;
import com.example.evaware.databinding.SnackNoInternetBinding;
import com.example.evaware.databinding.SnackRemoveBinding;
import com.example.evaware.databinding.SnackSuccessfulBinding;
import com.example.evaware.presentation.wishlist.SavedItem;
import com.google.android.material.snackbar.Snackbar;

public class SnackBar {

    @SuppressLint("RestrictedApi")
    public static Snackbar showSnackDisable(Activity activity, View finalView, ViewGroup viewGroup) {
        Snackbar snackbar = Snackbar.make(finalView, "",
                Snackbar.LENGTH_INDEFINITE);
        SnackRemoveBinding binding = SnackRemoveBinding.inflate(activity.getLayoutInflater(),
                viewGroup, false);

        RotateAnimation r = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        r.setDuration(2000);
        r.setRepeatCount(Animation.INFINITE);
        binding.imgLoading.startAnimation(r);

        binding.btnCancel.setOnClickListener(view2 -> {
            snackbar.dismiss();
        });

        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) layout.getLayoutParams();
        params.gravity = Gravity.TOP;
        layout.setLayoutParams(params);

        layout.addView(binding.getRoot(), 0);
        snackbar.show();

        return snackbar;
    }

    @SuppressLint("RestrictedApi")
    public static void showSnackNoInternet(Activity activity, View finalView, ViewGroup viewGroup) {
        Snackbar snackbar = Snackbar.make(finalView, "",
                Snackbar.LENGTH_INDEFINITE);
        SnackNoInternetBinding binding = SnackNoInternetBinding.inflate(activity.getLayoutInflater(),
                viewGroup, false);

        binding.btnRetry.setOnClickListener(view2 -> {
            snackbar.dismiss();
        });

        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) layout.getLayoutParams();
        params.gravity = Gravity.TOP;
        layout.setLayoutParams(params);

        layout.addView(binding.getRoot(), 0);
        snackbar.show();
    }

    @SuppressLint("RestrictedApi")
    public static void showSnackSuccessful(Activity activity, View finalView, ViewGroup viewGroup) {
        Snackbar snackbar = Snackbar.make(finalView, "", Snackbar.LENGTH_INDEFINITE);
        SnackSuccessfulBinding binding = SnackSuccessfulBinding.inflate(activity.getLayoutInflater(),
                viewGroup, false);

        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) layout.getLayoutParams();
        params.gravity = Gravity.TOP;
        layout.setLayoutParams(params);

        layout.addView(binding.getRoot(), 0);
        snackbar.show();

        binding.btnGoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    snackbar.dismiss();
//                    Navigation.findNavController(activity, R.id.nav_host_fragment)
//                            .navigate(R.id.wishlistFragment);
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }
        });
        finalView.postDelayed(new Runnable() {
            @Override
            public void run() {
                snackbar.dismiss();
            }
        }, 3000);
    }


}
