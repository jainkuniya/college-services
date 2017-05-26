package com.example.himanshu.canteen;

import java.util.HashMap;

/**
 * Created by himanshu on 2/2/17.
 */

public class Singleton {
    private static Singleton singleton;
    private static HashMap<Long,Items> itemsSparseArray;

    public static Singleton getInstance() {
        if (singleton == null) {
            itemsSparseArray = new HashMap<>();
            singleton = new Singleton();
            return singleton;
        } else {
            return singleton;
        }
    }
    public HashMap<Long,Items> getItemsSparseArray() {
        return itemsSparseArray;
    }
}
