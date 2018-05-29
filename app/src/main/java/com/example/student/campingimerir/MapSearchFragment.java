package com.example.student.campingimerir;


import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import entities.Emplacement;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapSearchFragment extends Fragment {

    //Variable pour initialiser ma map
    MapView mMapView;
    private GoogleMap googleMap;

    //Liste de mes emplacements
    private List<Emplacement> emplacements = new ArrayList<Emplacement>();

    //Sert à associer mon objet au marqueur
    HashMap<Marker, Emplacement> hashMap = new HashMap<Marker, Emplacement>();


    public MapSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map_search, container, false);



        //Initialisation de ma map
        if(mMapView == null) {
            mMapView = (MapView) v.findViewById(R.id.mapView);
            mMapView.onCreate(savedInstanceState);

            mMapView.onResume(); // needed to get the map to display immediately

            try {
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                loadEmplacement();

                //Initialisation de ma position
                LatLng myPosition = new LatLng(42.674703,  2.848071);
                //Zoom sur ma position
                CameraPosition cameraPosition = new CameraPosition.Builder().target(myPosition).zoom(19).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {

                        Emplacement emplacement = hashMap.get(marker);

                        //Appel de ma fonction POST qui va me permettre de chargé l'évenement choisie grace à son ID
                        Intent intent = new Intent(getActivity(), EmplacementDetailActivity.class);
                        intent.putExtra("Emplacement",emplacement);
                        startActivity(intent);

                    }
                });
            }
        });

        return v;
    }

    //Fonction me permettant de chercher mes emplacements
    private void loadEmplacement(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(getResources().getString(R.string.getEmplacement),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            //now looping through all the elements of the json array
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                int id = jsonObject.getInt("id");
                                String name = jsonObject.getString("nom_empla");
                                String imageURL = jsonObject.getString("image");
                                String description_empla = jsonObject.getString("type_empla");
                                double lat = jsonObject.getDouble("lat");
                                double lng = jsonObject.getDouble("long");

                                emplacements.add(new Emplacement(id, name, imageURL, description_empla, lat, lng));
                            }

                            for (Emplacement emplacement : emplacements){
                                Marker marker = googleMap.addMarker(new MarkerOptions()
                                        .title(emplacement.getName())
                                        .snippet(emplacement.getDescription_empla())
                                        .position(new LatLng(
                                                emplacement.getLat(),
                                                emplacement.getLng()
                                        ))
                                );
                                hashMap.put(marker, emplacement);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        requestQueue.add(jsonArrayRequest);

    }
}
