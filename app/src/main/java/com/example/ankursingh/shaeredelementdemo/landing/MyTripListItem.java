package com.example.ankursingh.shaeredelementdemo.landing;

import android.database.Cursor;

/**
 * Created by Ankur Singh on 17/04/16.
 */
public class MyTripListItem {
    private String name;

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }

    public static MyTripListItem fromCursor(Cursor cursor) {
        //TODO return your MyTripListItem from cursor.
        MyTripListItem mtli = new MyTripListItem();
        mtli.setName("ID: "+cursor.getString(0)+" "+cursor.getString(1));
        return mtli;
    }
}