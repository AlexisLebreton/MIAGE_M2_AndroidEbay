package com.example.alexislebreton.applicationebay.model;

import java.lang.reflect.Field;

public class Bid {

    private String _id, idAuction, bidderUsername;
    private Long price;
    private Auction auction = null;
    private User bidder;

    public Bid(String idAuction, String bidderUsername, Long price) {
        this.idAuction = idAuction;
        this.bidderUsername = bidderUsername;
        this.price = price;
    }

    public Bid(String idAuction, String bidderUsername, Long price, User user, Auction auction) {
        this(idAuction, bidderUsername, price);
        this.bidder = user;
        this.auction = auction;
    }

    public String getIdAuction() {
        return idAuction;
    }

    public String getBidderUsername() {
        return bidderUsername;
    }

    public void setBidderUsername(String bidderUsername) {
        this.bidderUsername = bidderUsername;
    }

    public Long getPrice() {
        return price;
    }

    public Auction getAuction() {
        return auction;
    }

    public User getBidder() {
        return bidder;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append(this.getClass().getName());
        result.append(" Object {");
        result.append(newLine);

        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            result.append("  ");
            try {
                result.append(field.getName());
                result.append(": ");
                result.append(field.get(this));
            } catch (IllegalAccessException ex) {
                System.out.println(ex);
            }
            result.append(newLine);
        }
        result.append("}");

        return result.toString();
    }
}
