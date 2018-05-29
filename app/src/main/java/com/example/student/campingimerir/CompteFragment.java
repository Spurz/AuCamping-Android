package com.example.student.campingimerir;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompteFragment extends Fragment {

    private TextView textViewNom;
    private TextView textViewPrenom;
    private TextView textViewEmail;

    int id;

    public CompteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_compte, container, false);

        setHasOptionsMenu(true);

        //Récupération de l'id de l'utilisateur
        id = PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt("ID", 0);

        this.textViewNom = (TextView) v.findViewById(R.id.textViewNom);
        this.textViewPrenom = (TextView) v.findViewById(R.id.textViewPrenom);
        this.textViewEmail = (TextView) v.findViewById(R.id.textViewEmail);

        getInfoCompte(id);
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

    //Fonction me permettant de récupérer mes informations de compte
    private void getInfoCompte(int id){
        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Chargement", "Connexion...",false,false);

        StringRequest stringRequest = new StringRequest(getResources().getString(R.string.getMembre)+"/"+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                                JSONObject jsonObject = new JSONObject(response);

                                String nom = jsonObject.getString("nom_membre");
                                String prenom = jsonObject.getString("prenom_membre");
                                String email = jsonObject.getString("mail_membre");

                         //Affichage des informations de compte
                            textViewNom.setText(nom);
                            textViewPrenom.setText(prenom);
                            textViewEmail.setText(email);

                            loading.dismiss();

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

        requestQueue.add(stringRequest);
    }
}