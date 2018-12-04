package com.example.alexislebreton.applicationebay;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.alexislebreton.applicationebay.model.User;
import com.example.alexislebreton.applicationebay.rest.ApiClient;
import com.example.alexislebreton.applicationebay.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class restAPI extends AppCompatActivity {
    private static final String TAG = restAPI.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_api);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ArrayList<User>> allUsers = apiService.getAllUsers();
        allUsers.enqueue(new Callback<ArrayList<User>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                List<User> users = response.body();
                Log.d(TAG, "Number of users received: " + users.size());
                Log.d(TAG, "Users : " + users.toString());
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
}
