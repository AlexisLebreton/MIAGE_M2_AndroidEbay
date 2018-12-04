package com.example.alexislebreton.applicationebay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AnnonceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annonce);

        final Button annonce_btn_encherir = (Button)findViewById(R.id.annonce_btn_encherir);
        annonce_btn_encherir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final EditText annonce_et_encherir = (EditText) findViewById(R.id.annonce_et_encherir);
                String encherir = annonce_et_encherir.getText().toString();

                String idUtilisateur= getIntent().getStringExtra("UTILISATEUR_ID");
                String idAnnonce= getIntent().getStringExtra("ANNONCE_ID");

                //TODO ajout enchere à la bd

                finish();
            }

        });
    }
}
