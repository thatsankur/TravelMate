package com.example.ankursingh.shaeredelementdemo.landing;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ankur Singh on 17/04/16.
 */
public class MyTripListItem implements Parcelable {
    private String name;
    private int _id;
    private int place_id = -1;

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public int getPlaceId() {
        return place_id;
    }

    public void setPlaceId(int place_id) {
        this.place_id = place_id;
    }

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }

    public static MyTripListItem fromCursor(Cursor cursor) {
        MyTripListItem mtli = new MyTripListItem();
        mtli.setId(cursor.getInt(0));
        mtli.setName(cursor.getString(1));
        mtli.setPlaceId(cursor.getInt(2));
        return mtli;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this._id);
        dest.writeInt(this.place_id);
    }

    public MyTripListItem() {
    }

    protected MyTripListItem(Parcel in) {
        this.name = in.readString();
        this._id = in.readInt();
        this.place_id = in.readInt();
    }

    public static final Parcelable.Creator<MyTripListItem> CREATOR = new Parcelable.Creator<MyTripListItem>() {
        public MyTripListItem createFromParcel(Parcel source) {
            return new MyTripListItem(source);
        }

        public MyTripListItem[] newArray(int size) {
            return new MyTripListItem[size];
        }
    };
}