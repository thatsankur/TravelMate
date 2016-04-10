package com.example.ankursingh.shaeredelementdemo;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Ankur Singh on 10/04/16.
 */
public class Util {
    private Util(){

    }
    private static Util mInstance = new Util();

    public static Util getInstance(){
        if(mInstance==null){
            mInstance = new Util();
        }
        return mInstance;
    }

    /**
     *  Method to get URL for geo direction service
     * @param pLatLngs
     * @return
     */
    public String getMapsApiDirectionsUrl(List<LatLng> pLatLngs) {
        StringBuilder sb = new StringBuilder();
        sb.append("https://maps.googleapis.com/maps/api/directions/");
        sb.append("json");
        sb.append("?");
        //sb.append("waypoints=optimize:true|");
        if(pLatLngs!=null && pLatLngs.size()>0){
            int i = 0,size = pLatLngs.size();
            boolean addedWayPoint = false;
            StringBuilder wayPoint = new StringBuilder();
            for(LatLng l : pLatLngs) {
                if(l!=null) {
                    if(i==0){
                        //origin
                        sb.append("origin=");
                        sb.append(l.latitude +
                                "," +
                                l.longitude);
                    }
                    else if(i==size-1){
                        //destination
                        sb.append("&destination=");
                        sb.append(l.latitude +
                                "," +
                                l.longitude);
                    }
                    if(!addedWayPoint) {
                        wayPoint.append("&waypoints=optimize:true|");
                        addedWayPoint = true;
                    }
                    wayPoint.append(l.latitude +
                            "," +
                            l.longitude);
                    if(i<size){
                        wayPoint.append("|");
                    }
                    i++;
                }
            }
            sb.append(wayPoint.toString());
        }

        sb.append("&sensor=false");
       /* String waypoints = "waypoints=optimize:true|"
                + LOWER_MANHATTAN.latitude + "," + LOWER_MANHATTAN.longitude
                + "|" + "|" + BROOKLYN_BRIDGE.latitude + ","
                + BROOKLYN_BRIDGE.longitude + "|" + WALL_STREET.latitude + ","
                + WALL_STREET.longitude;*/

        /*String sensor = "sensor=false";
        String params = waypoints + "&" + sensor;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/"
                + output + "?" + params;*/
        return sb.toString();
    }
}
