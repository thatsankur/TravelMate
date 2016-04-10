package com.example.ankursingh.shaeredelementdemo;

/**
 * Created by Ankur Singh on 09/04/16.
 */
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,PlaceSelectionListener,
        View.OnClickListener{
    public String TAG = "MapsActivity";
    private GoogleMap mMap;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    int PLACE_PICKER_REQUEST = 1;
    private View pickUpALocationOnMap;
    PolylineOptions polylineOptions;
    private ArrayList<LatLng> arrayPoints = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        addSelectLocationOnMapClickView();
        addMapFragment();
        addAutoSearchFragment();
        arrayPoints = new ArrayList<>();
    }

    private void addSelectLocationOnMapClickView() {
        pickUpALocationOnMap = findViewById(R.id.tv_select_location_on_map);
        pickUpALocationOnMap.setOnClickListener(this);
    }

    private void launchPlacePicker() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
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
        if (requestCode == PLACE_PICKER_REQUEST) {
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
            String name = place.getName().toString();
            MarkerOptions lMarkerOptions = new MarkerOptions();
            lMarkerOptions.position(sydney);
            lMarkerOptions.title("Marker in " + name != null ? name : place.getLatLng().toString());
//            lMarkerOptions.icon()

            IconGenerator iconFactory = new IconGenerator(this);

            iconFactory.setColor(Color.BLUE);
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
                launchPlacePicker();
            }
                break;
        }
    }

}

