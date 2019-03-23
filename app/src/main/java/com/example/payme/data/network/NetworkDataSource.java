package com.example.payme.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.payme.AppExecutors;
import com.example.payme.data.model.InitializeRequest;
import com.example.payme.data.model.InitializeResponse;

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
    public LiveData<String> getAccessCode(String amount, String email) {

        final MutableLiveData<String> mutableLiveData = new MutableLiveData<>();

        mExecutors.networkIO().execute(() -> {
            PaystackInterface mPaystackInterface = PaystackClient.getClient();

            mPaystackInterface.getAccessCode(new InitializeRequest(amount, email)).enqueue(new Callback<InitializeResponse>() {
                @Override
                public void onResponse(@NonNull Call<InitializeResponse> call, @NonNull Response<InitializeResponse> response) {
                    if (response.body() != null) {
                        mutableLiveData.postValue(response.body().getData().getAccess_code());
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

}
