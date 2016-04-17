package com.example.ankursingh.shaeredelementdemo.database;

import android.provider.BaseColumns;

/**
 * Created by Ankur Singh on 17/04/16.
 */
public class TravelMateContract {
    public static abstract class TravelPlan implements BaseColumns {
        public static final String TABLE_NAME = "travel_plan_table";
        public static final String PLAN_NAME = "plan_name";
        public static final String PLAN_DESCRIPTION = "plan_description";
        public static final String _PLACE_ID = "_place_id";
    }
    public static abstract class Place implements BaseColumns {
        public static final String TABLE_NAME = "place_table";
        public static final String PLACE_DETAIL = "place_detail";
        public static final String NEXT_HOP = "_id_place";
    }
}
