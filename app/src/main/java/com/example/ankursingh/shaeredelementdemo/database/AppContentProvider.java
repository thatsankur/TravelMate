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

/**
 * Created by Ankur Singh on 16/04/16.
 */
public class AppContentProvider extends ContentProvider {

    static final String PROVIDER_NAME ="com.example.ankursingh.shaeredelementdemo.provider";


    public static final String AUTHORITY_STRING = "content://" + PROVIDER_NAME;
    public static final Uri TRAVEL_PLAN_TABLE_URI = Uri.parse(AUTHORITY_STRING+ "/"+TravelMateContract.TravelPlan.TABLE_NAME);
    public static final Uri NODE_TABLE_URI = Uri.parse(AUTHORITY_STRING+ "/"+ TravelMateContract.Node.TABLE_NAME);
    static final int TRAVEL_PLAN = 1;
    static final int TRAVEL_PLAN_ID = 2;
    static final int NODE = 3;
    static final int NODE_ID = 4;
    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, TravelMateContract.TravelPlan.TABLE_NAME, TRAVEL_PLAN);
        uriMatcher.addURI(PROVIDER_NAME, TravelMateContract.TravelPlan.TABLE_NAME +"/#", TRAVEL_PLAN_ID);
        uriMatcher.addURI(PROVIDER_NAME, TravelMateContract.Node.TABLE_NAME, NODE);
        uriMatcher.addURI(PROVIDER_NAME, TravelMateContract.Node.TABLE_NAME +"/#", NODE_ID);
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
            case NODE:
                qb.setTables(TravelMateContract.Node.TABLE_NAME);
                break;
            case NODE_ID:
                qb.setTables(TravelMateContract.Node.TABLE_NAME);
                qb.appendWhere( TravelMateContract.Node._ID + "=" + uri.getPathSegments().get(1));
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
                break;
            case NODE:
                rowID= db.insert(	TravelMateContract.Node.TABLE_NAME, "", values);
                break;
        }
        if (rowID > 0)
        {
            _uri = ContentUris.withAppendedId(uri, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        return _uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        String id = null;
        switch (uriMatcher.match(uri)) {
            case TRAVEL_PLAN:
                count = db.delete(TravelMateContract.TravelPlan.TABLE_NAME, selection, selectionArgs);
                break;
            case TRAVEL_PLAN_ID:
                 id = uri.getPathSegments().get(1);
                count = db.delete(TravelMateContract.TravelPlan.TABLE_NAME, TravelMateContract.TravelPlan._ID +  " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case NODE:
                count = db.delete(TravelMateContract.Node.TABLE_NAME, selection, selectionArgs);
                break;
            case NODE_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(TravelMateContract.Node.TABLE_NAME, TravelMateContract.TravelPlan._ID +  " = " + id +
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
        int rows = 0;
        switch (uriMatcher.match(uri)) {
            case TRAVEL_PLAN:
                rows = db.update(TravelMateContract.TravelPlan.TABLE_NAME,values,selection,selectionArgs);
                break;
            case TRAVEL_PLAN_ID:
                rows = db.update(TravelMateContract.TravelPlan.TABLE_NAME,values,selection,selectionArgs);
                break;
            case NODE:
                rows = db.update(TravelMateContract.Node.TABLE_NAME,values,selection,selectionArgs);
                break;
            case NODE_ID:
                rows = db.update(TravelMateContract.Node.TABLE_NAME,values,selection,selectionArgs);
                break;
        }
        if (rows > 0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rows;
    }
}
