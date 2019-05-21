package com.example.buyinglistocr.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Allow to interact with the "Product" table
 */
public class ItemDAO extends DAOBase {

    // "Item" table
    public static final String ITEM_TABLE_NAME = "Item";

    // Attributes of "Item" table
    public static final String ITEM_KEY = "id";
    public static final String ITEM_NAME = "name";
    public static final String ITEM_QUANTITY_DESIRED = "quantityDesired";
    public static final String ITEM_QUANTITY_GOT = "quantityGot";
    public static final String ITEM_NOTE = "note";
    public static final String ITEM_STATUS = "status";
    public static final String ITEM_KEY_LIST = "idList";

    /**
     * The constructor of the class
     * @param pContext - The context
     */
    public ItemDAO(Context pContext) {

        super(pContext);

    }

    /**
     * Allow to add an item in the "Item" table
     * @param item - The item
     * @return - The id of the item
     */
    public long add(Item item) {

        // The id of the item
        long ret;

        // Open the connection with the database
        mDb = open();

        // Specify the values which wil be inserted
        ContentValues value = new ContentValues();
        value.put(ItemDAO.ITEM_NAME, item.getName());
        value.put(ItemDAO.ITEM_QUANTITY_DESIRED, item.getQuantityDesired());
        value.put(ItemDAO.ITEM_QUANTITY_GOT, item.getQuantityGot());
        value.put(ItemDAO.ITEM_NOTE, item.getNote());
        value.put(ItemDAO.ITEM_STATUS, item.getStatus());
        value.put(ItemDAO.ITEM_KEY_LIST, item.getIdList());

        // Insert the data in the database
        ret = mDb.insert(ItemDAO.ITEM_TABLE_NAME, null, value);

        // Close the connection with the database
        mDb.close();

        return ret;

    }

    /**
     * Allow to delete an item in the "Item" table
     * @param id - The id of the item
     */
    public void delete(long id) {

        // Open the connection with the database
        mDb = open();

        // Delete the data in the database
        mDb.delete(ItemDAO.ITEM_TABLE_NAME, ItemDAO.ITEM_KEY + " = ?", new String[] {String.valueOf(id)});

        // Close the connection with the database
        mDb.close();

    }

    /**
     * Allow to update an item in the "Item" table
     * @param item - The item to update
     */
    public void update(Item item) {

        // Open the connection with the database
        mDb = open();

        // Specify the values which will be updated
        ContentValues values = new ContentValues();
        values.put(ItemDAO.ITEM_NAME, item.getName());
        values.put(ItemDAO.ITEM_QUANTITY_DESIRED, item.getQuantityDesired());
        values.put(ItemDAO.ITEM_QUANTITY_GOT, item.getQuantityGot());
        values.put(ItemDAO.ITEM_NOTE, item.getNote());
        values.put(ItemDAO.ITEM_STATUS, item.getStatus());
        values.put(ItemDAO.ITEM_KEY_LIST, item.getIdList());

        // Update the data in the database
        mDb.update(ItemDAO.ITEM_TABLE_NAME, values, ItemDAO.ITEM_KEY + " = ?", new String[]{String.valueOf(item.getId())});

        // Close the connection with the database
        mDb.close();

    }

    /**
     * Allow to get all item
     * @param listKey - The idList
     * @return - The ArrayList of item
     */
    public ArrayList<Item> get(long listKey) {

        // The return value
        ArrayList<Item> ret = new ArrayList<>();

        // Open the connection with the database
        mDb = open();

        // Get all data of the table
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + ItemDAO.ITEM_TABLE_NAME + " WHERE " + ItemDAO.ITEM_KEY_LIST + " = " + listKey, null);

        // If the table isn't empty
        if(cursor.getCount() > 0) {

            // Browse all data
            while (cursor.moveToNext()) {

                Long id = cursor.getLong(0);
                String name = cursor.getString(1);
                int quantityDesired = cursor.getInt(2);
                int quantityGot = cursor.getInt(3);
                String note = cursor.getString(4);
                int status = cursor.getInt(5);
                long idList = cursor.getLong(6);

                ret.add(new Item(id, name, quantityDesired, quantityGot, note, status, idList));

            }

        }

        // Close the cursor
        cursor.close();

        // Close the connection with the database
        mDb.close();

        return ret;
    }

    /**
     * Allow to get a specific item
     * @param id - The id
     * @return - The item
     */
    public Item getItem(long id) {

        // The return value
        Item ret;

        // Open the connection with the database
        mDb = open();

        // Get all data of a specific item
        Cursor cursor = mDb.rawQuery("SELECT * FROM " + ItemDAO.ITEM_TABLE_NAME + " WHERE " + ItemDAO.ITEM_KEY + " = " + id, null);

        // Go to the head of data
        cursor.moveToFirst();

        // Create the new item with the data
        String name = cursor.getString(1);
        int quantityDesired = cursor.getInt(2);
        int quantityGot = cursor.getInt(3);
        String note = cursor.getString(4);
        int status = cursor.getInt(5);
        long idList = cursor.getLong(6);

        ret = new Item(id, name, quantityDesired, quantityGot, note, status, idList);

        cursor.close();

        // Close the connection with the database
        mDb.close();

        return ret;

    }

    /**
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

    }*/

    public void clear() {

        // Open the connection with the database
        mDb = open();

        // delete all data in the database
        mDb.delete(ProductDAO.PRODUCT_TABLE_NAME, null, null);

        // Close the connection with the database
        mDb.close();

    }

}

