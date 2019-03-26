package com.example.payme.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class VerificationResponse implements Parcelable {

    @SerializedName("status")
    private final boolean mStatus;
    @SerializedName("message")
    private final boolean mMessage;
    @SerializedName("data")
    private final VerificationData mData;

    //Getters
    public VerificationData getmData() {
        return mData;
    }

    public boolean getStatus () {
        return mStatus;
    }

    public boolean getMessage () {
        return mMessage;
    }

    private VerificationResponse(Parcel in) {
        mStatus = in.readByte() != 0;
        mMessage = in.readByte() != 0;
        mData = in.readParcelable(VerificationData.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (mStatus ? 1 : 0));
        dest.writeByte((byte) (mMessage ? 1 : 0));
        dest.writeParcelable(mData, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VerificationResponse> CREATOR = new Creator<VerificationResponse>() {
        @Override
        public VerificationResponse createFromParcel(Parcel in) {
            return new VerificationResponse(in);
        }

        @Override
        public VerificationResponse[] newArray(int size) {
            return new VerificationResponse[size];
        }
    };
}
