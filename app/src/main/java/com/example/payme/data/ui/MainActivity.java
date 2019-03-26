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

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    //UI elements
    TextInputEditText card_number_input, expiry_date_input, cvv_input;
    Button payButton;
    ProgressBar mLoadingIndicator;

    //View model object
    private MainActivityViewModel mViewModel;

    //Access code and reference from Paystack's initialize transaction response
    private String mAccessCode;
    private String mReference;

    private Card mCard;

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

        //Populate UI
//        noValidation();
//        pinOnly();
//        pinPhoneOtp();
        pinPlusOtp();
//        bankAuthorisation();



    }

    private void noValidation() {
        card_number_input.setText("4084084084084081");
        expiry_date_input.setText("408");
    }

    private void pinPlusOtp() {
        //Use PIN -1234 and OTP - 123456
        card_number_input.setText("5060666666666666666");
        cvv_input.setText("123");
    }

    private void pinOnly() {
        //Use PIN: 1111
        card_number_input.setText("507850785078507812");
        cvv_input.setText("081");
    }

    private void pinPhoneOtp () {
        //PIN: 0000
        //Phone: If less than 10 numeric characters, Transaction will fail.
        //OTP: 123456
        card_number_input.setText("50785078507850784");
        cvv_input.setText("884");
    }

    private void bankAuthorisation () {
        card_number_input.setText("4084080000000409");
        cvv_input.setText("000");
    }

    private void validateCard() {
        //Collect card details
        String cardNumber = card_number_input.getText().toString();
        String expiryDate = expiry_date_input.getText().toString();
        int expiryMonth = Integer.parseInt(expiryDate.substring(0, 2));
        int expiryYear = Integer.parseInt(expiryDate.substring(3, 5));
        String cvv = cvv_input.getText().toString();

        //Validate card
        mCard = new Card(cardNumber, expiryMonth, expiryYear, cvv);
        if (mCard.isValid()) {
            initializeTransaction();
            chargeCard();
        } else {
            Snackbar.make(payButton, R.string.invalid_card, Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Initialize a transaction and return access_code
     */
    private void initializeTransaction() {

        String amount = "2000";
        String email = "oluwadara.abijo@gmail.com";

        if (isNetworkAvailable()) {
            showLoading();
            mViewModel.getAccessCode(amount, email).observe(this, responseData -> {
                if (responseData != null) {
                    showData();
                    mAccessCode = responseData.getData().getAccess_code();
                    mReference = responseData.getData().getReference();
                    Log.d(LOG_TAG, mAccessCode);
                    Log.d(LOG_TAG, mReference);
                } else {
                    Snackbar.make(payButton, "An error occurred", Snackbar.LENGTH_LONG).show();
                }

            });
        } else {
            Snackbar.make(payButton, "No internet connection", Snackbar.LENGTH_LONG).show();

        }
    }

    private void chargeCard() {
        //Create a charge object
        Charge charge = new Charge();

        charge.setCard(mCard);
        charge.setAccessCode(mAccessCode);
        charge.setAmount(2000);
        charge.setEmail("oluwadara.abijo@gmail.com");

        PaystackSdk.chargeCard(this, charge, new Paystack.TransactionCallback() {
            @Override
            public void onSuccess(Transaction transaction) {
                Snackbar.make(payButton, "Transaction successful", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void beforeValidate(Transaction transaction) {
                Snackbar.make(payButton, "Before validate", Snackbar.LENGTH_LONG).show();

            }

            @Override
            public void onError(Throwable error, Transaction transaction) {
                Log.d(LOG_TAG, error.getMessage());
                Snackbar.make(payButton, "Transaction error", Snackbar.LENGTH_LONG).show();

            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = Objects.requireNonNull(connectivityManager)
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private void showData() {
        mLoadingIndicator.setVisibility(View.GONE);
    }

    private void showLoading() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

}
