package com.example.payme.utilities;

import android.content.Context;

import com.example.payme.AppExecutors;
import com.example.payme.data.Repository;
import com.example.payme.data.network.NetworkDataSource;
import com.example.payme.data.ui.MainViewModelFactory;


/**
 * Provides static methods to inject the various classes needed for Sunshine
 */
public class InjectorUtils {

    private static Repository provideRepository(Context context) {
        AppExecutors executors = AppExecutors.getInstance();
        NetworkDataSource networkDataSource =
                NetworkDataSource.getInstance(executors);
        return Repository.getInstance(networkDataSource, executors, context);
    }

    public static NetworkDataSource provideNetworkDataSource() {
        AppExecutors executors = AppExecutors.getInstance();
        return NetworkDataSource.getInstance(executors);
    }

    public static MainViewModelFactory provideMainActivityViewModelFactory(Context context) {
        Repository repository = provideRepository(context.getApplicationContext());
        return new MainViewModelFactory(repository);
    }

}
