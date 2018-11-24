package com.example.alexislebreton.applicationebay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ConnexionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        final Button connexion_btn_sinscrire = (Button)findViewById(R.id.connexion_btn_sinscrire);
        connexion_btn_sinscrire.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent sinscrireActivity = new Intent (ConnexionActivity.this, SinscrireActivity.class);
                startActivity(sinscrireActivity);



            }
        });

        final Button connexion_btn_connexion = (Button)findViewById(R.id.connexion_btn_connexion);
        connexion_btn_connexion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent accueilActivity = new Intent (ConnexionActivity.this, AccueilActivity.class);
                startActivity(accueilActivity);
            }
        });

    }





}
