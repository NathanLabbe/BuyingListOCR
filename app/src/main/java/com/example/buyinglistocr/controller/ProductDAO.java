package com.example.buyinglistocr.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.buyinglistocr.model.List;
import com.example.buyinglistocr.model.Product;

import java.util.ArrayList;

/**
 * Allow to interact with the "List" table
 */
public class ProductDAO extends DAOBase {

    // "Produit" table
    public static final String PRODUCT_TABLE_NAME = "Product";

    // Attributes of "Produit" table
    public static final String PRODUCT_KEY = "idProduit";
    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_QUANTITY_BASE = "quantityBase";
    public static final String PRODUCT_QUANTITY_ACT = "quantityAct";
    public static final String PRODUCT_NOTE = "note";
    public static final String PRODUCT_STATUT = "statut";
    public static final String PRODUCT_KEY_LIST = "idList";

    /**
     * The constructor of the class
     * @param pContext - The context
     */
    public ProductDAO(Context pContext) {

        super(pContext);

    }

    /**
     * Allow to add a product in the "List" table
     * @param product - The product
     */
    public void add(Product product) {

        // Open the connection with the database
        mDb = open();

        // Specify the values which wil be inserted
        ContentValues value = new ContentValues();
        value.put(ProductDAO.PRODUIT_NAME, product.getName());

        // Insert the data in the database
        mDb.insert(ListDAO.PRODUCT_TABLE_NAME, null, value);

        // Close the connection with the database
        mDb.close();

    }

    /**
     * Allow to get all name of course
     * @return - The ArrayList<String> of the name courses
     */
    public ArrayList<String> get() {

        // The return value
        ArrayList<String> ret = new ArrayList<>();

        // Open the connection with the database
        mDb = open();

        Cursor cursor = mDb.rawQuery("select " + PRODUIT_NAME + " from " + PRODUIT_TABLE_NAME, null);

        if(cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                String name = cursor.getString(0);

                ret.add(name);

            }

        }

        cursor.close();

        // Close the connection with the database
        mDb.close();

        return ret;

    }

}

