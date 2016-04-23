package com.example.ankursingh.shaeredelementdemo.travelmate.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.location.places.Place;

import java.io.Serializable;

/**
 * Created by Ankur Singh() on 09/04/16.
 */
public class Node implements Serializable, Parcelable {
    private int id;
    private PlaceImpl mPlace;
    private AdditionalData mAdditionalData;
    private Note mNote;

    public PlaceImpl getPlace() {
        return mPlace;
    }

    public void setPlace(PlaceImpl mPlace) {
        this.mPlace = mPlace;
    }

    public AdditionalData getAdditionalData() {
        return mAdditionalData;
    }

    public void setAdditionalData(AdditionalData mAdditionalData) {
        this.mAdditionalData = mAdditionalData;
    }

    public Note getNote() {
        return mNote;
    }

    public void setNote(Note mNote) {
        this.mNote = mNote;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mPlace, 0);
        dest.writeParcelable(this.mAdditionalData, 0);
        dest.writeSerializable(this.mNote);
    }

    public Node() {
    }

    protected Node(Parcel in) {
        this.mPlace = in.readParcelable(PlaceImpl.class.getClassLoader());
        this.mAdditionalData = in.readParcelable(AdditionalData.class.getClassLoader());
        this.mNote = (Note) in.readSerializable();
    }

    public static final Parcelable.Creator<Node> CREATOR = new Parcelable.Creator<Node>() {
        public Node createFromParcel(Parcel source) {
            return new Node(source);
        }

        public Node[] newArray(int size) {
            return new Node[size];
        }
    };
}
