package com.example.payme.data.network;

import com.example.payme.data.model.InitializeRequest;
import com.example.payme.data.model.InitializeResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

interface PaystackInterface {
    @POST("transaction/initialize")
    Call<InitializeResponse> getAccessCode(@Body InitializeRequest body);

}
