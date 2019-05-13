package com.example.buyinglistocr.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Allow the management of the database
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // "List" table
    public static final String LIST_TABLE_NAME = "List";

    // Attributes of "List" table
    public static final String LIST_KEY = "idList";
    public static final String LIST_NAME = "name";
    public static final String LIST_SPENT = "spent";



    // SQL request for the creation of the "List" table
    public static final String LIST_TABLE_CREATE = "CREATE TABLE " + LIST_TABLE_NAME + " (" +
                                                        LIST_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                        LIST_NAME + " TEXT, " +
                                                        LIST_SPENT + " REAL);";

    // SQL request for the update of the "List" table
    public static final String LIST_TABLE_DROP = "DROP TABLE IF EXISTS " + LIST_TABLE_NAME + ";";

    // "Produit" table
    public static final String PRODUIT_TABLE_NAME = "Produit";

    // Attributes of "Produit" table
    public static final String PRODUIT_KEY = "idProduit";
    public static final String PRODUIT_NAME = "name";
    public static final String PRODUIT_QUANTITY_BASE = "quantityBase";
    public static final String PRODUIT_QUANTITY_ACT = "quantityAct";
    public static final String PRODUIT_NOTE = "note";
    public static final String PRODUIT_STATUT = "statut";
    public static final String PRODUIT_KEY_LIST = "idList";

    // SQL request for the creation of the "List" table
    public static final String PRODUIT_TABLE_CREATE = "CREATE TABLE " + PRODUIT_TABLE_NAME + " (" +
                                                        PRODUIT_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                        PRODUIT_NAME + " TEXT, " +
                                                        PRODUIT_QUANTITY_BASE + " INTEGER, " +
                                                        PRODUIT_QUANTITY_ACT + " INTEGER, " +
                                                        PRODUIT_NOTE + " TEXT, " +
                                                        PRODUIT_STATUT + " INTEGER, " +
                                                        PRODUIT_KEY_LIST + " INTEGER);";

    // SQL request for the update of the "List" table
    public static final String PRODUIT_TABLE_DROP = "DROP TABLE IF EXISTS " + PRODUIT_TABLE_NAME + ";";

    /**
     * The constructor of the class
     * @param context - The context
     * @param name - The name of the database
     * @param factory - The factory
     * @param version - The version of the database
     */
    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, name, factory, version);

    }

    /**
     * Allow the creation of the "List" table
     * @param db - The database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(LIST_TABLE_CREATE);
        db.execSQL(PRODUIT_TABLE_CREATE);

    }

    /**
     * Allow the update of the "List" table
     * @param db - The databse
     * @param oldVersion - The number of the old version
     * @param newVersion - The number of the new version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(LIST_TABLE_DROP);
        db.execSQL(PRODUIT_TABLE_DROP);
        onCreate(db);

    }

}