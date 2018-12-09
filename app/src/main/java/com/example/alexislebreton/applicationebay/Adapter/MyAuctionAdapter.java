package com.example.alexislebreton.applicationebay.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alexislebreton.applicationebay.R;
import com.example.alexislebreton.applicationebay.RecyclerView.AuctionViewHolder;
import com.example.alexislebreton.applicationebay.model.Auction;

import java.util.List;

public class MyAuctionAdapter extends RecyclerView.Adapter<AuctionViewHolder> {
    List<Auction> list;

    //ajouter un constructeur prenant en entrée une liste
    public MyAuctionAdapter(List<Auction> list) {
        this.list = list;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public AuctionViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_auctions, viewGroup, false);
        return new AuctionViewHolder(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(AuctionViewHolder myViewHolder, int position) {
        Auction myObject = list.get(position);
        myViewHolder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Auction getAuction(int position){
        return this.list.get(position);
    }
}
