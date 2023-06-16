package com.example.evaware.presentation.payment;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.evaware.R;
import com.example.evaware.data.model.PaymentMethodModel;
import com.example.evaware.databinding.FragmentNewCardBottomSheetBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewCardBottomSheetFragment extends BottomSheetDialogFragment {
    private FragmentNewCardBottomSheetBinding binding;
    private BottomSheetDialog dialog;
    private ImageButton closeBts;

    private PaymentViewModel viewModel;
    private PaymentMethodModel model;

    public static NewCardBottomSheetFragment newInstance(PaymentViewModel viewModel) {
        NewCardBottomSheetFragment fragment = new NewCardBottomSheetFragment();
        fragment.viewModel = viewModel;
        return fragment;
    }

    private static final String TAG = "NewCardBottomSheetFragm";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewCardBottomSheetBinding.inflate(inflater, container, false);
        closeBts = binding.btnClose;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/yy");
        dateFormat.setLenient(false);

        binding.btnSave.setOnClickListener(view -> {
            boolean isError = false;
            if (!isDateFormatValid(binding.edtExpiredDate.getText().toString())) {
                binding.edtExpiredDate.setError("Invalid date format");
                isError = true;
            }
            if (binding.edtCardNumber.getText().toString().length() != 16) {
                binding.edtCardNumber.setError("Invalid card number");
                isError = true;
            }
            if (binding.edtNameOnCard.getText().toString().equals("")) {
                binding.edtNameOnCard.setError("Required");
                isError = true;
            }
            if (binding.edtSecurityCode.getText().toString().length() < 3) {
                binding.edtSecurityCode.setError("Invalid CVC");
                isError = true;
            }

            if (isError) {
                return;
            }

            PaymentMethodModel paymentMethodModel = new PaymentMethodModel(
                    "https://firebasestorage.googleapis.com/v0/b/evaware-893a5.appspot.com/o/payment_providers%2Fmastercard.png?alt=media&token=e27221d4-d12d-4653-9b73-00dd43227c97",
                    binding.edtCardNumber.getText().toString(),
                    binding.edtNameOnCard.getText().toString(),
                    "Mastercard",
                    binding.edtExpiredDate.getText().toString(),
                    true
            );

            if (model != null) {
                model.name = paymentMethodModel.name;
                model.exp = paymentMethodModel.exp;
                model.account_no = paymentMethodModel.account_no;
                viewModel.update(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        dismiss();
                    }
                });
            } else {

                viewModel.add(paymentMethodModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        dismiss();
                    }
                });
            }
        });

        closeBts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        binding.edtCardNumber.setText("");
        binding.edtNameOnCard.setText("");
        binding.edtExpiredDate.setText("");
        binding.edtSecurityCode.setText("");
        setArguments(null);
        model = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle args = getArguments();
        if (args != null) {
            PaymentMethodModel model = (PaymentMethodModel) args.getSerializable("data");
            this.model = model;
            binding.edtSecurityCode.setText("***");
            binding.edtNameOnCard.setText(model.name);
            binding.edtExpiredDate.setText(model.exp);
            binding.edtCardNumber.setText(model.account_no);
            Picasso.with(getActivity()).load(model.logo).resize(64, 64).centerInside()
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                            Drawable cardDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.ic_card);
                            binding.edtCardNumber.setCompoundDrawablesWithIntrinsicBounds(cardDrawable, null, drawable, null);
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
        }
    }

    public static boolean isDateFormatValid(String input) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy");
        dateFormat.setLenient(false);

        try {
            Date date = dateFormat.parse(input);
            Date now = new Date();
            if (date.before(now)) {
                return false;
            }
            Log.e(TAG, "isDateFormatValid: " + date.toString());
            return input.equals(dateFormat.format(date));
        } catch (ParseException e) {
            return false;
        }
    }
}