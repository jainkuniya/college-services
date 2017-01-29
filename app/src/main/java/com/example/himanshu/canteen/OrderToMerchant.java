package com.example.himanshu.canteen;

/**
 * Created by khushboo on 29/1/17.
 */

public class OrderToMerchant {
    private String initial, user_id, total_price;

    public OrderToMerchant(String initial, String user_id, String total_price) {
        this.initial=initial;
        this.user_id=user_id;
        this.total_price=total_price;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial=initial;
    }

    public String getShop_name() {
        return  user_id;
    }

    public void setShop_name(String shop_name) {
        this.user_id=shop_name;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price=total_price;
    }
}
