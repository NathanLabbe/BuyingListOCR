package com.example.buyinglistocr.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Allow to interact with the "Item" table
 */
public class SavePurchaseDAO extends DAOBase {

    // "Product" table
    public static final String SAVEPURCHASE_TABLE_NAME = "Purchase";

    // Attributes of "Product" table
    public static final String SAVEPURCHASE_KEY = "id";
    public static final String SAVEPURCHASE_NAME = "name";

    /**
     * The constructor of the class
     * @param pContext - The context
     */
    public SavePurchaseDAO(Context pContext) {

        super(pContext);

    }
    public long add(SavePurchase purchase) {

        // The id of the item
        long ret;

        // Open the connection with the database
        mDb = open();

        // Specify the values which wil be inserted
        ContentValues value = new ContentValues();
        value.put(SavePurchaseDAO.SAVEPURCHASE_NAME, purchase.getName());


        // Insert the data in the database
        ret = mDb.insert(SavePurchaseDAO.SAVEPURCHASE_TABLE_NAME, null, value);

        // Close the connection with the database
        mDb.close();

        return ret;

    }

    public ArrayList<SavePurchase> getAll(long savepurchasekey) {

        // The return value
        ArrayList<SavePurchase> ret = new ArrayList<>();

        // Open the connection with the database
        mDb = open();

        // Get all data of the table
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + SavePurchaseDAO.SAVEPURCHASE_TABLE_NAME + " WHERE " + SavePurchaseDAO.SAVEPURCHASE_KEY + " = " + savepurchasekey, null);

        // If the table isn't empty
        if(cursor.getCount() > 0) {

            // Browse all data
            while (cursor.moveToNext()) {

                Long id = cursor.getLong(0);
                String name = cursor.getString(1);


                ret.add(new SavePurchase(id, name));

            }

        }

        // Close the cursor
        cursor.close();

        // Close the connection with the database
        mDb.close();

        return ret;
    }

    public void delete(long id) {

        // Open the connection with the database
        mDb = open();

        // Delete the data in the database
        mDb.delete(SavePurchaseDAO.SAVEPURCHASE_TABLE_NAME,  SavePurchaseDAO.SAVEPURCHASE_KEY + " = ?", new String[] {String.valueOf(id)});

        // Close the connection with the database
        mDb.close();
    }

    public SavePurchase getSavePurchase(long id) {

        // The return value
        SavePurchase ret;

        // Open the connection with the database
        mDb = open();

        // Get all data of a specific item
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + SavePurchaseDAO.SAVEPURCHASE_TABLE_NAME + " WHERE " + SavePurchaseDAO.SAVEPURCHASE_KEY + " = " + id, null);

        // Go to the head of data
        cursor.moveToFirst();

        // Create the new item with the data
        String name = cursor.getString(1);


        ret = new SavePurchase(id, name );

        cursor.close();

        // Close the connection with the database
        mDb.close();

        return ret;

    }
   /* public ArrayList<Product> getAll(long shopKey) {

        // The return value
        ArrayList<Product> ret = new ArrayList<>();

        // Open the connection with the database
        mDb = open();

        // Get all data of the table
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + SavePurchaseDAO.SAVEPURCHASE_TABLE_NAME + " WHERE " + SavePurchaseDAO.SAVEPURCHASE_KEY + " = " + shopKey, null);

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
    }*/

    public void clear() {

        // Open the connection with the database
        mDb = open();

        // delete all data in the database
        mDb.delete(SavePurchaseDAO.SAVEPURCHASE_TABLE_NAME, null, null);

        // Close the connection with the database
        mDb.close();

    }

}