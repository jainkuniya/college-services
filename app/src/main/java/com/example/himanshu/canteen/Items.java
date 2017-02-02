package com.example.himanshu.canteen;

/**
 * Created by himanshu on 13/1/17.
 */

public class Items {
    private String itemName;
    private int itemQty, s_no, itemPrice;

    public Items() {

    }

    public Items(int s_no, String itemName, int itemPrice, int itemQty) {
        this.s_no = s_no;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQty = itemQty;
    }

    public void setS_no(int s_no) {
        this.s_no = s_no;
    }

    public int getS_no() {
        return s_no;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public int getItemQty() {
        return itemQty;
    }
}
