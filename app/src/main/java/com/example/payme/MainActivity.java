package com.example.payme;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import co.paystack.android.model.Card;

public class MainActivity extends AppCompatActivity {

    //UI elements
    TextInputEditText card_number_input, expiry_date_input, cvv_input;
    Button payButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind views
        card_number_input = findViewById(R.id.card_number_edit);
        expiry_date_input = findViewById(R.id.expiry_date_edit);
        cvv_input = findViewById(R.id.cvv_input);
        payButton = findViewById(R.id.button_pay);

        //Set click listener on pay button
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateCard();
            }
        });

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
            Snackbar.make(payButton, R.string.valid_card, Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(payButton, R.string.invalid_card, Snackbar.LENGTH_LONG).show();
        }
    }
}
