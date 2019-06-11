package com.example.buyinglistocr.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Allow to interact with the "Product" table
 */
public class ItemDAO {

    private Context context;

    /**
     * The constructor of the class
     * @param pContext - The context
     */
    public ItemDAO(Context context) {

        this.context = context;

    }

    /**
     * Allow to add an item in the "Item" table
     * @param item - The item
     * @return - The id of the item
     */
    public long add(Item item) {

       return 0;

    }

    /**
     * Allow to delete an item in the "Item" table
     * @param id - The id of the item
     */
    public void delete(long id) {



    }

    /**
     * Allow to update an item in the "Item" table
     * @param item - The item to update
     */
    public void update(Item item) {



    }

    /**
     * Allow to get all item
     * @param listKey - The idList
     * @return - The ArrayList of item
     */
    public ArrayList<Item> get(long listKey) {



        return null;
    }

    /**
     * Allow to get a specific item
     * @param id - The id
     * @return - The item
     */
    public Item getItem(long id) {

       return null;

    }

    /**
     *
     */
    public void clear() {

        
    }

}

