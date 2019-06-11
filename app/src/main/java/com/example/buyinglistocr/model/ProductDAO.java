package com.example.buyinglistocr.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Allow to interact with the "Item" table
 */
public class ProductDAO {

    // "Product" table
    private Context context;

    /**
     * The constructor of the class
     * @param context - The context
     */
    public ProductDAO(Context context) {

       this.context = context;

    }


    public long add(Product product) {



        return 0;

    }


    public Product getProduct(long id) {

       return null;

    }
    public ArrayList<Product> getAll(long shopKey) {

       return null;
    }

    public void clear() {
    }

}