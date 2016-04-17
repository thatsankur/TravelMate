package com.example.ankursingh.shaeredelementdemo.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ankur Singh on 16/04/16.
 */
public class AppSqliteOpenHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "tm_db";


    private static final String TEXT_TYPE = " TEXT";
    private static final String BLOB_TYPE = " BLOB";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_TRAVEL_PLAN=
            "CREATE TABLE " + TravelMateContract.TravelPlan.TABLE_NAME + " (" +
                    TravelMateContract.TravelPlan._ID + " INTEGER PRIMARY KEY," +
                    TravelMateContract.TravelPlan.PLAN_NAME + TEXT_TYPE + COMMA_SEP +
                    TravelMateContract.TravelPlan._PLACE_ID + TEXT_TYPE + COMMA_SEP +
                    TravelMateContract.TravelPlan.PLAN_DESCRIPTION + TEXT_TYPE +
                    " )";
    private static final String SQL_DELETE_TRAVEL_PLAN =
            "DROP TABLE IF EXISTS " + TravelMateContract.TravelPlan.TABLE_NAME;

    public AppSqliteOpenHelper(Context context, String name,
                               SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public AppSqliteOpenHelper(Context context, String name,
                               SQLiteDatabase.CursorFactory factory,
                               int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public AppSqliteOpenHelper(Context context) {
        this(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TRAVEL_PLAN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TRAVEL_PLAN);
        db.execSQL(SQL_CREATE_TRAVEL_PLAN);
    }
}
