package com.example.student.campingimerir;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapters.LocationListAdapter;
import entities.Emplacement;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationFragment extends Fragment {

    private GridView gridViewEmpla;
    private List<Emplacement> emplacements = new ArrayList<Emplacement>();
    private String url = "http://localhost:8888/CampingIMERIR-WS/web/app_dev.php/emplacement";
    private Adapter adapter;

    public ReservationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reservation, container, false);

        adapter = new LocationListAdapter(getActivity(), emplacements);
        this.gridViewEmpla = (GridView) v.findViewById(R.id.gridViewEmplacement);
        this.gridViewEmpla.setAdapter((ListAdapter) adapter);

        loadEmplacement();

        //emplacements.add(new Emplacement(1,"Emplacement 45", "http://recherche.newtonconcept.com/Justylive/vignette/vignette1.png", "blablabla"));

        return v;
    }

    private void loadEmplacement(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://10.0.2.2:8888/CampingIMERIR-WS/web/app_dev.php/emplacement",
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

                            ((ArrayAdapter) adapter).notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("VOLLEY", error.getMessage());
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        //adding the string request to request queue
        requestQueue.add(jsonArrayRequest);

    }
}
