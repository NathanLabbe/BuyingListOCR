package com.example.buyinglistocr.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.buyinglistocr.controller.ListsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private Context context;

    public UserManager(Context context) {

        this.context = context;

    }

    public void login(final User user) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://51.83.70.93/android/BuyingListOCR/UserIndex.php",

            new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    try {

                        JSONObject jsonObject = new JSONObject(response);

                        if(jsonObject.getBoolean("error")) {

                            Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                        } else {

                            SharedPreferencesUser.getInstance(context).userLogin(jsonObject.getInt("id"), jsonObject.getString("login"), jsonObject.getString("mail"));

                            Intent intent = new Intent(context, ListsActivity.class);
                            context.startActivity(intent);

                            ((Activity)context).finish();

                        }

                    } catch(JSONException e) {

                        e.printStackTrace();

                    }

                }

            },

            new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();

                }

            }

        ) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                params.put("tag", "login");
                params.put("login", user.getLogin());
                params.put("password", user.getPassword());

                return params;

            }

        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);

    }

    public void register(final User user, final String confirmPassword) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://51.83.70.93/android/BuyingListOCR/UserIndex.php",

            new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    try {

                        JSONObject jsonObject = new JSONObject(response);

                        if(jsonObject.getBoolean("error")) {

                            Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                        } else {

                            SharedPreferencesUser.getInstance(context).userLogin(jsonObject.getInt("id"), jsonObject.getString("login"), jsonObject.getString("mail"));

                            Intent intent = new Intent(context, ListsActivity.class);
                            context.startActivity(intent);

                            ((Activity)context).finish();

                        }

                    } catch(JSONException e) {

                        e.printStackTrace();

                    }

                }

            },

            new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();

                }

            }

        ) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                params.put("tag", "register");
                params.put("login", user.getLogin());
                params.put("password", user.getPassword());
                params.put("confirmPassword", confirmPassword);
                params.put("mail", user.getMail());

                return params;

            }

        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);

    }

}
