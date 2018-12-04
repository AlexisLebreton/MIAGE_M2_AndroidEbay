package com.example.alexislebreton.applicationebay;

import android.content.Context;
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

public class AnnonceActivity extends AppCompatActivity {
    private SharedPreferences myPrefs;
    private SharedPreferences.Editor myPrefsEditor;
    private Gson gson = new Gson();
    private static final String TAG = "TESTGUI";
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annonce);

        // Récupération SharedPreferences
        myPrefs = getSharedPreferences("AndroidEbay", Context.MODE_PRIVATE);
        myPrefsEditor = myPrefs.edit();

        String json = myPrefs.getString("currentUser", "");
        currentUser = gson.fromJson(json, User.class);

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

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
