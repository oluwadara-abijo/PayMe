package com.example.payme.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.payme.AppExecutors;
import com.example.payme.data.model.InitializeRequest;
import com.example.payme.data.model.InitializeResponse;
import com.example.payme.data.model.VerificationResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkDataSource {

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static final String LOG_TAG = NetworkDataSource.class.getSimpleName();
    private static NetworkDataSource sInstance;

    private final AppExecutors mExecutors;

    //Constructor
    private NetworkDataSource(AppExecutors executors) {
        mExecutors = executors;
    }

    /**
     * Get the singleton for this class
     */
    public static NetworkDataSource getInstance(AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new NetworkDataSource(executors);
                Log.d(LOG_TAG, "Made new network data source");
            }
        }
        return sInstance;
    }

    //Gets the access code from Paystack

    /**
     * Prompts backend to initialize a transaction using Paystack's initialize endpoint,
     * backend returns access_code
     *
     * @param amount Amount in kobo, required to initialize transaction
     * @param email  Customer's email address, required to initialize transaction
     * @return The response from initialize transaction which contains the access code and reference
     */
    public LiveData<InitializeResponse> getAccessCodes(String amount, String email) {

        final MutableLiveData<InitializeResponse> mutableLiveData = new MutableLiveData<>();

        mExecutors.networkIO().execute(() -> {
            PaystackInterface mPaystackInterface = PaystackClient.getClient();

            mPaystackInterface.getAccessCode(new InitializeRequest(amount, email)).enqueue(new Callback<InitializeResponse>() {
                @Override
                public void onResponse(@NonNull Call<InitializeResponse> call, @NonNull Response<InitializeResponse> response) {
                    if (response.body() != null) {
                        mutableLiveData.postValue(response.body());
                    }
                    Log.d(LOG_TAG, String.valueOf(response));
                }

                @Override
                public void onFailure(@NonNull Call<InitializeResponse> call, @NonNull Throwable t) {
                    Log.d(LOG_TAG, "An error occurred while getting access code");
                }
            });

        });
        return mutableLiveData;
    }

    /**
     * Send the reference to backend and verify transaction
     * @param reference The reference which will be used to verify the status of the transaction
     * @return The authorization from Paystack
     */
    public LiveData<VerificationResponse> verifyTransaction(String reference) {

        final MutableLiveData<VerificationResponse> mutableLiveData = new MutableLiveData<>();

        mExecutors.networkIO().execute(() -> {
            PaystackInterface mPaystackInterface = PaystackClient.getClient();

            mPaystackInterface.verifyTransaction(reference).enqueue(new Callback<VerificationResponse>() {
                @Override
                public void onResponse(@NonNull Call<VerificationResponse> call,@NonNull Response<VerificationResponse> response) {
                    if (response.body() != null) {
                        mutableLiveData.postValue(response.body());
                    }
                    Log.d(LOG_TAG, String.valueOf(response));
                }

                @Override
                public void onFailure(@NonNull Call<VerificationResponse> call,@NonNull Throwable t) {
                    Log.d(LOG_TAG, "An error occurred while verifying transaction");
                }
            });

        });
        return mutableLiveData;
    }

}
