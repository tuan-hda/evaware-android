package com.example.evaware.data.repo;

public interface OnDataFetchListener<T> {
    void onSuccess(T data);
    void onFailure(Exception e);
}