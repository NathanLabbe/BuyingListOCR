package com.example.buyinglistocr.model;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Allow to interact with the "Item" table
 */
public class ProductDAO extends DAOBase {

    // "Product" table
    public static final String PRODUCT_TABLE_NAME = "Product";

    // Attributes of "Product" table
    public static final String PRODUCT_KEY = "id";
    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_CORRESPONDENCE = "correspondence";
    public static final String PRODUCT_KEY_SHOP = "idShop";

    /**
     * The constructor of the class
     * @param pContext - The context
     */
    public ProductDAO(Context pContext) {

        super(pContext);

    }

    public Product getProduct(long id) {

        // The return value
        Product ret;

        // Open the connection with the database
        mDb = open();

        // Get all data of a specific item
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + ProductDAO.PRODUCT_TABLE_NAME + " WHERE " + ProductDAO.PRODUCT_KEY + " = " + id, null);

        // Go to the head of data
        cursor.moveToFirst();

        // Create the new item with the data
        String name = cursor.getString(1);
        String correspondence = cursor.getString(2);
        int idShop = cursor.getInt(3);


        ret = new Product(id, name, correspondence, idShop );

        cursor.close();

        // Close the connection with the database
        mDb.close();

        return ret;

    }
    public ArrayList<Product> getAll(long shopKey) {

        // The return value
        ArrayList<Product> ret = new ArrayList<>();

        // Open the connection with the database
        mDb = open();

        // Get all data of the table
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + ProductDAO.PRODUCT_TABLE_NAME + " WHERE " + ProductDAO.PRODUCT_KEY + " = " + shopKey, null);

        // If the table isn't empty
        if(cursor.getCount() > 0) {

            // Browse all data
            while (cursor.moveToNext()) {

                Long id = cursor.getLong(0);
                String name = cursor.getString(1);
                String correspondence = cursor.getString(2);
                int idShop = cursor.getInt(3);


                ret.add(new Product(id, name,  correspondence, idShop));

            }

        }

        // Close the cursor
        cursor.close();

        // Close the connection with the database
        mDb.close();

        return ret;
    }

}