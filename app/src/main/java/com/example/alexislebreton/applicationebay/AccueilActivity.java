package com.example.alexislebreton.applicationebay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.alexislebreton.applicationebay.model.Auction;
import com.example.alexislebreton.applicationebay.model.User;
import com.example.alexislebreton.applicationebay.rest.ApiClient;
import com.example.alexislebreton.applicationebay.rest.ApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccueilActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private SharedPreferences myPrefs;
    private SharedPreferences.Editor myPrefsEditor;
    private Gson gson = new Gson();
    private static final String TAG = "TESTGUI";
    private User currentUser;
    private ArrayList<Auction> auctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Récupération SharedPreferences
        myPrefs = getSharedPreferences("AndroidEbay", Context.MODE_PRIVATE);
        myPrefsEditor = myPrefs.edit();

        String json = myPrefs.getString("currentUser", "");
        currentUser = gson.fromJson(json, User.class);

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ArrayList<Auction>> getAllAuctions = apiService.getAllAuctions();
        getAllAuctions.enqueue(new Callback<ArrayList<Auction>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ArrayList<Auction>> call, Response<ArrayList<Auction>> response) {
                auctions = response.body();
                if (auctions != null) {
                    Toast.makeText(getApplicationContext(), "Auctions !", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No Auctions !", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Auction>> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, "onFailure Erreur : " + t.toString());
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.accueil, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_ajouterannonce) {
            Intent ajouterAnnonceActivity = new Intent(AccueilActivity.this, AjouterAnnonceActivity.class);
            startActivity(ajouterAnnonceActivity);
        } else if (id == R.id.nav_mesannonces) {
            Intent listeMesAnnoncesActivity = new Intent(AccueilActivity.this, ListeMesAnnoncesActivity.class);
            startActivity(listeMesAnnoncesActivity);
        } else if (id == R.id.nav_mesencheres) {
            Intent listeMesEncheresActivity = new Intent(AccueilActivity.this, ListeMesEncheresActivity.class);
            startActivity(listeMesEncheresActivity);
        } else if (id == R.id.nav_mesrdv) {
            Intent listeMesRdvActivity = new Intent(AccueilActivity.this, ListeMesRdvActivity.class);
            startActivity(listeMesRdvActivity);
        } else if (id == R.id.nav_modifiermoncompte) {
            Intent compteActivity = new Intent(AccueilActivity.this, CompteActivity.class);
            startActivity(compteActivity);
        } else if (id == R.id.nav_deconnection) {
            currentUser = null;
            String json = gson.toJson(currentUser);
            myPrefsEditor.putString("currentUser", json);
            myPrefsEditor.apply();
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
