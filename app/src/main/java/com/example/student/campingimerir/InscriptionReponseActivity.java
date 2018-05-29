package com.example.student.campingimerir;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InscriptionReponseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_reponse);

        final Button buttonCommencer = (Button) findViewById(R.id.btnCommencer);
        buttonCommencer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(InscriptionReponseActivity.this, ConnexionActivity.class);
                startActivity(intent);
            }
        });
    }
}
