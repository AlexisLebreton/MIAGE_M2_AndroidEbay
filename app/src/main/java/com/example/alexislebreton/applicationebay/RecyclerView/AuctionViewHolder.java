package com.example.alexislebreton.applicationebay.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexislebreton.applicationebay.R;
import com.example.alexislebreton.applicationebay.model.Auction;

public class AuctionViewHolder extends RecyclerView.ViewHolder {

    private TextView itemNameView, statusView, sellerUsernameView, descriptionView, bidderUsernameView, bidPriceView, timeRemainingView;
    private ImageView imageView;

    public AuctionViewHolder(View itemView) {
        super(itemView);
        itemNameView = itemView.findViewById(R.id.itemName);
        statusView = itemView.findViewById(R.id.status);
        descriptionView = itemView.findViewById(R.id.description);
        bidderUsernameView = itemView.findViewById(R.id.bidderUsername);
        bidPriceView = itemView.findViewById(R.id.bidPrice);
        timeRemainingView = itemView.findViewById(R.id.timeRemaining);
        //imageView = (ImageView) itemView.findViewById(R.id.image);
    }

    public void bind(Auction myAuction) {
        itemNameView.setText(myAuction.getItemName() + " (" + myAuction.getSellerUsername() + ")");
        statusView.setText(myAuction.getStatus());
        descriptionView.setText(myAuction.getItemDescription());
        bidderUsernameView.setText((!myAuction.getSellerUsername().equals(myAuction.getHighestBid().getBidderUsername())) ? " " + myAuction.getHighestBid().getBidderUsername() : "  ---");
        bidPriceView.setText(myAuction.getHighestBid().getPrice().toString() + "€");
        timeRemainingView.setText(millToMins(myAuction.getTimeRemaining()) + " min");
    }

    private String millToMins(long millisec) {
        return millisec / (60000) + ":" + (int) (millisec / 1000) % (60);
    }
}
