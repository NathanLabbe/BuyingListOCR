package com.example.buyinglistocr.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesList {

    private static SharedPreferencesList instance;
    private static Context context;

    private static final String SHARED_PREF_NAME = "sharedPreferencesList";
    private static final String KEY_ID = "id";

    private SharedPreferencesList(Context context) {

        this.context = context;

    }

    public static synchronized SharedPreferencesList getInstance(Context context) {

        if(instance == null) {

            instance = new SharedPreferencesList(context);

        }

        return instance;

    }

    public boolean listChoose(int id) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_ID, id);

        editor.apply();

        return true;

    }

    public int getId() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);

        return sharedPreferences.getInt(KEY_ID, 0);

    }

}
