package com.example.ankursingh.shaeredelementdemo.travelmate.model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ankursingh.shaeredelementdemo.R;
import com.example.ankursingh.shaeredelementdemo.util.AppConstants;
import com.example.ankursingh.shaeredelementdemo.util.AppUtils;
import com.example.ankursingh.shaeredelementdemo.util.GsonUtils;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

/**
 * Created by Ankur Singh on 14/04/16.
 */
public class NodeFragment extends Fragment implements View.OnClickListener {
    private Node mNode;
    private NodeFragmentCallbacks mCallbacks;
    private View closeButton,addNearbyPlaces,addNextHop;
    public static NodeFragment getInstance(Node pNode){
        NodeFragment nf = new NodeFragment();
        Bundle b = new Bundle();
        b.putParcelable("node", pNode);
        nf.setArguments(b);
        return nf;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNode = getArguments().getParcelable("node");
        if(getActivity() instanceof NodeFragmentCallbacks){
            mCallbacks = (NodeFragmentCallbacks) getActivity();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.node_fragment_layout,null,false);
        initViews(v);

        return v;
    }

    private void initViews(View v) {
        TextView placeDetailsTextView = (TextView)v.findViewById(R.id.tv_place_details);
        placeDetailsTextView.setText(mNode.getPlace().getAddress());
        closeButton = v.findViewById(R.id.iv_close_button);
        closeButton.setOnClickListener(this);
        addNearbyPlaces = v.findViewById(R.id.iv_add_nearby_places);
        addNearbyPlaces.setOnClickListener(this);
        addNextHop = v.findViewById(R.id.iv_next_hop);
        addNextHop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_button:
                    if(mCallbacks!=null){
                        mCallbacks.onClose();
                    }
                break;
            case R.id.iv_add_nearby_places:
                AppUtils.getInstance().launchPlacePicker(this, AppConstants.PLACE_PICKER_REQUEST);
                break;
            case R.id.iv_next_hop:
                break;
        }
    }

    public interface NodeFragmentCallbacks{
        void onClose();
        void onPlaceAdded(Place pPlace);
        void onPlaceRemoved(Place pPlace);
        void onNextHopAdded(Place pPlace);
        void onNextHopRemoved(Place pPlace);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this.getActivity());
                String toastMsg = String.format("Node: %s", place.getName());
                Toast.makeText(this.getContext(), toastMsg, Toast.LENGTH_LONG).show();
                //addPlaceOnMap(place);
            }
        }
    }
}
