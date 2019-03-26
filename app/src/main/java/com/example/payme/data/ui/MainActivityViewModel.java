package com.example.payme.data.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.payme.data.Repository;
import com.example.payme.data.model.InitializeResponse;

class MainActivityViewModel extends ViewModel {

    private final Repository mRepository;

    MainActivityViewModel(Repository repository) {
        mRepository = repository;
    }

    LiveData<InitializeResponse> getAccessCode(String amount, String email) {
        return mRepository.getAccessCodes(amount, email);
    }
}
