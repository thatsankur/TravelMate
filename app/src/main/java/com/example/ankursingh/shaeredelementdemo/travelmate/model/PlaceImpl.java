package com.example.ankursingh.shaeredelementdemo.travelmate.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by Ankur Singh on 16/04/16.
 */
public final class PlaceImpl implements Serializable, Parcelable,Place {

    private String id;

    private List<Integer> placeTypes;

    private String address;

    private Locale locale;

    private String name;

    private LatLng latLng;

    private LatLngBounds viewport;

    private String websiteUri;

    private String phoneNumber;

    private float rating;

    private int priceLevel;

    private String attributions;

    public PlaceImpl(String id, List<Integer> placeTypes,
                     String address, Locale locale,
                     String name, LatLng latLng,
                     LatLngBounds viewport, String websiteUri,
                     String phoneNumber, float rating,
                     int priceLevel, String attributions) {
        this.id = id;
        this.placeTypes = placeTypes;
        this.address = address;
        this.locale = locale;
        this.name = name;
        this.latLng = latLng;
        this.viewport = viewport;
        this.websiteUri = websiteUri;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.priceLevel = priceLevel;
        this.attributions = attributions;
    }

    public String getId() {
        return id;
    }

    public List<Integer> getPlaceTypes() {
        return placeTypes;
    }

    public String getAddress() {
        return address;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getName() {
        return name;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public LatLngBounds getViewport() {
        return viewport;
    }

    public Uri getWebsiteUri() {
        return Uri.parse(websiteUri);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public float getRating() {
        return rating;
    }

    public int getPriceLevel() {
        return priceLevel;
    }

    public String getAttributions() {
        return attributions;
    }

    public static PlaceImpl getMyPlace(Place pPlace){
        return new PlaceImpl(pPlace.getId(),pPlace.getPlaceTypes(),
                pPlace.getAddress()+"",pPlace.getLocale(),
                pPlace.getName()+"",pPlace.getLatLng(),pPlace.getViewport(),
                pPlace.getWebsiteUri().toString(),pPlace.getPhoneNumber()+"",
                pPlace.getRating(),pPlace.getPriceLevel(),pPlace.getAttributions()+"");
    }

    @Override
    public Place freeze() {
        return null;
    }

    @Override
    public boolean isDataValid() {
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeList(this.placeTypes);
        dest.writeString(this.address);
        dest.writeSerializable(this.locale);
        dest.writeString(this.name);
        dest.writeParcelable(this.latLng, 0);
        dest.writeParcelable(this.viewport, 0);
        dest.writeString(this.websiteUri);
        dest.writeString(this.phoneNumber);
        dest.writeFloat(this.rating);
        dest.writeInt(this.priceLevel);
        dest.writeString(this.attributions);
    }

    protected PlaceImpl(Parcel in) {
        this.id = in.readString();
        this.placeTypes = new ArrayList<Integer>();
        in.readList(this.placeTypes, List.class.getClassLoader());
        this.address = in.readString();
        this.locale = (Locale) in.readSerializable();
        this.name = in.readString();
        this.latLng = in.readParcelable(LatLng.class.getClassLoader());
        this.viewport = in.readParcelable(LatLngBounds.class.getClassLoader());
        this.websiteUri = in.readString();
        this.phoneNumber = in.readString();
        this.rating = in.readFloat();
        this.priceLevel = in.readInt();
        this.attributions = in.readString();
    }

    public static final Creator<PlaceImpl> CREATOR = new Creator<PlaceImpl>() {
        public PlaceImpl createFromParcel(Parcel source) {
            return new PlaceImpl(source);
        }

        public PlaceImpl[] newArray(int size) {
            return new PlaceImpl[size];
        }
    };
}