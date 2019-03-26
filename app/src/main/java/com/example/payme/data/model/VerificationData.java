package com.example.payme.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class VerificationData implements Parcelable {

    @SerializedName("id")
    private final int mId;
    @SerializedName("status")
    private final String mStatus;
    @SerializedName("reference")
    private final String mReference;
    @SerializedName("amount")
    private final int mAmount;
    @SerializedName("gateway_response")
    private final String mGatewayResponse;
    @SerializedName("channel")
    private final String mChannel;
    @SerializedName("currency")
    private final String mCurrency;
    @SerializedName("customer")
    private final CustomerObject mCustomer;
    @SerializedName("transaction_date")
    private final String mTransactionDate;

    //Getters


    public CustomerObject getCustomer() {
        return mCustomer;
    }

    public String getTransactionDate() {
        return mTransactionDate;
    }

    public int getAmount() {
        return mAmount;
    }

    public String getStatus() {
        return mStatus;
    }

    private VerificationData(Parcel in) {
        mId = in.readInt();
        mStatus = in.readString();
        mReference = in.readString();
        mAmount = in.readInt();
        mGatewayResponse = in.readString();
        mChannel = in.readString();
        mCurrency = in.readString();
        mCustomer = in.readParcelable(CustomerObject.class.getClassLoader());
        mTransactionDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mStatus);
        dest.writeString(mReference);
        dest.writeInt(mAmount);
        dest.writeString(mGatewayResponse);
        dest.writeString(mChannel);
        dest.writeString(mCurrency);
        dest.writeParcelable(mCustomer, flags);
        dest.writeString(mTransactionDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VerificationData> CREATOR = new Creator<VerificationData>() {
        @Override
        public VerificationData createFromParcel(Parcel in) {
            return new VerificationData(in);
        }

        @Override
        public VerificationData[] newArray(int size) {
            return new VerificationData[size];
        }
    };
}
