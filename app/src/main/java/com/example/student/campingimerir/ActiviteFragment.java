package com.example.student.campingimerir;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

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

import adapters.ActiviteListAdapter;
import entities.Activite;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActiviteFragment extends Fragment {

    private ListView listViewActivite;
    private List<Activite> activites = new ArrayList<Activite>();
    private Adapter adapter;

    int activeReserv;

    public ActiviteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_activite, container, false);

        setHasOptionsMenu(true);

        //Creation et appel de mon "adapter"
        adapter = new ActiviteListAdapter(getActivity(), activites);
        this.listViewActivite = (ListView) v.findViewById(R.id.listViewActivite);
        this.listViewActivite.setAdapter((ListAdapter) adapter);

        //Appel de ma fonction
        loadActivite();
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

    //Fonction me permettant de charger mes activités
    private void loadActivite(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(getResources().getString(R.string.getActivite),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);

                                int id = jsonObject.getInt("id");
                                String name = jsonObject.getString("nom_activite");
                                String typeActivite = jsonObject.getString("type_activite");
                                String dateActivite = jsonObject.getString("date_activite");
                                activeReserv = jsonObject.getInt("active_reserv");

                                activites.add(new Activite(id, name, typeActivite, dateActivite, activeReserv));
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
