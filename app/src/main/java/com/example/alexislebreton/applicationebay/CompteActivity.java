package com.example.alexislebreton.applicationebay;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alexislebreton.applicationebay.model.User;
import com.example.alexislebreton.applicationebay.rest.ApiClient;
import com.example.alexislebreton.applicationebay.rest.ApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompteActivity extends AppCompatActivity {
    private static final String TAG = CompteActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);

        final Button sinscrire_btn_sinscrire = (Button) findViewById(R.id.compte_btn_enregistrer);
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

                String idUtilisateur = getIntent().getStringExtra("UTILISATEUR_ID");
                Log.d(TAG, "idUtilisateur : " + idUtilisateur);

                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Log.d(TAG, "idUtilisateur.equals(\"NEW\") : " + idUtilisateur.equals("NEW"));

                if (idUtilisateur.equals("NEW")) {
                    User user = new User(login, motdepasse, nom, prenom, email, adresse, "");
                    Log.e(TAG, " User : " + user.toString());
                    Gson gson = new Gson();
                    String json = gson.toJson(user);
                    Log.e(TAG, " userJSON : " + user.toString());
                    Call<User> addNewUser = apiService.addNewUser(user);
                    addNewUser.enqueue(new Callback<User>() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Log.d(TAG, "Response : " + response.body());
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            // Log error here since request failed
                            Log.e(TAG, "onFailure Erreur : " + t.toString());
                        }
                    });
                } else {
                    //TODO new util UPDATE
                }
                finish();

            }
        });
    }
}
