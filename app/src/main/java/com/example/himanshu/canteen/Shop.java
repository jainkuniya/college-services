package com.example.himanshu.canteen;

/**
 * Created by himanshu on 10/12/16.
 */

public class Shop {
    private String num;
    private String name;
    private int thummbnail;

    public Shop(String num, String name, int thummbnail) {
        this.num = num;
        this.name = name;
        this.thummbnail = thummbnail;
    }

    public String getId() {
        return num;
    }

    public void setId(String num) {
        this.num = num;
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
        this.thummbnail = thummbnail;
    }
}
