package com.example.student.campingimerir;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReservationReponseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_reponse);

        final Button buttonRevenir = (Button) findViewById(R.id.btnRevenirRecherche);
        buttonRevenir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ReservationReponseActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
