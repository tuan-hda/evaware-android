package com.example.evaware.utils;

import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.evaware.R;

public class PasswordUtils {
    public static void setupPasswordVisibilityToggle(EditText editText) {
        Drawable eyeIcon = editText.getResources().getDrawable(R.drawable.ic_eye);
        eyeIcon.setBounds(0, 0, eyeIcon.getIntrinsicWidth(), eyeIcon.getIntrinsicHeight());

        // Set the icon to the right of the EditText
        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, eyeIcon, null);

        // Set the input type to password by default
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        // Add an OnClickListener to the icon to show/hide password
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (editText.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            editText.setCompoundDrawablesWithIntrinsicBounds(null, null, editText.getResources().getDrawable(R.drawable.ic_eye_slash), null);
                        } else {
                            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            editText.setCompoundDrawablesWithIntrinsicBounds(null, null, eyeIcon, null);
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }
}

