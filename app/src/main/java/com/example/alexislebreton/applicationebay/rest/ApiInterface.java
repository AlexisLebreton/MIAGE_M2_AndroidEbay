package com.example.alexislebreton.applicationebay.rest;

import com.example.alexislebreton.applicationebay.model.Auction;
import com.example.alexislebreton.applicationebay.model.Bid;
import com.example.alexislebreton.applicationebay.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ApiInterface {

    // Users
    @GET("users")
    Call<ArrayList<User>> getAllUsers();

    @GET("users/getUserByUserName/{username}")
    Call<User> getUserByUserName(@Path("username") String username);

    @POST("user")
    Call<String> addNewUser(@Body User user);

    @POST("users/updateUserByUsername/{username}")
    Call<String> updateUserByUsername(@Path("username") String username, @Body User user);

    @GET("users/deleteUserByUsername/{username}")
    Call<User> deleteUserByUsername(@Path("username") String username);

    // Bids
    @GET("bids")
    Call<ArrayList<Bid>> getAllBids();

    @GET("bids/getBidByBidderUsername/{username}")
    Call<ArrayList<Bid>> getBidByBidderUsername(@Path("username") String username);

    @GET("bids/getBidByIdAuction/{idAuction}")
    Call<Bid> getBidByIdAuction(@Path("idAuction") String idAuction);

    @POST("bid")
    Call<String> addNewBid(@Body Bid bid);

    @GET("bids/deleteBidByIdAuction/{idAuction}")
    Call<Bid> deleteBidByIdAuction(@Path("idAuction") String idAuction);

    // Auctions
    @GET("auctions")
    Call<ArrayList<Auction>> getAllAuctions();

    @GET("auctions/getAuctionsBySellerUsername/{username}")
    Call<ArrayList<Auction>> getAuctionsBySellerUsername(@Path("username") String username);

    @POST("auction")
    Call<String> addNewAuction(@Body Auction auction);

    @GET("auctions/deleteAuctionByIdAuction/{idAuction}")
    Call<Auction> deleteAuctionByIdAuction(@Path("idAuction") String idAuction);
}