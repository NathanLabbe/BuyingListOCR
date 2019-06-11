package com.example.buyinglistocr.model;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static SharedPrefManager instance;
    private static Context context;

    private static final String SHARED_PREF_NAME = "mysharedpref";
    private static final String KEY_ID = "id";
    private static final String KEY_LOGIN = "login";
    private static final String KEY_MAIL = "mail";


    private SharedPrefManager(Context context) {

        this.context = context;

    }

    public static synchronized SharedPrefManager getInstance(Context context) {

        if(instance == null) {

            instance = new SharedPrefManager(context);

        }

        return instance;

    }

    public boolean userLogin(int id, String login, String mail) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_ID, id);
        editor.putString(KEY_LOGIN, login);
        editor.putString(KEY_MAIL, mail);

        editor.apply();

        return true;

    }

    public boolean isLoggedIn() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if(sharedPreferences.getString(KEY_LOGIN, null) != null) {

            return true;

        } else {

            return false;

        }

    }

    public boolean logout() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();

        return true;

    }

    public String getLogin() {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);

        return sharedPreferences.getString(KEY_LOGIN, null);

    }

}