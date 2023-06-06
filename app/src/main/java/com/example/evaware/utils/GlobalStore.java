package com.example.evaware.utils;

import android.app.Application;

import com.example.evaware.data.model.AddressModel;
import com.example.evaware.data.model.UserModel;

import java.util.HashMap;
import java.util.Map;

public class GlobalStore extends Application {
    private static GlobalStore instance;
    private Map<String, Object> data;
    public static AddressModel addressModel;

    private GlobalStore() {
        data = new HashMap<>();
    }

    public static synchronized GlobalStore getInstance() {
        if (instance == null) {
            instance = new GlobalStore();
        }
        return instance;
    }

    public void setData(String key, Object value) {
        data.put(key, value);
    }

    public Object getData(String key) {
        return data.get(key);
    }
}
