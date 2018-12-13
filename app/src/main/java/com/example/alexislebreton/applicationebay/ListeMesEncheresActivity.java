package com.example.alexislebreton.applicationebay;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
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

import com.example.alexislebreton.applicationebay.Adapter.MyBidAdapter;
import com.example.alexislebreton.applicationebay.Utils.ItemClickSupport;
import com.example.alexislebreton.applicationebay.model.Auction;
import com.example.alexislebreton.applicationebay.model.Bid;
import com.example.alexislebreton.applicationebay.model.User;
import com.example.alexislebreton.applicationebay.rest.ApiClient;
import com.example.alexislebreton.applicationebay.rest.ApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListeMesEncheresActivity extends AppCompatActivity {
    private static final String TAG = "TESTGUI";
    final ApiInterface apiService =
            ApiClient.getClient().create(ApiInterface.class);
    private SharedPreferences myPrefs;
    private SharedPreferences.Editor myPrefsEditor;
    private Gson gson = new Gson();
    private User currentUser;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View mProgressView;
    private RecyclerView recyclerView;
    private MyBidAdapter myBidAdapter;
    private List<Bid> bids = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_mes_encheres);

        // Récupération SharedPreferences
        myPrefs = getSharedPreferences("AndroidEbay", Context.MODE_PRIVATE);
        myPrefsEditor = myPrefs.edit();

        String json = myPrefs.getString("currentUser", "");
        currentUser = gson.fromJson(json, User.class);

        mProgressView = findViewById(R.id.progressView_myBids);

        swipeRefreshLayout = findViewById(R.id.swiperefresh_myBids);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i(TAG, "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        showMyBids();
                    }
                }
        );

        showProgress(true);
        showMyBids();
    }

    private void showMyBids() {
        Call<ArrayList<Bid>> getBidsByBidderUsername = apiService.getBidsByBidderUsername(currentUser.getUsername());
        getBidsByBidderUsername.enqueue(new Callback<ArrayList<Bid>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ArrayList<Bid>> call, Response<ArrayList<Bid>> response) {
                bids = response.body();
                if (bids.size() != 0) {
                    recyclerView = findViewById(R.id.myBidsRecyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ListeMesEncheresActivity.this));
                    myBidAdapter = new MyBidAdapter(bids);
                    recyclerView.setAdapter(myBidAdapter);
                    OnDone();
                } else {
                    Toast.makeText(getApplicationContext(), "No Bids !", Toast.LENGTH_SHORT).show();
                    showProgress(false);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Bid>> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, "onFailure Erreur : " + t.toString());
                OnDone();
            }
        });
    }

    private void getAuction(Bid bid) {
        Call<Auction> getAuctionByIdAuction = apiService.getAuctionByIdAuction(bid.getIdAuction());
        getAuctionByIdAuction.enqueue(new Callback<Auction>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<Auction> call, Response<Auction> response) {
                Auction auction = response.body();
                if (auction.getStatus().equals("OUVERTE")) {
                    myPrefsEditor.putString("auctionSelected", gson.toJson(auction));
                    myPrefsEditor.apply();
                    Intent annonceActivity = new Intent(ListeMesEncheresActivity.this, AnnonceActivity.class);
                    startActivity(annonceActivity);
                } else {
                    Toast.makeText(getApplicationContext(), "Enchère close !", Toast.LENGTH_SHORT).show();
                }
                OnDone();
            }

            @Override
            public void onFailure(Call<Auction> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, "onFailure Erreur : " + t.toString());
                OnDone();
            }
        });
    }

    private void OnDone() {
        showProgress(false);
        recyclerView.setAlpha(1);
        swipeRefreshLayout.setRefreshing(false);
        configureOnClickRecyclerView();
    }

    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView, R.layout.activity_liste_mes_encheres)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Bid bid = myBidAdapter.getBid(position);
                        showProgress(true);
                        recyclerView.setAlpha(0);
                        getAuction(bid);
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
