package com.example.buyinglistocr.model;

import android.content.ContentValues;
import android.content.Context;

/**
 * Allow to interact with the "Item" table
 */
public class ShopDAO {

    private Context context;
    /**
     * The constructor of the class
     * @param context - The context
     */
    public ShopDAO(Context context) {

        this.context = context;

    }


    public long add(Shop shop) {

        return 0;

    }

}