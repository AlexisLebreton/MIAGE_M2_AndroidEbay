package com.example.alexislebreton.applicationebay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AjouterAnnonceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_annonce);

        final Button ajouterannonce_btn_enregistrer = (Button)findViewById(R.id.ajouterannonce_btn_enregistrer);
        ajouterannonce_btn_enregistrer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO ajout annonce à la bd
                finish();
            }
        });
    }


}
