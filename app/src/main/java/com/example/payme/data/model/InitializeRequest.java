package com.example.payme.data.model;

public class InitializeRequest {
    private String amount;
    private String email;

    public InitializeRequest(String amount, String email) {
        this.amount = amount;
        this.email = email;
    }
}
