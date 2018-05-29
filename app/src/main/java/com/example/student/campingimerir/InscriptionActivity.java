package com.example.student.campingimerir;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class InscriptionActivity extends AppCompatActivity {

    String nomMembre;
    String prenomMembre;
    String emailMembre;
    String mdpMembre;

    EditText nomET;
    EditText prenomET;
    EditText emailET;
    EditText mdpET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        //Action du bouton REGISTER
        //Recupération des données des EditText
        final Button buttonRegister = (Button) findViewById(R.id.btnRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                nomET = (EditText)findViewById(R.id.nomET);
                nomMembre = nomET.getText().toString();

                prenomET = (EditText)findViewById(R.id.prenomET);
                prenomMembre = prenomET.getText().toString();

                emailET = (EditText)findViewById(R.id.emailET);
                emailMembre = emailET.getText().toString();

                mdpET = (EditText)findViewById(R.id.mdpET);
                mdpMembre = mdpET.getText().toString();

                checkDataEntered();
            }
        });

        //Action du bouton CONNEXION
        //Demarrage de l'activité INSCRIPTION
        final TextView goConnexion = (TextView) findViewById(R.id.goConnexion);
        goConnexion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(InscriptionActivity.this, ConnexionActivity.class);
                startActivity(intent);

            }
        });

    }

    //Fonction me permettant d'ajouter un membre via une requete POST
    private void addMember () {
        final ProgressDialog loading = ProgressDialog.show(this,"Chargement", "Inscription en cours...",false,false);

        StringRequest postRequest = new StringRequest(Request.Method.POST, getResources().getString(R.string.getMembre),
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        loading.dismiss();
                            Intent intent = new Intent(InscriptionActivity.this, ConnexionActivity.class);
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
                Map<String, String>  params = new HashMap<String, String>();
                params.put("nomMembre", nomMembre);
                params.put("prenomMembre", prenomMembre);
                params.put("mailMembre", emailMembre);
                params.put("mdpMembre", mdpMembre);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }

    //Verification de l'email
    boolean isEmail(EditText text){
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    //Verification EditText vide
    boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    //Verification du mot de passe
    boolean isValid(EditText text) {
        CharSequence mdp = text.getText().toString();
        if (mdp.length() < 8) {
            return false;
        } else {
            char c;
            int count = 1;
            for (int i = 0; i < mdp.length() - 1; i++) {
                c = mdp.charAt(i);
                if (!Character.isLetterOrDigit(c)) {
                    return false;
                } else if (Character.isDigit(c)) {
                    count++;
                    if (count < 2)   {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //Mise en place et appelle des fonctions de verifications de formulaire
    void checkDataEntered(){

        if (isEmpty(prenomET) == false && isEmpty(nomET) == false && isEmail(emailET) == true && isValid(mdpET) == true) {
                addMember();
        }

        if (isEmpty(prenomET)) {
            prenomET.setError("Votre prénom est requis pour vous inscrire !");
        }

        if (isEmpty(nomET)) {
            nomET.setError("Votre nom est requis pour vous inscrire !");
        }

        if (isEmail(emailET) == false) {
            emailET.setError("Veuillez entrer un email valide !");
        }

        if (isValid(mdpET) == false) {
            mdpET.setError("Votre mot de passe doit être composé d'au moins 8 caractères");
        }
    }
}
