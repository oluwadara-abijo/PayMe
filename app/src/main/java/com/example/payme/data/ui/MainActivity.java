package com.example.payme.data.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.payme.R;
import com.example.payme.utilities.InjectorUtils;

import java.util.Objects;

import co.paystack.android.model.Card;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    //UI elements
    TextInputEditText card_number_input, expiry_date_input, cvv_input;
    Button payButton;
    ProgressBar mLoadingIndicator;

    //View model object
    private MainActivityViewModel mViewModel;

    //Access code
    private String mAccessCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind views
        card_number_input = findViewById(R.id.card_number_edit);
        expiry_date_input = findViewById(R.id.expiry_date_edit);
        cvv_input = findViewById(R.id.cvv_input);
        payButton = findViewById(R.id.button_pay);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        //Set click listener on pay button
        payButton.setOnClickListener(v -> validateCard());

        MainViewModelFactory factory = InjectorUtils.provideMainActivityViewModelFactory(this);
        mViewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel.class);

    }

    private void validateCard() {
        //Collect card details
        String cardNumber = card_number_input.getText().toString();
        String expiryDate = expiry_date_input.getText().toString();
        int expiryMonth = Integer.parseInt(expiryDate.substring(0, 2));
        int expiryYear = Integer.parseInt(expiryDate.substring(3, 5));
        String cvv = cvv_input.getText().toString();

        //Validate card
        Card card = new Card(cardNumber, expiryMonth, expiryYear, cvv);
        if (card.isValid()) {
            mAccessCode = initializeTransaction();
        } else {
            Snackbar.make(payButton, R.string.invalid_card, Snackbar.LENGTH_LONG).show();
        }
    }

    private String initializeTransaction() {

        String amount = "2000";
        String email = "oluwadara.abijo@gmail.com";

        if (isNetworkAvailable()) {
            mViewModel.getAccessCode(amount, email).observe(this, s -> {
                mAccessCode = s;
                Log.d(LOG_TAG, mAccessCode);
            });
        }
        return mAccessCode;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = Objects.requireNonNull(connectivityManager)
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private void showData() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
    }

    private void showLoading() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

}
