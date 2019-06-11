package com.example.buyinglistocr.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Allow to interact with the "Item" table
 */
public class  SavePurchaseDAO {


    /**
     * The constructor of the class
     * @param pContext - The context
     */
    public SavePurchaseDAO(Context pContext) {



    }
    public long add(SavePurchase purchase) {

        return 0;

    }

    public ArrayList<SavePurchase> getAll(long savepurchasekey) {

        // The return value
        ArrayList<SavePurchase> ret = new ArrayList<>();



        return ret;
    }

    public void delete(long id) {



    }

    public SavePurchase getSavePurchase(long id) {

        return null;

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

}