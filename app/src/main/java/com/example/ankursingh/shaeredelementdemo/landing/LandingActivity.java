package com.example.ankursingh.shaeredelementdemo.landing;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.EditText;

import com.example.ankursingh.shaeredelementdemo.R;
import com.example.ankursingh.shaeredelementdemo.base.AppBaseActivity;
import com.example.ankursingh.shaeredelementdemo.database.AppContentProvider;
import com.example.ankursingh.shaeredelementdemo.database.TravelMateContract;
import com.example.ankursingh.shaeredelementdemo.util.LogUtils;

/**
 * Created by Ankur Singh on 17/04/16.
 */
public class LandingActivity extends AppBaseActivity implements View.OnClickListener,LoaderManager.LoaderCallbacks<Cursor> {
    private final String TAG = LogUtils.getClassName();
    private EditText mTravelPlanNameEditText;
    private FloatingActionButton addFav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_activity);
        mTravelPlanNameEditText = (EditText) findViewById(R.id.et_travel_plan_name);
        addFav = (FloatingActionButton) findViewById(R.id.fab_add_plan);
        addFav.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_add_plan:
                insertNewImageUriInDb(mTravelPlanNameEditText.getText().toString());
                break;
        }
    }


    private void insertNewImageUriInDb(String pPlanName) {
        ContentValues cv = new ContentValues();
        cv.put(TravelMateContract.TravelPlan.PLAN_NAME, pPlanName);
        LogUtils.info(TAG,getApplicationContext().getContentResolver().insert(AppContentProvider.TRAVEL_PLAN_TABLE_URI, cv).toString());
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        LogUtils.info(TAG, "Loader Query for " + id);
        switch (id) {
            default:
                // Returns a new CursorLoader
                return new CursorLoader(
                        getApplicationContext(),   // Parent activity context
                        AppContentProvider.TRAVEL_PLAN_TABLE_URI,        // Table to query
                        null,     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        null             // Default sort order
                );
//            default:
                // An invalid id was passed in
//                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
