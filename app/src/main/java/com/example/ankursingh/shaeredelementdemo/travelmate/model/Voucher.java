package com.example.ankursingh.shaeredelementdemo.travelmate.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ankur Singh() on 09/04/16.
 */
public class Voucher implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public Voucher() {
    }

    protected Voucher(Parcel in) {
    }

    public static final Parcelable.Creator<Voucher> CREATOR = new Parcelable.Creator<Voucher>() {
        public Voucher createFromParcel(Parcel source) {
            return new Voucher(source);
        }

        public Voucher[] newArray(int size) {
            return new Voucher[size];
        }
    };
}
