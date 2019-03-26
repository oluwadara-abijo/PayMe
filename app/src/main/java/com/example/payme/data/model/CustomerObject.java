package com.example.payme.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CustomerObject implements Parcelable {

    @SerializedName("first_name")
    private final String mFirstName;
    @SerializedName("last_name")
    private final String mLastName;
    @SerializedName("email")
    private final String mEmail;
    @SerializedName("customer_code")
    private final String mCustomerCode;
    @SerializedName("phone")
    private final String mPhone;

    private CustomerObject(Parcel in) {
        mFirstName = in.readString();
        mLastName = in.readString();
        mEmail = in.readString();
        mCustomerCode = in.readString();
        mPhone = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mFirstName);
        dest.writeString(mLastName);
        dest.writeString(mEmail);
        dest.writeString(mCustomerCode);
        dest.writeString(mPhone);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CustomerObject> CREATOR = new Creator<CustomerObject>() {
        @Override
        public CustomerObject createFromParcel(Parcel in) {
            return new CustomerObject(in);
        }

        @Override
        public CustomerObject[] newArray(int size) {
            return new CustomerObject[size];
        }
    };
}
