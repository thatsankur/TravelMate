package com.example.ankursingh.shaeredelementdemo.travelmate.model;

import com.google.android.gms.location.places.Place;

import java.io.Serializable;

/**
 * Created by Ankur Singh() on 09/04/16.
 */
public class Node implements Serializable {
    private PlaceImpl mPlace;
    private AdditionalData mAdditionalData;
    private Note mNote;

    public PlaceImpl getPlace() {
        return mPlace;
    }

    public void setPlace(PlaceImpl mPlace) {
        this.mPlace = mPlace;
    }

    public AdditionalData getmAdditionalData() {
        return mAdditionalData;
    }

    public void setmAdditionalData(AdditionalData mAdditionalData) {
        this.mAdditionalData = mAdditionalData;
    }

    public Note getmNote() {
        return mNote;
    }

    public void setmNote(Note mNote) {
        this.mNote = mNote;
    }
}
