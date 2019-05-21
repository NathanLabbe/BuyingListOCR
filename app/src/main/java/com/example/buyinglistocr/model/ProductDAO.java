package com.example.buyinglistocr.model;

import android.content.Context;

/**
 * Allow to interact with the "Item" table
 */
public class ProductDAO extends DAOBase {

    // "Product" table
    public static final String PRODUCT_TABLE_NAME = "Product";

    // Attributes of "Product" table
    public static final String PRODUCT_KEY = "id";
    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_TYPE = "type";
    public static final String PRODUCT_PRICE = "price";
    public static final String PRODUCT_KEY_SHOP = "idShop";

    /**
     * The constructor of the class
     * @param pContext - The context
     */
    public ProductDAO(Context pContext) {

        super(pContext);

    }

}