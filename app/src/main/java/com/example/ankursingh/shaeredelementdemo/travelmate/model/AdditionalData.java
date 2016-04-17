package com.example.ankursingh.shaeredelementdemo.travelmate.model;

import com.google.android.gms.location.places.Place;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ankur Singh() on 09/04/16.
 */
public class AdditionalData implements Serializable{
    private List<PlaceImpl> mPlacesNearbyList;
    private Place nextHop;
    private List<Voucher> ticketsForThisHop;
    private List<Ticket> ticketsForNextHop;

}
