package com.example.evaware.presentation.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.evaware.R;
import com.example.evaware.databinding.FragmentForgotPasswordCodeBinding;
import com.google.android.material.button.MaterialButton;


public class ForgotPasswordCodeFragment extends Fragment {
    private EditText editText1, editText2, editText3, editText4;
    private StringBuilder pinBuilder;
    private MaterialButton btnSendCode;

    private FragmentForgotPasswordCodeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgotPasswordCodeBinding.inflate(inflater, container, false);
        initVie();
        return binding.getRoot();
    }

    private void initVie() {
        editText1 = binding.editText1;
        editText2 = binding.editText2;
        editText3 = binding.editText3;
        editText4 = binding.editText4;

        btnSendCode = binding.btnSendCode;

        pinBuilder = new StringBuilder();

        btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ForgotPasswordCodeFragment.this).navigate(R.id.action_forgot_password_code_fragment_to_reset_password_fragment);
            }
        });
        setPinEntryListeners();
    }

    private void setPinEntryListeners() {
        editText1.addTextChangedListener(new PinTextWatcher(editText1, editText2));
        editText2.addTextChangedListener(new PinTextWatcher(editText2, editText3));
        editText3.addTextChangedListener(new PinTextWatcher(editText3, editText4));
        editText4.addTextChangedListener(new PinTextWatcher(editText4, null));

        editText1.setOnKeyListener(new PinKeyListener(editText1, null));
        editText2.setOnKeyListener(new PinKeyListener(editText2, editText1));
        editText3.setOnKeyListener(new PinKeyListener(editText3, editText2));
        editText4.setOnKeyListener(new PinKeyListener(editText4, editText3));
    }
    private class PinTextWatcher implements TextWatcher {
        private EditText currentEditText;
        private EditText nextEditText;

        private PinTextWatcher(EditText currentEditText, EditText nextEditText) {
            this.currentEditText = currentEditText;
            this.nextEditText = nextEditText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            String text = s.toString().trim();
            if (!text.isEmpty()) {
                pinBuilder.append(text);
                if (nextEditText != null) {
                    nextEditText.requestFocus();
                } else {
                    // All digits entered
                    String pin = pinBuilder.toString();
                    // Perform necessary actions with the pin (e.g., validation)
                }
            } else {
                pinBuilder.setLength(pinBuilder.length() - 1);
                if (currentEditText.getText().length() == 0 && nextEditText != null) {
                    nextEditText.requestFocus();
                }
            }
        }
    }

    private class PinKeyListener implements View.OnKeyListener {
        private EditText currentEditText;
        private EditText previousEditText;

        private PinKeyListener(EditText currentEditText, EditText previousEditText) {
            this.currentEditText = currentEditText;
            this.previousEditText = previousEditText;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (currentEditText.getText().length() == 0 && previousEditText != null) {
                    previousEditText.requestFocus();
                    return true; // Consume the event, preventing further handling
                } else if (currentEditText.getText().length() == 1) {
                    currentEditText.setText(""); // Clear the current digit
                    return true; // Consume the event
                }
            }
            return false;
        }
    }
}