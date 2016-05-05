package com.example.ankursingh.shaeredelementdemo.landing;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.ankursingh.shaeredelementdemo.MapsActivity;
import com.example.ankursingh.shaeredelementdemo.R;
import com.example.ankursingh.shaeredelementdemo.base.AppBaseActivity;
import com.example.ankursingh.shaeredelementdemo.database.AppContentProvider;
import com.example.ankursingh.shaeredelementdemo.database.TravelMateContract;
import com.example.ankursingh.shaeredelementdemo.util.AppConstants;
import com.example.ankursingh.shaeredelementdemo.util.LogUtils;

/**
 * Created by Ankur Singh on 17/04/16.
 */
public class LandingActivity extends AppBaseActivity implements View.OnClickListener,
        LoaderManager.LoaderCallbacks<Cursor>,MyTripListCursorAdapter.MyTripListCursorAdapterCallbacks {
    private final String TAG = LogUtils.getClassName();

    private FloatingActionButton addFav;
    private RecyclerView mMytripRecyclerView;
    private MyTripListCursorAdapter myTripListCursorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_activity);

        addFav = (FloatingActionButton) findViewById(R.id.fab_add_plan);
        addFav.setOnClickListener(this);
        mMytripRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mMytripRecyclerView.setHasFixedSize(true);

        getSupportLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_add_plan:
                MyTripListItem l = new MyTripListItem();
                l.setName("New Trip");
                launchMyTripMap(l);
                break;
        }
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
        if(myTripListCursorAdapter== null) {
            myTripListCursorAdapter = new MyTripListCursorAdapter(this, data,this);
            mMytripRecyclerView.setAdapter(myTripListCursorAdapter);
            mMytripRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }else {
            myTripListCursorAdapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if(myTripListCursorAdapter!=null){
            myTripListCursorAdapter.swapCursor(null);
        }
    }

    @Override
    public void onDelete(int pRowID) {
        LogUtils.info(TAG, "Deleted >> "+getApplicationContext().getContentResolver().
                delete(AppContentProvider.TRAVEL_PLAN_TABLE_URI.buildUpon().appendPath(String.valueOf(pRowID)).build(),
                         null,
                        null)+"");
    }

    @Override
    public void onRowItemClicked(MyTripListItem pMyTripListItem) {
        launchMyTripMap(pMyTripListItem);
    }

    private void launchMyTripMap(MyTripListItem pMyTripListItem) {
        Intent intent = new Intent(LandingActivity.this, MapsActivity.class);
        Bundle b = new Bundle();
        b.putParcelable(AppConstants.TRAVEL_PLAN_KEY,pMyTripListItem);
        intent.putExtras(b);
        startActivity(intent);
    }
}
