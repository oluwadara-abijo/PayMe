package com.example.payme.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AccessCodeData implements Parcelable {
    //Fields
    @SerializedName("authorization_url")
    private final String authorizationCode;
    @SerializedName("access_code")
    private final String access_code;
    @SerializedName("reference")
    private final String reference;

    private AccessCodeData(Parcel in) {
        authorizationCode = in.readString();
        access_code = in.readString();
        reference = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(authorizationCode);
        dest.writeString(access_code);
        dest.writeString(reference);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AccessCodeData> CREATOR = new Creator<AccessCodeData>() {
        @Override
        public AccessCodeData createFromParcel(Parcel in) {
            return new AccessCodeData(in);
        }

        @Override
        public AccessCodeData[] newArray(int size) {
            return new AccessCodeData[size];
        }
    };

    //Getters
    public String getAccess_code() {
        return access_code;
    }
}
