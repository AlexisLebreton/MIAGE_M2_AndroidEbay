package com.example.alexislebreton.applicationebay.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexislebreton.applicationebay.R;
import com.example.alexislebreton.applicationebay.model.Auction;

public class AuctionViewHolder extends RecyclerView.ViewHolder {

    private TextView itemNameView, sellerUsernameView, minPriceView, currentBidView, timespanView;
    private ImageView imageView;

    //itemView est la vue correspondante à 1 cellule
    public AuctionViewHolder(View itemView) {
        super(itemView);
        itemNameView = itemView.findViewById(R.id.itemName);
        sellerUsernameView = itemView.findViewById(R.id.sellerUsername);
        minPriceView = itemView.findViewById(R.id.minPrice);
        currentBidView = itemView.findViewById(R.id.currentBid);
        timespanView = itemView.findViewById(R.id.timespan);
        //imageView = (ImageView) itemView.findViewById(R.id.image);
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(Auction myAuction) {
        itemNameView.setText(myAuction.getItemName());
        sellerUsernameView.setText(myAuction.getSellerUsername());
        minPriceView.setText(myAuction.getMinPrice().toString());
        currentBidView.setText(myAuction.getMinPrice().toString());
        timespanView.setText(myAuction.getTimespan().toString());
    }
}
