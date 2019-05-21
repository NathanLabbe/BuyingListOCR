package com.example.buyinglistocr.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Implement the method which allow the connection with the database
 */
public abstract class DAOBase {

    // The attribute of the database
    protected final static int VERSION = 1;
    protected final static String NAME = "database.db";

    // The database
    protected SQLiteDatabase mDb = null;

    // The handler of the database
    protected DatabaseHandler mHandler;

    /**
     * The constructor of the class
     * @param pContext - The context
     */
    public DAOBase(Context pContext) {

        this.mHandler = new DatabaseHandler(pContext, NAME, null, VERSION);

    }

    /**
     * Allow to open the connection with the database
     * @return - The database
     */
    public SQLiteDatabase open() {

        mDb = mHandler.getWritableDatabase();

        return mDb;

    }

    /**
     * Allow to close the connection with the database
     */
    public void close() {

        mDb.close();

    }

    /**
     * Allow to return the database
     * @return - The database
     */
    public SQLiteDatabase getDb() {

        return mDb;

    }

}