package com.example.buyinglistocr.model;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesList {

    private static SharedPreferencesList instance;
    private static Context context;

    private static final String SHARED_PREF_NAME = "sharedPreferencesList";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SPENT = "spent";
    private static final String KEY_STATUS = "status";
    private static final String KEY_ID_USER = "idUser";

    private SharedPreferencesList(Context context) {

        this.context = context;

    }

    public static synchronized SharedPreferencesList getInstance(Context context) {

        if(instance == null) {

            instance = new SharedPreferencesList(context);

        }

        return instance;

    }

    public boolean setList(List list) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_ID, list.getId());
        editor.putString(KEY_NAME, list.getName());
        editor.putFloat(KEY_SPENT, (float) list.getSpent());
        editor.putInt(KEY_STATUS, list.getStatus());
        editor.putInt(KEY_ID_USER, list.getIdUser());

        editor.apply();

        return true;

    }

    public int getId() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);

        return sharedPreferences.getInt(KEY_ID, 0);

    }

    public List getList() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);

        int id = sharedPreferences.getInt(KEY_ID, 0);
        String name = sharedPreferences.getString(KEY_NAME, null);
        double spent = sharedPreferences.getFloat(KEY_SPENT, 0);
        int status = sharedPreferences.getInt(KEY_STATUS, 0);
        int idUser = sharedPreferences.getInt(KEY_ID_USER, 0);

        List list = new List(id, name, spent, status, idUser);

        return list;

    }

    public boolean isDeleted() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);

        if(sharedPreferences.getString(KEY_NAME, null) == null) {

            return true;

        } else {

            return false;

        }

    }

}
