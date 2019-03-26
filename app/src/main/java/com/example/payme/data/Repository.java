package com.example.payme.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;

import com.example.payme.AppExecutors;
import com.example.payme.data.model.InitializeResponse;
import com.example.payme.data.model.VerificationResponse;
import com.example.payme.data.network.NetworkDataSource;
import com.example.payme.utilities.InjectorUtils;

public class Repository {

    private static final String LOG_TAG = Repository.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static Repository sInstance;

    //Constructor
    private Repository(NetworkDataSource networkDataSource,
                       AppExecutors executors, Context context) {
    }

    //Singleton
    public synchronized static Repository getInstance(
            NetworkDataSource networkDataSource, AppExecutors executors,
            Context context) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new Repository(networkDataSource,
                        executors, context);
                Log.d(LOG_TAG, "Made new repository");
            }
        }
        return sInstance;
    }

    //Network related operations
    public LiveData<InitializeResponse> getAccessCodes(String amount, String email) {
        NetworkDataSource networkDataSource = InjectorUtils.provideNetworkDataSource();
        return networkDataSource.getAccessCodes(amount, email);
    }

    public LiveData<VerificationResponse> verifyTransaction(String reference) {
        NetworkDataSource networkDataSource = InjectorUtils.provideNetworkDataSource();
        return networkDataSource.verifyTransaction(reference);
    }


}
