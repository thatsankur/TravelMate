package com.example.ankursingh.shaeredelementdemo.travelmate.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ankur Singh() on 09/04/16.
 */
public class Ticket implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public Ticket() {
    }

    protected Ticket(Parcel in) {
    }

    public static final Parcelable.Creator<Ticket> CREATOR = new Parcelable.Creator<Ticket>() {
        public Ticket createFromParcel(Parcel source) {
            return new Ticket(source);
        }

        public Ticket[] newArray(int size) {
            return new Ticket[size];
        }
    };
}
