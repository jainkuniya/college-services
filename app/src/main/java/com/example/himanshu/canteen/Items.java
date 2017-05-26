package com.example.himanshu.canteen;

/**
 * Created by himanshu on 13/1/17.
 */

public class Items {
    private String id, itemName;
    private int itemQty, itemPrice;
    private Long  s_no;
    private boolean isAvailable;

    public Items() {

    }

    public Items(String id, String itemName,int itemPrice, boolean isAvailable) {
        this.id = id;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.isAvailable = isAvailable;
    }

    public Items(Long s_no, String itemName, int itemPrice, int itemQty) {
        this.s_no = s_no;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQty = itemQty;
    }

    public void setS_no(Long s_no) {
        this.s_no = s_no;
    }

    public Long getS_no() {
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

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getId() {
        return id;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
