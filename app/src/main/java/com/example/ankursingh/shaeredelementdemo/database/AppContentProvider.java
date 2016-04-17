package com.example.ankursingh.shaeredelementdemo.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.ankursingh.shaeredelementdemo.R;
import com.example.ankursingh.shaeredelementdemo.util.AppUtils;

import java.util.HashMap;

/**
 * Created by Ankur Singh on 16/04/16.
 */
public class AppContentProvider extends ContentProvider {

    static final String PROVIDER_NAME ="com.example.ankursingh.shaeredelementdemo.provider";


    public static final String AUTHORITY_STRING = "content://" + PROVIDER_NAME;
    public static final Uri TRAVEL_PLAN_TABLE_URI = Uri.parse(AUTHORITY_STRING+ "/"+TravelMateContract.TravelPlan.TABLE_NAME);
    public static final Uri PLACE_TABLE_URI = Uri.parse(AUTHORITY_STRING+ "/"+TravelMateContract.Place.TABLE_NAME);
    static final int TRAVEL_PLAN = 1;
    static final int TRAVEL_PLAN_ID = 2;
    static final int PLACE = 3;
    static final int PLACE_ID = 4;
    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, TravelMateContract.TravelPlan.TABLE_NAME, TRAVEL_PLAN);
        uriMatcher.addURI(PROVIDER_NAME, TravelMateContract.TravelPlan.TABLE_NAME +"/#", TRAVEL_PLAN_ID);
        uriMatcher.addURI(PROVIDER_NAME, TravelMateContract.Place.TABLE_NAME, PLACE);
        uriMatcher.addURI(PROVIDER_NAME, TravelMateContract.Place.TABLE_NAME +"/#", PLACE_ID);
    }
    private SQLiteDatabase db;
    @Override
    public boolean onCreate() {
        Context context = getContext();
        AppSqliteOpenHelper dbHelper = new AppSqliteOpenHelper(context);

        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */
        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (uriMatcher.match(uri)) {
            case TRAVEL_PLAN:
                qb.setTables(TravelMateContract.TravelPlan.TABLE_NAME);
                break;
            case TRAVEL_PLAN_ID:
                qb.setTables(TravelMateContract.TravelPlan.TABLE_NAME);
                qb.appendWhere( TravelMateContract.TravelPlan._ID + "=" + uri.getPathSegments().get(1));
                break;
            case PLACE:
                qb.setTables(TravelMateContract.Place.TABLE_NAME);
                break;
            case PLACE_ID:
                qb.setTables(TravelMateContract.Place.TABLE_NAME);
                qb.appendWhere( TravelMateContract.Place._ID + "=" + uri.getPathSegments().get(1));
                break;
        }
        Cursor c = qb.query(db,	projection,	selection, selectionArgs,null, null, sortOrder);

        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri _uri = null;
        long rowID= -1;
        switch (uriMatcher.match(uri)) {
            case TRAVEL_PLAN:
                rowID= db.insert(	TravelMateContract.TravelPlan.TABLE_NAME, "", values);
                if (rowID > 0)
                {
                    _uri = ContentUris.withAppendedId(TRAVEL_PLAN_TABLE_URI, rowID);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
                break;
            case PLACE:
                rowID= db.insert(	TravelMateContract.Place.TABLE_NAME, "", values);
                if (rowID > 0)
                {
                    _uri = ContentUris.withAppendedId(PLACE_TABLE_URI, rowID);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
                break;
        }
        return _uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case TRAVEL_PLAN:
                count = db.delete(TravelMateContract.TravelPlan.TABLE_NAME, selection, selectionArgs);
                break;
            case TRAVEL_PLAN_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete(TravelMateContract.TravelPlan.TABLE_NAME, TravelMateContract.TravelPlan._ID +  " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);

                break;

        }
        if (count > 0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case TRAVEL_PLAN:
                break;
        }
        return 0;
    }
}
