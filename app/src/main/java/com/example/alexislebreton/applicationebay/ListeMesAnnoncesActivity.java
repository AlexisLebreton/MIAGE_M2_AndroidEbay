package com.example.alexislebreton.applicationebay;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class ListeMesAnnoncesActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_liste_mes_annonces);

        // Récupération SharedPreferences
        myPrefs = getSharedPreferences("AndroidEbay", Context.MODE_PRIVATE);
        myPrefsEditor = myPrefs.edit();

        String json = myPrefs.getString("currentUser", "");
        currentUser = gson.fromJson(json, User.class);

        mProgressView = findViewById(R.id.progressView_myAuctions);

        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        swipeRefreshLayout = findViewById(R.id.swiperefresh_myAuctions);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i(TAG, "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        showMyAuctions(apiService);
                    }
                }
        );

        showProgress(true);
        showMyAuctions(apiService);
    }

    private void showMyAuctions(ApiInterface apiService) {
        Call<ArrayList<Auction>> getAuctionsBySellerUsername = apiService.getAuctionsBySellerUsername(currentUser.getUsername());
        getAuctionsBySellerUsername.enqueue(new Callback<ArrayList<Auction>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ArrayList<Auction>> call, Response<ArrayList<Auction>> response) {
                auctions = response.body();
                if (auctions.size() != 0) {
                    recyclerView = findViewById(R.id.myAuctionsRecyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ListeMesAnnoncesActivity.this));
                    myAuctionAdapter = new MyAuctionAdapter(auctions);
                    recyclerView.setAdapter(myAuctionAdapter);
                    OnDone();
                } else {
                    Toast.makeText(getApplicationContext(), "Pas d'annonces à afficher !", Toast.LENGTH_SHORT).show();
                    showProgress(false);
                    swipeRefreshLayout.setRefreshing(false);
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
        ItemClickSupport.addTo(recyclerView, R.layout.activity_liste_mes_annonces)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Auction auction = myAuctionAdapter.getAuction(position);
                        User bidder = auction.getHighestBid().getBidder();
                        if (auction.getStatus().equals("OUVERTE")) {
                            Toast.makeText(getApplicationContext(), "La plus haute enchère est de " + bidder.getUsername() + " avec " + auction.getHighestBid().getPrice() + "€.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Le gagnat de votre enchère est " + bidder.getFirstname() + " " + bidder.getLastname() + " avec " + auction.getHighestBid().getPrice() + "€. Son adresse est : " + bidder.getAdress() + ". Vous pouvez le contacter à l'adresse suivante : " + bidder.getMail(), Toast.LENGTH_LONG).show();
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
