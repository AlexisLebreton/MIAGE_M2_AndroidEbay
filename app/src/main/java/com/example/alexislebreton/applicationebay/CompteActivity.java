package com.example.alexislebreton.applicationebay;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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

import com.example.alexislebreton.applicationebay.model.User;
import com.example.alexislebreton.applicationebay.rest.ApiClient;
import com.example.alexislebreton.applicationebay.rest.ApiInterface;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompteActivity extends AppCompatActivity {
    private static final String TAG = "TESTGUI";
    private SharedPreferences myPrefs;
    private SharedPreferences.Editor myPrefsEditor;
    private Gson gson = new Gson();
    private User currentUser;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);

        // Récupération SharedPreferences
        myPrefs = getSharedPreferences("AndroidEbay", Context.MODE_PRIVATE);
        myPrefsEditor = myPrefs.edit();

        String json = myPrefs.getString("currentUser", null);
        currentUser = gson.fromJson(json, User.class);

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        final EditText compte_et_mail = findViewById(R.id.compte_et_mail);
        final EditText compte_et_nom = findViewById(R.id.compte_et_nom);
        final EditText compte_et_prenom = findViewById(R.id.compte_et_prenom);
        final EditText compte_et_login = findViewById(R.id.compte_et_login);
        final EditText compte_et_motdepasse = findViewById(R.id.compte_et_motdepasse);
        final EditText compte_et_adresse = findViewById(R.id.compte_et_adresse);

        mProgressView = findViewById(R.id.progressView_compte);

        if (currentUser != null) {
            compte_et_login.setEnabled(false);

            compte_et_mail.setText(currentUser.getMail());
            compte_et_nom.setText(currentUser.getLastname());
            compte_et_prenom.setText(currentUser.getFirstname());
            compte_et_motdepasse.setText(currentUser.getPwd());
            compte_et_adresse.setText(currentUser.getAdress());
        }

        final Button sinscrire_btn_sinscrire = findViewById(R.id.compte_btn_enregistrer);
        sinscrire_btn_sinscrire.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showProgress(true);

                String email = compte_et_mail.getText().toString();
                String nom = compte_et_nom.getText().toString();
                String prenom = compte_et_prenom.getText().toString();
                String login = compte_et_login.getText().toString();
                String motdepasse = compte_et_motdepasse.getText().toString();
                String adresse = compte_et_adresse.getText().toString();

                if (currentUser == null) {
                    final User user = new User(login, motdepasse, nom, prenom, email, adresse, "");
                    Call<String> addNewUser = apiService.addNewUser(user);
                    addNewUser.enqueue(new Callback<String>() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Toast.makeText(getApplicationContext(), "Inscription réussie !\n Bienvenue " + user.getUsername(), Toast.LENGTH_LONG).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            // Log error here since request failed
                            Log.e(TAG, "onFailure Erreur : " + t.toString());
                            Toast.makeText(getApplicationContext(), "Inscription ratée !", Toast.LENGTH_LONG).show();
                            showProgress(false);
                            finish();
                        }
                    });
                } else {
                    final User updatedUser = new User(login, motdepasse, nom, prenom, email, adresse, "");
                    Call<String> updateUserByUsername = apiService.updateUserByUsername(updatedUser.getUsername(), updatedUser);
                    updateUserByUsername.enqueue(new Callback<String>() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (!response.body().equals("0")) {
                                Toast.makeText(getApplicationContext(), "Modification(s) réussie !", Toast.LENGTH_LONG).show();
                                updatedUser.set_id(currentUser.get_id());
                                myPrefsEditor.putString("currentUser", gson.toJson(updatedUser));
                                myPrefsEditor.apply();
                                showProgress(false);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Modification(s) ratée !", Toast.LENGTH_LONG).show();
                                showProgress(false);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            // Log error here since request failed
                            Log.e(TAG, "onFailure Erreur : " + t.toString());
                            showProgress(false);
                            finish();
                        }
                    });
                }

            }
        });
    }

    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}
