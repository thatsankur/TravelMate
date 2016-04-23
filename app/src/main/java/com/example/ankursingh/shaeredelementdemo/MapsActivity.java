package com.example.ankursingh.shaeredelementdemo;

/**
 * Created by Ankur Singh on 09/04/16.
 */

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.ankursingh.shaeredelementdemo.base.BaseHandler;
import com.example.ankursingh.shaeredelementdemo.database.AppContentProvider;
import com.example.ankursingh.shaeredelementdemo.database.TravelMateContract;
import com.example.ankursingh.shaeredelementdemo.landing.MyTripListItem;
import com.example.ankursingh.shaeredelementdemo.travelmate.model.Node;
import com.example.ankursingh.shaeredelementdemo.travelmate.model.NodeFragment;
import com.example.ankursingh.shaeredelementdemo.travelmate.model.PlaceImpl;
import com.example.ankursingh.shaeredelementdemo.util.AppConstants;
import com.example.ankursingh.shaeredelementdemo.util.AppUtils;
import com.example.ankursingh.shaeredelementdemo.util.GsonUtils;
import com.example.ankursingh.shaeredelementdemo.util.LogUtils;
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
        View.OnClickListener, GoogleMap.OnInfoWindowClickListener,NodeFragment.NodeFragmentCallbacks,BaseHandler.HandlerCallbacks {
    private String TAG = "MapsActivity";
    private GoogleMap mMap;

    private View pickUpALocationOnMap;
    private PolylineOptions polylineOptions;
    private ArrayList<LatLng> arrayPoints = null;
    private final String PLACE_FRAGMENT_KEY = "PLACE_FRAGMENT_KEY";
    private MyTripListItem myTripListItem;
    private View mPickupaPlace;
    private BaseHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_maps);
        addSelectLocationOnMapClickView();
        addMapFragment();
        addAutoSearchFragment();
        mHandler = new BaseHandler(this);

        arrayPoints = new ArrayList<>();
        map = new HashMap<>();

        mPickupaPlace = findViewById(R.id.rl_place_search_holder);
        if(getIntent().getExtras()!=null) {
            myTripListItem = getIntent().getExtras().getParcelable(AppConstants.TRAVEL_PLAN_KEY);
            if(myTripListItem!=null && myTripListItem.getPlaceId()==-1){
                //show dialog to choose a place to start with !
                mPickupaPlace.setVisibility(View.VISIBLE);
            }else{
                Cursor c = getContentResolver().query(AppContentProvider.NODE_TABLE_URI, null,
                        TravelMateContract.Node._ID + " = ?",
                        new String[]{myTripListItem.getPlaceId() + ""}, null);
                c.moveToFirst();
                Node n = GsonUtils.getInstance().deserializeJSON(c.getString(1),Node.class);
                addPlaceOnMap(n.getPlace());
            }
        }

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
       /* PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);*/
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
        addPlaceOnMap(PlaceImpl.getMyPlace(place));
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
                String toastMsg = String.format("Node: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();

                Node n = new Node();
                PlaceImpl mp = PlaceImpl.getMyPlace(place);
                n.setPlace(mp);
                int id = savePlaceInDBAndGetID(n);
                updateItenaryInDBWithPlaceID(id);
                addPlaceOnMap(PlaceImpl.getMyPlace(place));
            }
        }

    }

    private void addPlaceOnMap(final PlaceImpl place) {
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
            Log.d(TAG + "URL ", Util.getInstance().getMapsApiDirectionsUrl(arrayPoints));
        }else {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    addPlaceOnMap(place);
                }
            },1000);
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
        addPlaceDetailFragment(PlaceImpl.getMyPlace(p));

    }

    private void addPlaceDetailFragment(PlaceImpl p) {
        Node n = new Node();
        n.setPlace(p);
        NodeFragment nf = NodeFragment.getInstance(n);
        FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        ft.replace(R.id.rl_place_detail_fragment, nf, PLACE_FRAGMENT_KEY);
        ft.addToBackStack(PLACE_FRAGMENT_KEY);
        ft.commitAllowingStateLoss();

    }

    private void updateItenaryInDBWithPlaceID(int id) {
        ContentValues newCV = new ContentValues();
        //newCV.put(TravelMateContract.TravelPlan._ID, myTripListItem.getId());
        newCV.put(TravelMateContract.TravelPlan.PLAN_NAME, myTripListItem.getName());
        newCV.put(TravelMateContract.TravelPlan._PLACE_ID, id);
        LogUtils.info(TAG, getApplicationContext().getContentResolver().
                update(AppContentProvider.TRAVEL_PLAN_TABLE_URI, newCV,
                        TravelMateContract.TravelPlan._ID + " = ?",new String[]{myTripListItem.getId()+""}) +"");
    }

    private int savePlaceInDBAndGetID(Node n) {
        ContentValues cv = new ContentValues();
        cv.put(TravelMateContract.Node.PLACE_DETAIL, GsonUtils.getInstance().serializeToJson(n));

        return Integer.parseInt(
                getContentResolver().insert(AppContentProvider.NODE_TABLE_URI,cv).
                        getPathSegments().get(1));
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

    @Override
    public void handleMessage(Message pMessage) {

    }
}

