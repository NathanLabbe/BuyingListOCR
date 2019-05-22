package com.example.buyinglistocr.model;

import android.content.ContentValues;
import android.content.Context;

/**
 * Allow to interact with the "Item" table
 */
public class ShopDAO extends DAOBase {

    // "Shop" table
    public static final String SHOP_TABLE_NAME = "Shop";

    // Attributes of "Shop" table
    public static final String SHOP_KEY = "id";
    public static final String SHOP_NAME = "name";

    /**
     * The constructor of the class
     * @param pContext - The context
     */
    public ShopDAO(Context pContext) {

        super(pContext);

    }


    public long add(Shop shop) {

        // The id of the item
        long ret;

        // Open the connection with the database
        mDb = open();

        // Specify the values which wil be inserted
        ContentValues value = new ContentValues();
        value.put(ShopDAO.SHOP_NAME, shop.getName());
        // Insert the data in the database
        ret = mDb.insert(ShopDAO.SHOP_TABLE_NAME, null, value);

        // Close the connection with the database
        mDb.close();

        return ret;

    }

}