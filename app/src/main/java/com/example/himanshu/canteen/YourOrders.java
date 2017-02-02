package com.example.himanshu.canteen;

/**
 * Created by himanshu on 2/2/17.
 */

public class YourOrders {
    private String itemName;
    private int totalRupees;

    public YourOrders(){
    }

    public YourOrders(String itemName, int totalRupees){
        this.itemName=itemName;
        this.totalRupees=totalRupees;
    }

    public void setItemName(String itemName){
        this.itemName=itemName;
    }
    public String getItemName(){
        return itemName;
    }
    public void setTotalRupees(int totalRupees){
        this.totalRupees=totalRupees;
    }
    public int getTotalRupees(){
        return totalRupees;
    }

}
