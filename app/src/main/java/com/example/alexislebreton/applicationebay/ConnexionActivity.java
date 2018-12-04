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
                Intent compteActivity = new Intent (ConnexionActivity.this, CompteActivity.class);
                compteActivity.putExtra("UTILISATEUR_ID","NEW");
                startActivity(compteActivity);
            }
        });

        final Button connexion_btn_connexion = (Button)findViewById(R.id.connexion_btn_connexion);
        connexion_btn_connexion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO verif utilisateur en bd

                Intent accueilActivity = new Intent (ConnexionActivity.this, AccueilActivity.class);
                //TODO passer param id utilisateur
                startActivity(accueilActivity);
            }
        });

    }





}
