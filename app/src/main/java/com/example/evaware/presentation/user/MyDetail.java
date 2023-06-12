package com.example.evaware.presentation.user;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.evaware.R;
import com.example.evaware.data.model.UserModel;
import com.example.evaware.databinding.FragmentMyDetailBinding;
import com.example.evaware.utils.LoadingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MyDetail extends Fragment {

    FragmentMyDetailBinding binding;
    FragmentActivity activity;
    UserViewModel viewModel;
    UserModel user;

    final Calendar myCalendar= Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    String myFormat;
    SimpleDateFormat dateFormat;

    LoadingDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = requireActivity();
        viewModel = new ViewModelProvider(activity).get(UserViewModel.class);
        binding = FragmentMyDetailBinding.inflate(inflater, container, false);
        user  = new UserModel();

        myFormat = "dd/MM/yyyy";
        dateFormat = new SimpleDateFormat(myFormat, Locale.getDefault());
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                myCalendar.set(Calendar.YEAR, i);
                myCalendar.set(Calendar.MONTH, i1);
                myCalendar.set(Calendar.DAY_OF_MONTH, i2);
                binding.myDetailEtDob.setText(dateFormat.format(myCalendar.getTime()));
            }
        };

        dialog = new LoadingDialog(getActivity());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpButton();
        getUserInfo();
    }

    private void getUserInfo(){
        viewModel.getUserInfo().observe(getActivity(), userModel -> {
            user.email = userModel.email;
            user.img_url = userModel.img_url;

            Picasso.with(getContext())
                    .load(userModel.img_url)
                    .placeholder(R.drawable.button_round)
                    .into(binding.myDetailIvCamera);

            binding.myDetailEtFullName.setText(userModel.name);
            binding.myDetailEtEmail.setText(userModel.email);

            if(userModel.dob != null){
                binding.myDetailEtDob.setText(dateFormat.format(userModel.dob));
            }

            String phone = userModel.phone;
            if(phone != null){
                binding.myDetailEtPhone.setText(phone);
            }
        });
    }
    private void setUpButton(){
        binding.myDetailIbReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        binding.myDetailEtDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(),
                        date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        binding.myDetailTvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isValidPhoneNumber(binding.myDetailEtPhone.getText().toString())){
                    Toast.makeText(getContext(), "Phone number is not valid", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.showDialog();

                user.name = binding.myDetailEtFullName.getText().toString();
                user.phone = binding.myDetailEtPhone.getText().toString();
                user.dob = myCalendar.getTime();
                viewModel.uploadAndGetUri(user.img_url).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            Uri downloadUrl = task.getResult();
                            if(downloadUrl != null){
                                user.img_url = downloadUrl.toString();
                                viewModel.updateUser(user);

                                dialog.dismissDialog();
                                Toast.makeText(getContext(), "Update successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });

        binding.myDetailBtChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");
            }
        });
    }
    private boolean isValidPhoneNumber(String phoneNumber) {
        String pattern = "^(0[1-9][0-9]{8})$";
        return phoneNumber.matches(pattern);
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    binding.myDetailIvCamera.setImageURI(uri);
                    user.img_url = uri.toString();
                }
            });
}