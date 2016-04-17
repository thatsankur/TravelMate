package com.example.ankursingh.shaeredelementdemo.travelmate.model;

import android.net.Uri;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;


/**
 * Created by Ankur Singh on 16/04/16.
 */
public final class PlaceImpl implements Serializable {

    private String id;

    private List<Integer> placeTypes;

    private String address;

    private Locale locale;

    private String name;

    private LatLng latLng;

    private LatLngBounds viewport;

    private Uri websiteUri;

    private String phoneNumber;

    private float rating;

    private int priceLevel;

    private String attributions;

    public PlaceImpl(String id, List<Integer> placeTypes,
                     String address, Locale locale,
                     String name, LatLng latLng,
                     LatLngBounds viewport, Uri websiteUri,
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
        return websiteUri;
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
                pPlace.getWebsiteUri(),pPlace.getPhoneNumber()+"",
                pPlace.getRating(),pPlace.getPriceLevel(),pPlace.getAttributions()+"");
    }
}