package com.example.ankursingh.shaeredelementdemo.base;

import android.app.Application;

import com.example.ankursingh.shaeredelementdemo.util.AppUtils;

/**
 * Created by Ankur Singh on 17/04/16.
 */
public class AppClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.getInstance().setApplicationContext(getApplicationContext());
    }
}
