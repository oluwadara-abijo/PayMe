package com.example.payme;

import android.annotation.SuppressLint;
import android.app.Application;

import co.paystack.android.PaystackSdk;

@SuppressLint("Registered")
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Initialize Paystack SDK
        PaystackSdk.initialize(getApplicationContext());
    }
}
