package com.example.buyinglistocr.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;

/**
 * Allow to interact with the "List" table
 */
public class ListDAO extends DAOBase {

    // "List" table
    public static final String LIST_TABLE_NAME = "List";

    // Attributes of "List" table
    public static final String LIST_KEY = "id";
    public static final String LIST_NAME = "name";
    public static final String LIST_SPENT = "spent";

    /**
     * The constructor of the class
     * @param pContext - The context
     */
    public ListDAO(Context pContext) {

        super(pContext);

    }

    /**
     * Allow to add a list in the "List" table
     * @param list - The list
     * @return - The id of the list
     */
    public long add(List list) {

        // The id of the list
        long ret;

        // Open the connection with the database
        mDb = open();

        // Specify the values which will be inserted
        ContentValues value = new ContentValues();
        value.put(ListDAO.LIST_NAME, list.getName());
        value.put(ListDAO.LIST_SPENT, list.getSpent());

        // Insert the data in the database
        ret = mDb.insert(ListDAO.LIST_TABLE_NAME, null, value);

        // Close the connection with the database
        mDb.close();

        return ret;

    }

    /**
     * Allow to delete a list in the "List" table
     * @param id - The id of the list
     */
    public void delete(long id) {

        // Open the connection with the database
        mDb = open();

        // Delete the data in the database
        mDb.delete(ListDAO.LIST_TABLE_NAME, ListDAO.LIST_KEY + " = ?", new String[]{String.valueOf(id)} );

        // Close the connection with the database
        mDb.close();

    }

    /**
     * Allow to update a list in the "List" table
     * @param list - The list to update
     */
    public void update(List list) {

        // Open the connection with the database
        mDb = open();

        // Specify the values which will be updated
        ContentValues value = new ContentValues();
        value.put(ListDAO.LIST_NAME, list.getName());
        value.put(ListDAO.LIST_SPENT, list.getSpent());

        // Update the data in the database
        mDb.update(ListDAO.LIST_TABLE_NAME, value, ListDAO.LIST_KEY + " = ?", new String[] { String.valueOf(list.getId()) });

        // Close the connection with the database
        mDb.close();

    }

    /**
     * Allow to get all list
     * @return - The ArrayList of list
     */
    public ArrayList<List> get() {

        // The return value
        ArrayList<List> ret = new ArrayList<>();

        // Open the connection with the database
        mDb = open();

        // Get all data of the table
        Cursor cursor = mDb.rawQuery("select * from " + ListDAO.LIST_TABLE_NAME, null);

        // If the table isn't empty
        if(cursor.getCount() > 0) {

            // Browse all data
            while (cursor.moveToNext()) {

                Long id = cursor.getLong(0);
                String name = cursor.getString(1);
                double spent = cursor.getDouble(2);

                ret.add(new List(id, name, spent));

            }

        }

        // Close the cursor
        cursor.close();

        // Close the connection with the database
        mDb.close();

        return ret;

    }

    /**
     * Allow to get a specific list
     * @param id - the id of the list
     * @return - The list
     */
    public List getList(long id){

        List ret;

        // Open the connection with the database
        mDb = open();

        // Get all data of a specific list
        Cursor cursor = mDb.rawQuery("select * from " + LIST_TABLE_NAME + " where " + LIST_KEY + " = " + id, null);

        // Go to the head of data
        cursor.moveToFirst();

        // Create the new list with the data
        String name = cursor.getString(1);
        double spent = cursor.getDouble(2);

        ret = new List(id, name, spent);

        // Close the cursor
        cursor.close();

        // Close the connection with the database
        mDb.close();

        return ret;

    }

    public void clear() {

        // Open the connection with the database
        mDb = open();

        // delete all data in the database
        mDb.delete(ListDAO.LIST_TABLE_NAME, null, null);

        // Close the connection with the database
        mDb.close();

    }

    /*public void updateSpent(int spent) {
        int res = Integer.parseInt(ListDAO.LIST_SPENT)+ spent;
        mDb = open();

        // Specify the values which will be updated
        ContentValues value = new ContentValues();
        value.put(ListDAO.LIST_SPENT, res);

        // Update the data in the database
        mDb.update(ListDAO.LIST_TABLE_NAME, value, ListDAO.LIST_KEY + " = ?", new String[] { String.valueOf(LIST_KEY) });

        // Close the connection with the database
        mDb.close();


    }*/

}