package com.example.alexislebreton.applicationebay.model;

import java.lang.reflect.Field;

public class Auction {

    private String _id;
    private String sellerUsername;
    private String itemName;
    private String itemDescription;
    private Long minPrice;
    private String date;
    private Long endTime;
    private String photoURL;
    private Bid highestBid;

    public Auction(String sellerUsername, String itemName, String itemDescription, Long minPrice, String date, String photoURL, Bid highestBid) {
        this.sellerUsername = sellerUsername;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.minPrice = minPrice;
        this.date = date;
        this.photoURL = photoURL;
        this.highestBid = highestBid;
        this.endTime = System.currentTimeMillis() + 5 * 60000;
    }

    public String get_id() {
        return _id;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public Long getMinPrice() {
        return minPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getStatus() {
        return (this.endTime - System.currentTimeMillis() <= 0) ? "CLOTUREE" : "OUVERTE";
    }

    public Bid getHighestBid() {
        return highestBid;
    }

    public Long getTimeRemaining() {
        return (this.endTime - System.currentTimeMillis() <= 0) ? 0 : this.endTime - System.currentTimeMillis();
    }

    public Long getEndTime() {
        return endTime;
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
