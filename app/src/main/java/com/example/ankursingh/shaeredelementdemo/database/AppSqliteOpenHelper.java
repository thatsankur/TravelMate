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
        db.execSQL(TravelMateContract.TravelPlan.SQL_CREATE_TRAVEL_PLAN);
        db.execSQL(TravelMateContract.Node.SQL_CREATE_NODE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TravelMateContract.TravelPlan.SQL_DELETE_TRAVEL_PLAN);
        db.execSQL(TravelMateContract.TravelPlan.SQL_CREATE_TRAVEL_PLAN);
        db.execSQL(TravelMateContract.Node.SQL_CREATE_NODE_TABLE);
        db.execSQL(TravelMateContract.Node.SQL_DELETE_NODE_TABLE);
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}
