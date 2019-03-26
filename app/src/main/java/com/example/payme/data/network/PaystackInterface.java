package com.example.payme.data.network;

import com.example.payme.data.model.InitializeRequest;
import com.example.payme.data.model.InitializeResponse;
import com.example.payme.data.model.VerificationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

interface PaystackInterface {
    @POST("transaction/initialize")
    Call<InitializeResponse> getAccessCode(@Body InitializeRequest body);

    @GET("transaction/verify/{reference_code}")
    Call<VerificationResponse> verifyTransaction (@Path("reference_code") String reference_code);

}
