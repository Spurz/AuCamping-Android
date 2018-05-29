package com.example.student.campingimerir;


import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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

import adapters.ReservListAdapter;
import entities.Reservation;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReservFragment extends Fragment {

    private GridView gridViewReserv;
    private List<Reservation> reservations = new ArrayList<Reservation>();
    private Adapter adapter;
    int id;

    public ReservFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reserv, container, false);

        setHasOptionsMenu(true);

        adapter = new ReservListAdapter(getActivity(), reservations);
        this.gridViewReserv = (GridView) v.findViewById(R.id.gridViewReserv);
        this.gridViewReserv.setAdapter((ListAdapter) adapter);

        loadReserv();

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

    private void loadReserv (){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(getResources().getString(R.string.getReserv),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                            id = (PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt("ID", 0));
                            //now looping through all the elements of the json array
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);

                                int idReserv = jsonObject.getInt("id");
                                String nameReserv = jsonObject.getString("nom_reserv");
                                String imageURLReserv = jsonObject.getString("image_reserv");
                                String descriptionReserv = jsonObject.getString("type_reserv");
                                int idMembre = jsonObject.getInt("id_membre");

                                if (idMembre == id){
                                    reservations.add(new Reservation(idReserv, nameReserv, imageURLReserv, descriptionReserv, idMembre));
                                }

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
