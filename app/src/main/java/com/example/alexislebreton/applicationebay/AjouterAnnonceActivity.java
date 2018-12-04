package com.example.alexislebreton.applicationebay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AjouterAnnonceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_annonce);

        final Button ajouterannonce_btn_enregistrer = (Button)findViewById(R.id.ajouterannonce_btn_enregistrer);
        ajouterannonce_btn_enregistrer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final EditText ajouterannonce_et_nom = (EditText) findViewById(R.id.ajouterannonce_et_nom);
                String nom = ajouterannonce_et_nom.getText().toString();
                final EditText ajouterannonce_et_description = (EditText) findViewById(R.id.ajouterannonce_et_description);
                String description = ajouterannonce_et_description.getText().toString();
                final EditText ajouterannonce_et_prix = (EditText) findViewById(R.id.ajouterannonce_et_prix);
                String prix = ajouterannonce_et_prix.getText().toString();

                //TODO ajout annonce à la bd

                finish();
            }
        });
    }


}
