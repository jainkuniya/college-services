package com.example.himanshu.canteen;

import java.sql.Time;

import static com.example.himanshu.canteen.R.id.price;
/**
 * Created by himanshu on 9/12/16.
 */

public class Order {
    private String initial, shop_name, total_price;

    public Order() {

    }

    public Order(String initial, String shop_name, String total_price) {
        this.initial=initial;
        this.shop_name=shop_name;
        this.total_price=total_price;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial=initial;
    }

    public String getShop_name() {
        return  shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name=shop_name;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price=total_price;
    }
}
