package com.example.alexislebreton.applicationebay;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
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

public class ConnexionActivity extends AppCompatActivity {
    private SharedPreferences myPrefs;
    private SharedPreferences.Editor myPrefsEditor;
    private Gson gson = new Gson();
    private static final String TAG = "TESTGUI";
    private User currentUser;
    private User newUser;
    EditText connexion_et_login;
    EditText connexion_et_motdepasse;
    private View mProgressView;
    private View connexion_cl_login;
    private View connexion_cl_motdepasse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        // Récupération SharedPreferences
        myPrefs = getSharedPreferences("AndroidEbay", Context.MODE_PRIVATE);
        myPrefsEditor = myPrefs.edit();

        String json = myPrefs.getString("currentUser", "");
        currentUser = gson.fromJson(json, User.class);

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        connexion_et_login = findViewById(R.id.connexion_et_login);
        connexion_et_motdepasse = findViewById(R.id.connexion_et_motdepasse);

        mProgressView = findViewById(R.id.progressView_connection);
        connexion_cl_login = findViewById(R.id.connexion_cl_login);
        connexion_cl_motdepasse = findViewById(R.id.connexion_cl_motdepasse);

        final Button connexion_btn_sinscrire = findViewById(R.id.connexion_btn_sinscrire);
        connexion_btn_sinscrire.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent compteActivity = new Intent(ConnexionActivity.this, CompteActivity.class);
                startActivity(compteActivity);
            }
        });

        final Intent accueilActivity = new Intent(ConnexionActivity.this, AccueilActivity.class);

        final Button connexion_btn_connexion = findViewById(R.id.connexion_btn_connexion);
        connexion_btn_connexion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showProgress(true);

                final String login = connexion_et_login.getText().toString();
                final String pwd = connexion_et_motdepasse.getText().toString();

                Call<User> getUserByUserName = apiService.getUserByUserName(login);
                getUserByUserName.enqueue(new Callback<User>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        newUser = response.body();
                        if (userExist(newUser)) {
                            if (checkPwd(newUser, pwd)) {
                                Toast.makeText(getApplicationContext(), "Bienvenue " + newUser.getUsername(), Toast.LENGTH_LONG).show();
                                myPrefsEditor.putString("currentUser", gson.toJson(newUser));
                                myPrefsEditor.apply();
                                startActivity(accueilActivity);
                                showProgress(false);
                            } else {
                                Toast.makeText(getApplicationContext(), "Mot de passe incorrect !", Toast.LENGTH_LONG).show();
                                showProgress(false);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Login inconnu !", Toast.LENGTH_LONG).show();
                            showProgress(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        // Log error here since request failed
                        Log.e(TAG, "onFailure Erreur : " + t.toString());
                        showProgress(false);
                    }
                });
            }
        });

    }

    private boolean checkPwd(User user, String pwdToCheck) {
        return user.getPwd().equals(pwdToCheck);
    }

    private boolean userExist(User user) {
        return user != null;
    }

    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            connexion_cl_login.setVisibility(show ? View.GONE : View.VISIBLE);
            connexion_cl_login.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    connexion_cl_login.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            connexion_cl_motdepasse.setVisibility(show ? View.GONE : View.VISIBLE);
            connexion_cl_motdepasse.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    connexion_cl_motdepasse.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            connexion_cl_login.setVisibility(show ? View.GONE : View.VISIBLE);
            connexion_cl_motdepasse.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


}
