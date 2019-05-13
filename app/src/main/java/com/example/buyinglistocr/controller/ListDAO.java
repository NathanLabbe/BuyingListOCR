package com.example.buyinglistocr.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.buyinglistocr.model.List;

import java.util.ArrayList;

/**
 * Allow to interact with the "List" table
 */
public class ListDAO extends DAOBase {

    // "List" table
    public static final String LIST_TABLE_NAME = "List";

    // Attributes of "List" table
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
     */
    public void add(List list) {

        // Open the connection with the database
        mDb = open();

        // Specify the values which wil be inserted
        ContentValues value = new ContentValues();
        value.put(ListDAO.LIST_NAME, list.getName());
        value.put(ListDAO.LIST_SPENT, list.getSpent());

        // Insert the data in the database
        mDb.insert(ListDAO.LIST_TABLE_NAME, null, value);

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

        Cursor cursor = mDb.rawQuery("select " + LIST_NAME + " from " + LIST_TABLE_NAME, null);

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

