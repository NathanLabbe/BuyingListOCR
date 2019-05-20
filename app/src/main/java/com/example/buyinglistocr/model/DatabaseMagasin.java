package com.example.buyinglistocr.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by Chi Linh on 2019--2019-05-15--11:47.
 */
public class DatabaseMagasin extends SQLiteOpenHelper {


        public static final String LIST_MAGASIN = "Magasin";

        public static final String LIST_KEY = "idMagasin";
        public static final String LIST_NAME = "nameMagasin";


        public static final String LIST_MAGASIN_CREATE = "CREATE TABLE " + LIST_MAGASIN + " (" +
                LIST_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LIST_NAME + " TEXT);";

        public static final String LIST_MAGASIN_DROP = "DROP TABLE IF EXISTS " + LIST_MAGASIN + ";";



        public static final String PRODUCT_OF_MAGASIN = "ProductOfMagasin";


        public static final String PRODUCTOM_KEY = "idProduitOM";
        public static final String PRODUCTOM_NAME = "nameOM";
        public static final String PRODUCTOM_QUANTITY = "quantity";
        public static final String PRODUCTOM_PRIX = "prix";
        public static final String PRODUCTOM_KEY_MAGASIN = "idMagasin";


        public static final String PRODUCTOM_CREATE = "CREATE TABLE " + PRODUCT_OF_MAGASIN + " (" +
                PRODUCTOM_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PRODUCTOM_NAME + " TEXT, " +
                PRODUCTOM_QUANTITY + " INTEGER, " +
                PRODUCTOM_PRIX + " INTEGER, " +
                PRODUCTOM_KEY_MAGASIN + " INTEGER);";

        public static final String PRODUCTOM_MAGASIN_DROP = "DROP TABLE IF EXISTS " + PRODUCT_OF_MAGASIN+ ";";


        public DatabaseMagasin(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

            super(context, name, factory, version);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(LIST_MAGASIN_CREATE);
            db.execSQL(PRODUCTOM_CREATE);

        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL(LIST_MAGASIN_DROP);
            db.execSQL(PRODUCTOM_MAGASIN_DROP);
            onCreate(db);

        }

    }
