package com.example.alexislebreton.applicationebay;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.alexislebreton.applicationebay.Adapter.MyAuctionAdapter;
import com.example.alexislebreton.applicationebay.Utils.ItemClickSupport;
import com.example.alexislebreton.applicationebay.model.Auction;
import com.example.alexislebreton.applicationebay.model.User;
import com.example.alexislebreton.applicationebay.rest.ApiClient;
import com.example.alexislebreton.applicationebay.rest.ApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccueilActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "TESTGUI";
    private SharedPreferences myPrefs;
    private SharedPreferences.Editor myPrefsEditor;
    private Gson gson = new Gson();
    private User currentUser;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View mProgressView;

    private RecyclerView recyclerView;
    private MyAuctionAdapter myAuctionAdapter;
    private List<Auction> auctions = new ArrayList<>();

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

        mProgressView = findViewById(R.id.progressView_accueil);

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        swipeRefreshLayout = findViewById(R.id.swiperefresh_accueil);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i(TAG, "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        showAllAuctions(apiService);
                    }
                }
        );

        showProgress(true);
        showAllAuctions(apiService);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void showAllAuctions(ApiInterface apiService) {
        Call<ArrayList<Auction>> getAllAuctions = apiService.getAllAuctions();
        getAllAuctions.enqueue(new Callback<ArrayList<Auction>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ArrayList<Auction>> call, Response<ArrayList<Auction>> response) {
                auctions = response.body();
                if (auctions.size() != 0) {
                    Toast.makeText(getApplicationContext(), "Auctions !", Toast.LENGTH_LONG).show();

                    recyclerView = findViewById(R.id.allAuctionsRecyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(AccueilActivity.this));è
                    OnDone();
                } else {
                    Toast.makeText(getApplicationContext(), "No Auctions !", Toast.LENGTH_LONG).show();
                    OnDone();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Auction>> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, "onFailure Erreur : " + t.toString());
                OnDone();
            }
        });
    }

    private void OnDone() {
        showProgress(false);
        swipeRefreshLayout.setRefreshing(false);
        configureOnClickRecyclerView();
    }

    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView, R.layout.activity_accueil)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Auction auction = myAuctionAdapter.getAuction(position);
                        Toast.makeText(getApplicationContext(), "You clicked on auction : " + auction.getItemName(), Toast.LENGTH_LONG).show();
                    }
                });
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
