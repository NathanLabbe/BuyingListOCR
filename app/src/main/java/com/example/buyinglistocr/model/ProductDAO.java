package com.example.buyinglistocr.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Allow to interact with the "Product" table
 */
public class ProductDAO extends DAOBase {

    // "Product" table
    public static final String PRODUCT_TABLE_NAME = "Product";

    // Attributes of "Product" table
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
     * Allow to add a product in the "Product" table
     * @param product - The product
     */
    public void add(Product product) {

        // Open the connection with the database
        mDb = open();

        // Specify the values which wil be inserted
        ContentValues value = new ContentValues();
        value.put(ProductDAO.PRODUCT_NAME, product.getName());
        value.put(ProductDAO.PRODUCT_QUANTITY_BASE, product.getQuantityBase());
        value.put(ProductDAO.PRODUCT_QUANTITY_ACT, product.getQuantityAct());
        value.put(ProductDAO.PRODUCT_NOTE, product.getNote());
        value.put(ProductDAO.PRODUCT_STATUT, product.getStatut());
        value.put(ProductDAO.PRODUCT_KEY_LIST, product.getIdList());
        // Insert the data in the database
        mDb.insert(ProductDAO.PRODUCT_TABLE_NAME, null, value);

        // Close the connection with the database
        mDb.close();

    }

    /**
     * Allow to get all name of a list
     * @return - The ArrayList<String> of the name courses
     */
    public ArrayList<String> getNames(long id) {

        // The return value
        ArrayList<String> ret = new ArrayList<>();

        // Open the connection with the database
        mDb = open();

        String query = " SELECT " + PRODUCT_NAME + " FROM " + PRODUCT_TABLE_NAME + " WHERE " + ProductDAO.PRODUCT_KEY_LIST + " = " + id;

        Cursor cursor = mDb.rawQuery(query, null);

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

    /**
     * Allow to get all product
     * @param id
     * @return
     */
    public ArrayList<Product> getAllProducts(long id) {

        // The return value
        ArrayList<Product> ret = new ArrayList<>();

        // Open the connection with the database
        mDb = open();

        Cursor cursor = mDb.rawQuery("SELECT * FROM " + PRODUCT_TABLE_NAME + " WHERE " + ProductDAO.PRODUCT_KEY_LIST + " = " + id, null);

        if(cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                String name = cursor.getString(1);
                int quantityBase = cursor.getInt(2);
                int quantityAct = cursor.getInt(3);
                String note = cursor.getString(4);
                int statut = cursor.getInt(5);

                // TEST
                System.out.println("PRODUCT : " + name);

                ret.add(new Product(name, quantityBase, quantityAct, note, statut, id));

            }

        }

        cursor.close();

        // Close the connection with the database
        mDb.close();

        return ret;
    }

    /**
     * Allow to get the name of the product
     * @param id
     * @return
     */
    public String getName(long id) {

        // The return value
        String ret = "";

        // Open the connection with the database
        mDb = open();

        String query = "SELECT " + PRODUCT_NAME + " FROM " + ProductDAO.PRODUCT_TABLE_NAME + " WHERE " + ProductDAO.PRODUCT_KEY + " = " + id;
        Cursor cursor = mDb.rawQuery(query, null);
        cursor.moveToFirst();

        ret = cursor.getString(0);

        cursor.close();

        // Close the connection with the database
        mDb.close();

        return ret;

    }

    /**
     * Allow to get the idProduct of the product
     * @param str - The name
     * @return - The idProduct
     */
    public int getId(String str) {

        // The return value
        int ret = 0;

        // Open the connection with the database
        mDb = open();

        String query = "SELECT * FROM " + ProductDAO.PRODUCT_TABLE_NAME + " WHERE " + ProductDAO.PRODUCT_NAME + " = \"" + str + "\"";
        Cursor cursor = mDb.rawQuery(query, null);
        cursor.moveToFirst();

        ret = cursor.getInt(0);

        cursor.close();

        // Close the connection with the database
        mDb.close();

        return ret;

    }

    /**
     *  Allow to delete a product with this idProduct
     * @param id - The idProduct of the product
     */
    public void delete(long id) {

        // Open the connection with the database
        mDb = open();

        mDb.delete(ProductDAO.PRODUCT_TABLE_NAME, ProductDAO.PRODUCT_KEY + " = ?", new String[]{String.valueOf(id)} );

        // Close the connection with the database
        mDb.close();

    }

    /**
     * Allow to update the name of the product
     * @param id - The idProduct
     * @param str - The new name
     */
    public void updateName(long id, String str) {

        // Open the connection with the database
        mDb = open();

        ContentValues cv = new ContentValues();
        cv.put(ProductDAO.PRODUCT_NAME, str);

        mDb.update(ProductDAO.PRODUCT_TABLE_NAME, cv, ProductDAO.PRODUCT_KEY + " = ?", new String[]{String.valueOf(id)});

        // Close the connection with the database
        mDb.close();

    }


    public boolean exist(CharSequence str, long id) {

        // The return value
        Boolean ret = false;

        // Open the connection with the database
        mDb = open();

        String query = "SELECT * FROM " + ProductDAO.PRODUCT_TABLE_NAME + " WHERE " + ProductDAO.PRODUCT_NAME + " = \"" + str + "\" AND " + ProductDAO.PRODUCT_KEY_LIST + " = " + id;
        Cursor cursor = mDb.rawQuery(query, null);

        if(cursor.getCount() > 0) {
            ret = true;
        }

        // Close the connection with the database
        mDb.close();

        return ret;

    }

    public void deleteAll() {

        // Open the connection with the database
        mDb = open();

        mDb.delete(ProductDAO.PRODUCT_TABLE_NAME, null, null);

        // Close the connection with the database
        mDb.close();

    }

}

