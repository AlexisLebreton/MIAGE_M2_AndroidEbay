package com.example.alexislebreton.applicationebay.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexislebreton.applicationebay.R;
import com.example.alexislebreton.applicationebay.model.Auction;
import com.example.alexislebreton.applicationebay.model.Bid;

public class BidViewHolder extends RecyclerView.ViewHolder {

    private TextView idAuctionView, bidderUsernameView, priceView;
    private ImageView imageView;

    //itemView est la vue correspondante à 1 cellule
    public BidViewHolder(View itemView) {
        super(itemView);
        idAuctionView = (TextView) itemView.findViewById(R.id.idAuction);
        bidderUsernameView = (TextView) itemView.findViewById(R.id.bidderUsername);
        priceView = (TextView) itemView.findViewById(R.id.price);
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(Bid myBid) {
        idAuctionView.setText(myBid.getIdAuction());
        bidderUsernameView.setText(myBid.getBidderUsername());
        priceView.setText(myBid.getPrice().toString());
    }
}
