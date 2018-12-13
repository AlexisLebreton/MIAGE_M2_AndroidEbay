package com.example.alexislebreton.applicationebay.Adapter;

import android.os.CountDownTimer;
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

    public MyAuctionAdapter(final List<Auction> list) {
        this.list = list;

        long maxTime = System.currentTimeMillis();
        for (Auction item : list) {
            maxTime = Math.max(maxTime, item.getEndTime());
        }

        new CountDownTimer(maxTime - System.currentTimeMillis(), 1000) {
            @Override
            public void onTick(long l) {
                notifyDataSetChanged();
            }

            @Override
            public void onFinish() {
                notifyDataSetChanged();
            }
        }.start();
    }

    @Override
    public AuctionViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_auctions, viewGroup, false);
        return new AuctionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AuctionViewHolder myViewHolder, int position) {
        Auction myObject = list.get(position);
        myViewHolder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Auction getAuction(int position) {
        return this.list.get(position);
    }
}
