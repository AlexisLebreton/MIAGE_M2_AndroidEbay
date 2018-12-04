package com.example.alexislebreton.applicationebay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CompteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);

        final Button sinscrire_btn_sinscrire = (Button)findViewById(R.id.compte_btn_enregistrer);
        sinscrire_btn_sinscrire.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final EditText compte_et_mail = (EditText) findViewById(R.id.compte_et_mail);
                String email = compte_et_mail.getText().toString();
                final EditText compte_et_nom = (EditText) findViewById(R.id.compte_et_nom);
                String nom = compte_et_nom.getText().toString();
                final EditText compte_et_prenom = (EditText) findViewById(R.id.compte_et_prenom);
                String prenom = compte_et_prenom.getText().toString();
                final EditText compte_et_login = (EditText) findViewById(R.id.compte_et_login);
                String login = compte_et_login.getText().toString();
                final EditText compte_et_motdepasse = (EditText) findViewById(R.id.compte_et_motdepasse);
                String motdepasse = compte_et_motdepasse.getText().toString();
                final EditText compte_et_adresse = (EditText) findViewById(R.id.compte_et_adresse);
                String adresse = compte_et_adresse.getText().toString();

                String idUtilisateur= getIntent().getStringExtra("UTILISATEUR_ID");

                if (idUtilisateur=="NEW"){
                    //TODO new util POST
                }else{
                    //TODO new util UPDATE
                }
                finish();

            }
        });
    }
}
