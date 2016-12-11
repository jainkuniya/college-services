package com.example.himanshu.canteen;

import static android.R.attr.id;

/**
 * Created by himanshu on 10/12/16.
 */

public class Shop {
    private int num;
    private String name;
    private int thummbnail;

    public Shop() {

    }

    public Shop(int num, String name, int thummbnail) {
        this.num=num;
        this.name=name;
        this.thummbnail=thummbnail;
    }

    public int getId() {
        return num;
    }

    public void setId(int num) {
        this.num=num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThummbnail() {
        return thummbnail;
    }

    public void setThummbnail(int thummbnail) {
        this.thummbnail=thummbnail;
    }
}
