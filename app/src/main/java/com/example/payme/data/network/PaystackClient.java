package com.example.payme.data.network;

import com.example.payme.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class PaystackClient {
    private static final String BASE_URL = "https://api.paystack.co/";
    private static PaystackInterface client = null;

    static PaystackInterface getClient() {

        final String SECRET_KEY = "Bearer " + BuildConfig.SecretKey;

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(chain -> {
            Request request = chain.request().newBuilder()
                    .addHeader("Authorization", SECRET_KEY)
                    .build();
            return chain.proceed(request);
        });

        if (client == null) {
            client = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL).client(httpClient.build()).build()
                    .create(PaystackInterface.class);

        }
        return client;

    }
}
