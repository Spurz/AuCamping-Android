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
import adapters.ReservListAdapter;
import entities.Emplacement;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListSearchFragment extends Fragment {

    private GridView gridViewEmpla;
    private List<Emplacement> emplacements = new ArrayList<Emplacement>();
    private Adapter adapter;

    public ListSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_search, container, false);

        adapter = new LocationListAdapter(getActivity(), emplacements);
        this.gridViewEmpla = (GridView) v.findViewById(R.id.gridViewEmplacement);
        this.gridViewEmpla.setAdapter((ListAdapter) adapter);

        loadEmplacement();

        //Méthode permettant de gérer le clique sur un événement de ma Gridview
        this.gridViewEmpla.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Trouver l'élément qui a été cliquer
                Emplacement emplacement = emplacements.get(i);
                //Appel de ma fonction POST qui va me permettre de chargé l'évenement choisie grace à son ID
                Intent intent = new Intent(getActivity(), EmplacementDetailActivity.class);
                intent.putExtra("Emplacement",emplacement);
                startActivity(intent);
            }
        });

        return v;
    }

    //Chargement de mes différents emplacements
    private void loadEmplacement(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(getResources().getString(R.string.getEmplacement),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {

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

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        requestQueue.add(jsonArrayRequest);

    }
}
