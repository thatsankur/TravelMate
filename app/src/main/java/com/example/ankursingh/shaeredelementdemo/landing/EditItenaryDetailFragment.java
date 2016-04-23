package com.example.ankursingh.shaeredelementdemo.landing;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.ankursingh.shaeredelementdemo.R;
import com.example.ankursingh.shaeredelementdemo.base.BaseDialogFragment;
import com.example.ankursingh.shaeredelementdemo.database.AppContentProvider;
import com.example.ankursingh.shaeredelementdemo.database.TravelMateContract;
import com.example.ankursingh.shaeredelementdemo.util.LogUtils;

/**
 * Created by Ankur Singh on 23/04/16.
 */
public class EditItenaryDetailFragment extends BaseDialogFragment implements View.OnClickListener {
    public static final String ITINERARY_ITEM_KEY = "ITINERARY_ITEM_KEY";
    private MyTripListItem myTripListItem;
    private EditText mTravelPlanNameEditText;
    public static EditItenaryDetailFragment getInstance(MyTripListItem pMyTripListItem){
        EditItenaryDetailFragment nf = new EditItenaryDetailFragment();
        Bundle b = new Bundle();
        b.putParcelable(ITINERARY_ITEM_KEY, pMyTripListItem);
        nf.setArguments(b);
        return nf;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myTripListItem = getArguments().getParcelable(ITINERARY_ITEM_KEY);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.edit_itinerary_fragment_layout,null,false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {
        mTravelPlanNameEditText = (EditText) v.findViewById(R.id.et_travel_plan_name);
        mTravelPlanNameEditText.setText(myTripListItem.getName());
        mTravelPlanNameEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (mTravelPlanNameEditText.getRight() -
                            mTravelPlanNameEditText.getCompoundDrawables()[DRAWABLE_RIGHT].
                                    getBounds().width())) {
                        insertNewImageUriInDb(mTravelPlanNameEditText.getText().toString());
                        mTravelPlanNameEditText.setText("");
                        dismiss();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }
    private void insertNewImageUriInDb(String pPlanName) {
        ContentValues cv = new ContentValues();
        cv.put(TravelMateContract.TravelPlan._PLACE_ID,myTripListItem.getPlaceId());
        cv.put(TravelMateContract.TravelPlan.PLAN_NAME, pPlanName);
        if(myTripListItem.getId() == -1) {
            LogUtils.info(TAG, getActivity().getApplicationContext().
                    getContentResolver().insert(AppContentProvider.TRAVEL_PLAN_TABLE_URI, cv).toString());
        }
        else {
            cv.put(TravelMateContract.TravelPlan._ID,myTripListItem.getId());//updating the name
            LogUtils.info(TAG, getActivity().getApplicationContext().getContentResolver().
                    update(AppContentProvider.TRAVEL_PLAN_TABLE_URI, cv,
                            TravelMateContract.TravelPlan._ID + " = ?", new String[]{myTripListItem.getId() + ""}) + "");
        }
    }

}
