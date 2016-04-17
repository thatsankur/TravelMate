package com.example.ankursingh.shaeredelementdemo;

/**
 * Created by Ankur Singh on 09/04/16.
 */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.ankursingh.shaeredelementdemo.travelmate.model.Node;
import com.example.ankursingh.shaeredelementdemo.travelmate.model.NodeFragment;
import com.example.ankursingh.shaeredelementdemo.travelmate.model.PlaceImpl;
import com.example.ankursingh.shaeredelementdemo.util.AppConstants;
import com.example.ankursingh.shaeredelementdemo.util.AppUtils;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.ui.IconGenerator;

import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, PlaceSelectionListener,
        View.OnClickListener, GoogleMap.OnInfoWindowClickListener,NodeFragment.NodeFragmentCallbacks {
    private String TAG = "MapsActivity";
    private GoogleMap mMap;

    private View pickUpALocationOnMap;
    private PolylineOptions polylineOptions;
    private ArrayList<LatLng> arrayPoints = null;
    private final String PLACE_FRAGMENT_KEY = "PLACE_FRAGMENT_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_maps);
        addSelectLocationOnMapClickView();
        addMapFragment();
        addAutoSearchFragment();
        arrayPoints = new ArrayList<>();
        map = new HashMap<>();
    }

    private void addSelectLocationOnMapClickView() {
        pickUpALocationOnMap = findViewById(R.id.tv_select_location_on_map);
        pickUpALocationOnMap.setOnClickListener(this);
    }



    private void addMapFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void addAutoSearchFragment() {
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);
    }
    private final int PERMISSION_REQUEST_CODE = 1;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_REQUEST_CODE);
        }else {
            mMap.setMyLocationEnabled(true);
        }
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onPlaceSelected(Place place) {
        addPlaceOnMap(place);
    }

    @Override
    public void onError(Status status) {
        Log.i(TAG, "An error occurred: " + status);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                addPlaceOnMap(place);
            }
        }

    }

    private void addPlaceOnMap(Place place) {
        if(mMap!=null) {

            LatLng sydney = place.getLatLng();
            map.put(sydney,place);
            String name = place.getName().toString();
            MarkerOptions lMarkerOptions = new MarkerOptions();
            lMarkerOptions.position(sydney);
            lMarkerOptions.title("Marker in " + name != null ? name : place.getLatLng().toString());

            IconGenerator iconFactory = new IconGenerator(this);

            iconFactory.setColor(Color.parseColor("#2dFFDF"));
            lMarkerOptions.icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(arrayPoints.size() + 1 + "")));
            mMap.addMarker(lMarkerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


            polylineOptions = new PolylineOptions();
            polylineOptions.color(Color.RED);
            polylineOptions.width(5);
            arrayPoints.add(sydney);
            polylineOptions.addAll(arrayPoints);
            mMap.addPolyline(polylineOptions);

            Log.d(TAG+"URL ",Util.getInstance().getMapsApiDirectionsUrl(arrayPoints));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_select_location_on_map:{
                AppUtils.getInstance().launchPlacePicker(this, AppConstants.PLACE_PICKER_REQUEST);
            }
                break;
        }
    }

    private HashMap<LatLng,Place> map ;
    @Override
    public void onInfoWindowClick(Marker marker) {
        Place p = map.get(marker.getPosition());
        addPlaceDetailFragment(p);

    }

    private void addPlaceDetailFragment(Place p) {
        Node n = new Node();
        PlaceImpl mp = PlaceImpl.getMyPlace(p);
        n.setPlace(mp);

        NodeFragment nf = NodeFragment.getInstance(n);
        FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.rl_place_detail_fragment, nf, PLACE_FRAGMENT_KEY);
        ft.addToBackStack(PLACE_FRAGMENT_KEY);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:
                int i=0;
                for(String s : permissions){
                    if(Manifest.permission.ACCESS_COARSE_LOCATION.equals(s)){
                        if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                            if(! (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                                    ActivityCompat.checkSelfPermission(this,
                                            Manifest.permission.ACCESS_FINE_LOCATION) !=
                                            PackageManager.PERMISSION_GRANTED &&
                                    ActivityCompat.checkSelfPermission(this,
                                            Manifest.permission.ACCESS_COARSE_LOCATION) !=
                                            PackageManager.PERMISSION_GRANTED))
                            mMap.setMyLocationEnabled(true);
                        }
                    }
                    i++;
                }
                break;
        }
    }

    @Override
    public void onClose() {
        Fragment f = getSupportFragmentManager().findFragmentByTag(PLACE_FRAGMENT_KEY);
        getSupportFragmentManager().beginTransaction().remove(f).commitAllowingStateLoss();
    }

    @Override
    public void onPlaceAdded(Place pPlace) {

    }

    @Override
    public void onPlaceRemoved(Place pPlace) {

    }

    @Override
    public void onNextHopAdded(Place pPlace) {

    }

    @Override
    public void onNextHopRemoved(Place pPlace) {

    }
}

