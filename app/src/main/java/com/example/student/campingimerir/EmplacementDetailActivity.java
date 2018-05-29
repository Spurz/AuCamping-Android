package com.example.student.campingimerir;

import android.app.ProgressDialog;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import entities.Emplacement;

public class EmplacementDetailActivity extends AppCompatActivity {

    private TextView textViewNomEmpla;
    private TextView textViewDescEmpla;

    String url = "http://10.0.2.2:8888/CampingIMERIR-WS/web/app_dev.php/reserv";

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emplacement_detail);

        //Recupere l'information de l'emplacement
        Intent intent = getIntent();
        Emplacement emplacement = (Emplacement) intent.getSerializableExtra("Emplacement");

        ImageView imageView = (ImageView) findViewById(R.id.imageViewPhoto);
        Picasso.with(getApplicationContext()).load(emplacement.getImageURL()).placeholder(R.mipmap.ic_launcher).into(imageView);

        this.textViewNomEmpla = (TextView) findViewById(R.id.textViewNomEmpla);
        this.textViewNomEmpla.setText(emplacement.getName());

        this.textViewDescEmpla = (TextView) findViewById(R.id.textViewDescEmpla);
        this.textViewDescEmpla.setText(emplacement.getDescription_empla());

        //Activation de ma fonction au clique du bouton RESERVER
        final Button buttonConnexion = (Button) findViewById(R.id.btnReserver);
        buttonConnexion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addToBooking();
            }
        });

    }

    //Ajoute une réservation pour l'utilisateur connecté
    private void addToBooking (){
        final ProgressDialog loading = ProgressDialog.show(this,"Chargement", "Reservation en cours...",false,false);
        StringRequest postRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.getReserv),
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        loading.dismiss();
                        Intent intent = new Intent(EmplacementDetailActivity.this, ReservationReponseActivity.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Intent intent = getIntent();
                Emplacement emplacement = (Emplacement) intent.getSerializableExtra("Emplacement");
                id = String.valueOf(PreferenceManager.getDefaultSharedPreferences(EmplacementDetailActivity.this).getInt("ID", 0));

                Map<String, String>  params = new HashMap<String, String>();
                params.put("nomReserv", emplacement.getName());
                params.put("typeReserv", emplacement.getDescription_empla());
                params.put("imageReserv", emplacement.getImageURL());
                params.put("idMembre", id);

                return params;
            }
        };

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }
}
