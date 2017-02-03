package com.example.himanshu.canteen;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by himanshu on 2/2/17.
 */

public class Singleton {
    private static Singleton singleton;
    private static SparseArray<Items> itemsSparseArray;

    public static Singleton getInstance() {
        if (singleton == null) {
            itemsSparseArray = new SparseArray<>();
            singleton = new Singleton();
            return singleton;
        } else {
            return singleton;
        }
    }

    public SparseArray<Items> getItemsSparseArray() {
        return itemsSparseArray;
    }
}
