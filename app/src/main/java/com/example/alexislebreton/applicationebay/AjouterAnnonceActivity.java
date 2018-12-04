package com.example.alexislebreton.applicationebay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alexislebreton.applicationebay.model.User;
import com.example.alexislebreton.applicationebay.rest.ApiClient;
import com.example.alexislebreton.applicationebay.rest.ApiInterface;
import com.google.gson.Gson;

public class AjouterAnnonceActivity extends AppCompatActivity {
    private SharedPreferences myPrefs;
    private SharedPreferences.Editor myPrefsEditor;
    private Gson gson = new Gson();
    private static final String TAG = "TESTGUI";
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_annonce);

        // Récupération SharedPreferences
        myPrefs = getSharedPreferences("AndroidEbay", Context.MODE_PRIVATE);
        myPrefsEditor = myPrefs.edit();

        String json = myPrefs.getString("currentUser", "");
        currentUser = gson.fromJson(json, User.class);

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

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
