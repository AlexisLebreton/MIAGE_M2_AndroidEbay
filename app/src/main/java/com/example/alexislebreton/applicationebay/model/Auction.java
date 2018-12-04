package com.example.alexislebreton.applicationebay.model;

import java.lang.reflect.Field;

public class Auction {

    private String sellerUsername;
    private String itemName;
    private String itemDescription;
    private Long minPrice;
    private String date;
    private Long timespan;
    private String photoURL;
    private String status;

    public Auction(String sellerUsername, String itemName, String itemDescription, Long minPrice, String date, Long timespan, String photoURL, String status) {
        this.sellerUsername = sellerUsername;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.minPrice = minPrice;
        this.date = date;
        this.timespan = timespan;
        this.photoURL = photoURL;
        this.status = status;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
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

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Long getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Long minPrice) {
        this.minPrice = minPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getTimespan() {
        return timespan;
    }

    public void setTimespan(Long timespan) {
        this.timespan = timespan;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append( this.getClass().getName() );
        result.append( " Object {" );
        result.append(newLine);

        //determine fields declared in this class only (no fields of superclass)
        Field[] fields = this.getClass().getDeclaredFields();

        //print field names paired with their values
        for ( Field field : fields  ) {
            result.append("  ");
            try {
                result.append( field.getName() );
                result.append(": ");
                //requires access to private field:
                result.append( field.get(this) );
            } catch ( IllegalAccessException ex ) {
                System.out.println(ex);
            }
            result.append(newLine);
        }
        result.append("}");

        return result.toString();
    }
}
