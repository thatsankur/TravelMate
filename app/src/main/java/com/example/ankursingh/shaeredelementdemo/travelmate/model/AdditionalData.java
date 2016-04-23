package com.example.ankursingh.shaeredelementdemo.travelmate.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.location.places.Place;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankur Singh() on 09/04/16.
 */
public class AdditionalData implements Serializable, Parcelable {
    private List<PlaceImpl> mPlacesNearbyList;
    private PlaceImpl nextHop;
    private List<Voucher> ticketsForThisHop;
    private List<Ticket> ticketsForNextHop;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mPlacesNearbyList);
        dest.writeParcelable(this.nextHop, flags);
        dest.writeList(this.ticketsForThisHop);
        dest.writeList(this.ticketsForNextHop);
    }

    public AdditionalData() {
    }

    protected AdditionalData(Parcel in) {
        this.mPlacesNearbyList = in.createTypedArrayList(PlaceImpl.CREATOR);
        this.nextHop = in.readParcelable(Place.class.getClassLoader());
        this.ticketsForThisHop = new ArrayList<Voucher>();
        in.readList(this.ticketsForThisHop, List.class.getClassLoader());
        this.ticketsForNextHop = new ArrayList<Ticket>();
        in.readList(this.ticketsForNextHop, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<AdditionalData> CREATOR = new Parcelable.Creator<AdditionalData>() {
        public AdditionalData createFromParcel(Parcel source) {
            return new AdditionalData(source);
        }

        public AdditionalData[] newArray(int size) {
            return new AdditionalData[size];
        }
    };
}
