package com.example.student.campingimerir;

import android.app.ProgressDialog;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entities.Emplacement;

public class ConnexionActivity extends AppCompatActivity {

    //Declarations des Edittext email et mdp
    EditText nomET;
    EditText mdpET;

    String emailConnexion;
    String mdpConnexion;
    Boolean VerifConnexion = false;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        //Action du bouton CONNEXION
        //Recupere le contenu des EditText puis appel ma fonction
        final Button buttonConnexion = (Button) findViewById(R.id.btnConnexion);
        buttonConnexion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                nomET = (EditText)findViewById(R.id.emailConnexion);
                emailConnexion = nomET.getText().toString();

                mdpET = (EditText)findViewById(R.id.mdpConnexion);
                mdpConnexion = mdpET.getText().toString();

                isCheckDataEntered();

            }
        });

        //Action du bouton GOINSCRIPITON
        //Ouvre la page de connexion
        final TextView goInscription = (TextView) findViewById(R.id.goInscription);
        goInscription.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(ConnexionActivity.this, InscriptionActivity.class);
                startActivity(intent);

            }
        });
    }

    //Vérifie les identifiants de l'utilisateur
    private void ConnexionVerify (){
        final ProgressDialog loading = ProgressDialog.show(this,"Chargement", "Connexion...",false,false);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(getResources().getString(R.string.getMembre),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            //now looping through all the elements of the json array
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);


                                String email = jsonObject.getString("mail_membre");
                                String mdp = jsonObject.getString("mdp_membre");

                                if (emailConnexion.equals(email) && mdpConnexion.equals(mdp)) {
                                    VerifConnexion = true;

                                    id = jsonObject.getInt("id");
                                }

                            }

                            PreferenceManager.getDefaultSharedPreferences(ConnexionActivity.this).edit().putInt("ID", id).apply();

                            if (VerifConnexion == true) {
                                Intent intent = new Intent(ConnexionActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                CharSequence text = "Erreur d'identification";
                                int duration = Toast.LENGTH_SHORT;

                                Toast toast = Toast.makeText(ConnexionActivity.this, text, duration);
                                toast.show();
                            }

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

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);

    }

    //Verification de l'email
    boolean isEmail(EditText text){
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
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
    void isCheckDataEntered(){

        if (isEmail(nomET) == false) {
            nomET.setError("Veuillez entrer un email valide !");
        }

        if (isValid(mdpET) == false) {
            mdpET.setError("Votre mot de passe doit être composé d'au moins 8 caractères");
        }

        if (isEmail(nomET) && isValid(mdpET) == true) {
            ConnexionVerify();
        }
    }
}
