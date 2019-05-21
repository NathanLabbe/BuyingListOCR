package com.example.buyinglistocr.model;

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

}