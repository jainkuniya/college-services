package com.example.himanshu.canteen;

/**
 * Created by khushboo on 29/1/17.
 */

public class OrderToMerchant {
    private String id, name, items, totalPrice;
    private boolean isVerified, isDelivered;
    private boolean verified;

    public OrderToMerchant(String id, String name, String items, String totalPrice, boolean isVerified, boolean isDelivered) {
        this.id = id;
        this.name = name;
        this.items = items;
        this.isVerified = isVerified;
        this.isDelivered = isDelivered;
        this.totalPrice = totalPrice;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getItems() {
        return items;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
