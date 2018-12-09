package com.example.alexislebreton.applicationebay;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alexislebreton.applicationebay.model.Auction;
import com.example.alexislebreton.applicationebay.model.User;
import com.example.alexislebreton.applicationebay.rest.ApiClient;
import com.example.alexislebreton.applicationebay.rest.ApiInterface;
import com.google.gson.Gson;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AjouterAnnonceActivity extends AppCompatActivity {
    private static final String TAG = "TESTGUI";
    private SharedPreferences myPrefs;
    private SharedPreferences.Editor myPrefsEditor;
    private Gson gson = new Gson();
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

        final EditText ajouterannonce_et_nom = (EditText) findViewById(R.id.ajouterannonce_et_nom);
        final EditText ajouterannonce_et_description = (EditText) findViewById(R.id.ajouterannonce_et_description);
        final EditText ajouterannonce_et_prix = (EditText) findViewById(R.id.ajouterannonce_et_prix);

        final Button ajouterannonce_btn_enregistrer = (Button) findViewById(R.id.ajouterannonce_btn_enregistrer);
        ajouterannonce_btn_enregistrer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String nom = ajouterannonce_et_nom.getText().toString();
                String description = ajouterannonce_et_description.getText().toString();
                String prix = ajouterannonce_et_prix.getText().toString();

                final Auction auction = new Auction(currentUser.getUsername(), nom, description, Long.parseLong(prix), new Date().toString(), "");
                Call<String> addNewAuction = apiService.addNewAuction(auction);
                addNewAuction.enqueue(new Callback<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(getApplicationContext(), "Auction ajoutée !", Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        // Log error here since request failed
                        Log.e(TAG, "onFailure Erreur : " + t.toString());
                        Toast.makeText(getApplicationContext(), "Auction pas ajoutée !", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
            }
        });
    }


}
