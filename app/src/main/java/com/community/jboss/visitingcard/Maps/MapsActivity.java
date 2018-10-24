package com.community.jboss.visitingcard.Maps;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.community.jboss.visitingcard.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[][] dataset = new String[][]{
            new String[]{"Michael","michael@gmail.com"},
            new String[]{"John","john@gmail.com"},
            new String[]{"Alex","alex@gmail.com"},
            new String[]{"Ann","ann@gmail.com"},
            new String[]{"James","james@gmail.com"},
            new String[]{"Paul","paul@gmail.com"},
            new String[]{"Steve","steve@gmail.com"},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        View bottomSheet = findViewById(R.id.bottom_sheet);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);

        // TODO: Replace the TextView with a ListView containing list of Visiting cards in that locality using geo-fencing

        // TODO: List item click should result in launching of ViewVisitingCard Acitivity with the info of the tapped Visiting card.

        mRecyclerView = findViewById(R.id.list_item);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ListOfCardsRecyclerAdapter(dataset);
        mRecyclerView.setAdapter(mAdapter);

        //TODO: Create Custom pins for the selected location
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //TODO: Implement geo-fencing(NOT AS A WHOLE) just visual representation .i.e., a circle of an arbitrary radius with the PIN being the centre of it.
        //TODO: Make the circle color as @color/colorAccent
    }


    // TODO: Replace the stating location with user's current location.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
