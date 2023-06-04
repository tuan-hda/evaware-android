package com.example.evaware.utils;

import android.app.Application;

import com.example.evaware.data.model.AddressModel;
import com.example.evaware.data.model.UserModel;

public class GlobalStore extends Application {
    public static AddressModel addressModel;
    public static UserModel userModel;
}
