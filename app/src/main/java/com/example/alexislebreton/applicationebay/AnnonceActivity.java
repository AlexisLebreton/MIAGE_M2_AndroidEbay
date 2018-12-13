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

import com.example.alexislebreton.applicationebay.model.Auction;
import com.example.alexislebreton.applicationebay.model.Bid;
import com.example.alexislebreton.applicationebay.model.User;
import com.example.alexislebreton.applicationebay.rest.ApiClient;
import com.example.alexislebreton.applicationebay.rest.ApiInterface;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnnonceActivity extends AppCompatActivity {
    private static final String TAG = "TESTGUI";
    private SharedPreferences myPrefs;
    private SharedPreferences.Editor myPrefsEditor;
    private Gson gson = new Gson();
    private User currentUser;
    private View mProgressView;
    private Auction auctionSelected;
    private String idAuctionSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annonce);

        // Récupération SharedPreferences
        myPrefs = getSharedPreferences("AndroidEbay", Context.MODE_PRIVATE);
        myPrefsEditor = myPrefs.edit();

        String json = myPrefs.getString("currentUser", "");
        currentUser = gson.fromJson(json, User.class);

        json = myPrefs.getString("auctionSelected", "");
        auctionSelected = gson.fromJson(json, Auction.class);

        mProgressView = findViewById(R.id.progressView_annonce);

        final EditText annonce_et_nom = findViewById(R.id.annonce_et_nom);
        final EditText annonce_et_description = findViewById(R.id.annonce_et_description);
        final EditText annonce_et_prix = findViewById(R.id.annonce_et_prix);
        final EditText annonce_et_derniere_enchere = findViewById(R.id.annonce_et_derniere_enchere);
        final EditText annonce_et_encherir = findViewById(R.id.annonce_et_encherir);

        annonce_et_nom.setText(auctionSelected.getItemName());
        annonce_et_description.setText(auctionSelected.getItemDescription());
        annonce_et_prix.setText(auctionSelected.getMinPrice().toString());
        annonce_et_derniere_enchere.setText(auctionSelected.getHighestBid().getPrice().toString());

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        final Button annonce_btn_encherir = findViewById(R.id.annonce_btn_encherir);
        annonce_btn_encherir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showProgress(true);

                String encherir = annonce_et_encherir.getText().toString();
                if (auctionSelected.getStatus().equals("OUVERTE")) {
                    if (Long.parseLong(encherir) > auctionSelected.getHighestBid().getPrice()) {
                        final Bid bid = new Bid(auctionSelected.get_id(), currentUser.getUsername(), Long.parseLong(encherir), currentUser, auctionSelected);
                        Call<String> addNewBid = apiService.addNewBid(bid);
                        addNewBid.enqueue(new Callback<String>() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                Toast.makeText(getApplicationContext(), "Enchère ajoutée !", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                // Log error here since request failed
                                Log.e(TAG, "onFailure Erreur : " + t.toString());
                                showProgress(false);
                                finish();
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Montant insuffisant !", Toast.LENGTH_SHORT).show();
                        showProgress(false);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Enchère close !", Toast.LENGTH_SHORT).show();
                    showProgress(false);
                    finish();
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
