package com.example.ankursingh.shaeredelementdemo.database;

import android.provider.BaseColumns;

/**
 * Created by Ankur Singh on 17/04/16.
 */
public class TravelMateContract {
    private static final String TEXT_TYPE = " TEXT";
    private static final String BLOB_TYPE = " BLOB";
    private static final String INTEGER_TYPE = " INTEGER ";
    private static final String COMMA_SEP = ",";

    public static abstract class TravelPlan implements BaseColumns {
        public static final String TABLE_NAME = "travel_plan_table";
        public static final String PLAN_NAME = "plan_name";
        public static final String PLAN_DESCRIPTION = "plan_description";
        public static final String _PLACE_ID = "_place_id";
        public static final String SQL_CREATE_TRAVEL_PLAN=
                "CREATE TABLE " + TravelMateContract.TravelPlan.TABLE_NAME + " (" +
                        TravelMateContract.TravelPlan._ID + " INTEGER PRIMARY KEY," +
                        TravelMateContract.TravelPlan.PLAN_NAME + TEXT_TYPE + COMMA_SEP +
                        TravelMateContract.TravelPlan._PLACE_ID + INTEGER_TYPE + " DEFAULT -1 "+ COMMA_SEP +
                        TravelMateContract.TravelPlan.PLAN_DESCRIPTION + TEXT_TYPE +
                        " )";
        public static final String SQL_DELETE_TRAVEL_PLAN =
                "DROP TABLE IF EXISTS " + TravelMateContract.TravelPlan.TABLE_NAME;
    }
    public static abstract class Node implements BaseColumns {
        public static final String TABLE_NAME = "place_table";
        public static final String PLACE_DETAIL = "place_detail";
        public static final String NEXT_HOP = "_id_place";

        public static final String SQL_CREATE_NODE_TABLE=
                "CREATE TABLE " + TravelMateContract.Node.TABLE_NAME + " (" +
                        TravelMateContract.Node._ID + " INTEGER PRIMARY KEY," +
                        TravelMateContract.Node.PLACE_DETAIL + TEXT_TYPE + COMMA_SEP +
                        TravelMateContract.Node.NEXT_HOP + INTEGER_TYPE + " DEFAULT -1 "+
                        " )";
        public static final String SQL_DELETE_NODE_TABLE =
                "DROP TABLE IF EXISTS " + TravelMateContract.Node.TABLE_NAME;
    }
    public static abstract class NearByPlaces implements BaseColumns {
        public static final String TABLE_NAME = "nearby_table";
        public static final String STARTING_PLACE_ID = "_starting_place_id";//a place _id
        public static final String NEARBY_PLACE_ID = "_id_place"; // a place id
        /**
         *  P1 -> P2
         *  P1 -> P3
         *  P3 -> P4
         */

        public static final String SQL_CREATE_NEARBY_ASSOCIATION_TABLE=
                "CREATE TABLE " + TravelMateContract.Node.TABLE_NAME + " (" +
                        TravelMateContract.NearByPlaces._ID + " INTEGER PRIMARY KEY," +
                        TravelMateContract.NearByPlaces.STARTING_PLACE_ID + INTEGER_TYPE + COMMA_SEP +
                        NearByPlaces.NEARBY_PLACE_ID + INTEGER_TYPE + " DEFAULT -1 "+
                        " )";
        public static final String SQL_DELETE_NEARBY_ASSOCIATION_TABLE =
                "DROP TABLE IF EXISTS " + TravelMateContract.NearByPlaces.TABLE_NAME;
    }
}
