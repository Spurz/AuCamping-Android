package com.example.student.campingimerir;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocalisationFragment extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;

    public LocalisationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_localisation, container, false);

        setHasOptionsMenu(true);

        //Initialisation de la map
        if(mMapView == null) {
            mMapView = (MapView) v.findViewById(R.id.mapViewLocalisation);
            mMapView.onCreate(savedInstanceState);

            mMapView.onResume(); // needed to get the map to display immediately

            try {
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        //Action effectué en Async
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                googleMap.addMarker(new MarkerOptions()
                        .title("Camping IMERIR")
                        .snippet("Avenue Paul Pascot, BP 90443, 66004 Perpignan, France")
                        .position(new LatLng(
                                42.674502,
                                2.847776
                        ))
                );

                // For dropping a marker at a point on the Map
                LatLng myPosition = new LatLng(42.674502,  2.847776);
                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(myPosition).zoom(15).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        });

        return v;
    }

    //Rendre les items du menu invisible
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.changeSearchList);
        item.setVisible(false);

        MenuItem item2 = menu.findItem(R.id.changeSearchMap);
        item2.setVisible(false);
    }

}
