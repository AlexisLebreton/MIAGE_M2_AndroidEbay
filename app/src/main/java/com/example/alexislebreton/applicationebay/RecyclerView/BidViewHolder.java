package com.example.alexislebreton.applicationebay.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexislebreton.applicationebay.R;
import com.example.alexislebreton.applicationebay.model.Bid;

public class BidViewHolder extends RecyclerView.ViewHolder {

    private TextView itemNameView, statusView, sellerUsernameView, descriptionView, bidderUsernameView, bidPriceView, timeRemainingView;
    private ImageView imageView;

    //itemView est la vue correspondante à 1 cellule
    public BidViewHolder(View itemView) {
        super(itemView);
        itemNameView = itemView.findViewById(R.id.itemName);
        statusView = itemView.findViewById(R.id.status);
        descriptionView = itemView.findViewById(R.id.description);
        bidderUsernameView = itemView.findViewById(R.id.bidderUsername);
        bidPriceView = itemView.findViewById(R.id.bidPrice);
        timeRemainingView = itemView.findViewById(R.id.timeRemaining);
    }

    public void bind(Bid myBid) {
        itemNameView.setText(myBid.getAuction().getItemName() + " (" + myBid.getAuction().getSellerUsername() + ")");
        statusView.setText(myBid.getAuction().getStatus());
        descriptionView.setText(myBid.getAuction().getItemDescription());
        bidderUsernameView.setText(myBid.getBidderUsername());
        bidPriceView.setText(myBid.getPrice().toString() + "€");
        timeRemainingView.setText(millToMins(myBid.getAuction().getTimeRemaining()) + "min");
    }

    private String millToMins(long millisec) {
        return millisec / (60000) + ":" + (int) (millisec/1000) % (60);
    }
}
