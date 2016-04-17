package com.example.ankursingh.shaeredelementdemo.util;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;

/**
 * Created by Ankur Singh on 16/04/16.
 */
public class AppUtils {

    private Context mAppContext;
    private static AppUtils mInstance;
    private AppUtils(){

    }
    public static AppUtils getInstance(){
        if(mInstance == null){
            mInstance = new AppUtils();
        }
        return mInstance;
    }
    public void launchPlacePicker(Activity mActivity,int pRequestCode) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            mActivity.startActivityForResult(builder.build(mActivity), pRequestCode);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }
    public void launchPlacePicker(Fragment mActivity,int pRequestCode) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            mActivity.startActivityForResult(builder.build(mActivity.getActivity()), pRequestCode);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }
    public Context getApplicationContext(){
        return mAppContext;
    }
    public void setApplicationContext(Context pContext){
        mAppContext  = pContext;
    }


}
