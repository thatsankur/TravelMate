package com.example.ankursingh.shaeredelementdemo.landing;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ankursingh.shaeredelementdemo.R;
import com.example.ankursingh.shaeredelementdemo.base.CursorRecyclerViewAdapter;
import com.example.ankursingh.shaeredelementdemo.util.LogUtils;

/**
 * Created by Ankur Singh on 17/04/16.
 */
public class MyTripListCursorAdapter extends CursorRecyclerViewAdapter<MyTripListCursorAdapter.ViewHolder> {

    private MyTripListCursorAdapterCallbacks mCallback;
    public MyTripListCursorAdapter(Context context, Cursor cursor,MyTripListCursorAdapterCallbacks pCallback){
        super(context, cursor);
        setHasStableIds(true);
        mCallback = pCallback;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ImageView deleteView;
        public View root;
        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView)view.findViewById(R.id.tv_trip_name);
            deleteView = (ImageView) view.findViewById(R.id.iv_delete_trip);
            root = view.findViewById(R.id.rl_travel_plant_root_element);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mytrip_row_item, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final Cursor cursor) {
        final MyTripListItem myListItem = MyTripListItem.fromCursor(cursor);
        viewHolder.mTextView.setText(myListItem.getName());
        viewHolder.root.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mCallback != null) {
                    LogUtils.info("Del ", myListItem.getId() + " ");
                    mCallback.onRowItemClicked(myListItem);
                }
            }
        });
        viewHolder.deleteView.setOnClickListener(new View.OnClickListener() {
            private final int ID = cursor.getInt(0);

            @Override
            public void onClick(View v) {
                if (mCallback != null) {
                    LogUtils.info("Del ", ID + " ");
                    mCallback.onDelete(ID);
                }
            }
        });
    }

    public  interface MyTripListCursorAdapterCallbacks{
        void onDelete(int pRowID);
        void onRowItemClicked(MyTripListItem pMyTripListItem);
    }
}