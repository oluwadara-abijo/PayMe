package com.example.payme.data.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.payme.data.Repository;

public class MainActivityViewModel extends ViewModel {

    private final Repository mRepository;

    MainActivityViewModel(Repository repository) {
        mRepository = repository;
    }

    public LiveData<String> getAccessCode(String amount, String email) {
        return mRepository.getAccessCode(amount, email);
    }
}
