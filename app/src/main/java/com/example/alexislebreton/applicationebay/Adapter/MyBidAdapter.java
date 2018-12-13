package com.example.alexislebreton.applicationebay.Adapter;

import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alexislebreton.applicationebay.R;
import com.example.alexislebreton.applicationebay.RecyclerView.BidViewHolder;
import com.example.alexislebreton.applicationebay.model.Auction;
import com.example.alexislebreton.applicationebay.model.Bid;

import java.util.List;

public class MyBidAdapter extends RecyclerView.Adapter<BidViewHolder> {
    List<Bid> list;

    public MyBidAdapter(List<Bid> list) {
        this.list = list;

        long maxTime = System.currentTimeMillis();
        for (Bid item : list) {
            maxTime = Math.max(maxTime, item.getAuction().getEndTime());
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
    public BidViewHolder onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_bids, viewGroup, false);
        return new BidViewHolder(view);
    }

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
