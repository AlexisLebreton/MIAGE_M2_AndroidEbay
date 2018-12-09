package com.example.alexislebreton.applicationebay.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alexislebreton.applicationebay.R;
import com.example.alexislebreton.applicationebay.RecyclerView.BidViewHolder;
import com.example.alexislebreton.applicationebay.model.Bid;

import java.util.List;

public class MyBidAdapter extends RecyclerView.Adapter<BidViewHolder> {
    List<Bid> list;

    //ajouter un constructeur prenant en entrée une liste
    public MyBidAdapter(List<Bid> list) {
        this.list = list;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public BidViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_bids, viewGroup, false);
        return new BidViewHolder(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(BidViewHolder myViewHolder, int position) {
        Bid myObject = list.get(position);
        myViewHolder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Bid getBid(int position) {
        return this.list.get(position);
    }
}
