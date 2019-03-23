package com.example.payme.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class InitializeResponse implements Parcelable {

    //Fields
    @SerializedName("status")
    private final boolean status;
    @SerializedName("message")
    private final String message;
    @SerializedName("data")
    private final AccessCodeData data;

    private InitializeResponse(Parcel in) {
        status = in.readByte() != 0;
        message = in.readString();
        data = in.readParcelable(AccessCodeData.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (status ? 1 : 0));
        dest.writeString(message);
        dest.writeParcelable(data, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InitializeResponse> CREATOR = new Creator<InitializeResponse>() {
        @Override
        public InitializeResponse createFromParcel(Parcel in) {
            return new InitializeResponse(in);
        }

        @Override
        public InitializeResponse[] newArray(int size) {
            return new InitializeResponse[size];
        }
    };

    //Getters
    public AccessCodeData getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
