package com.example.buyinglistocr.model;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.buyinglistocr.util.RequestHandler;
import com.example.buyinglistocr.util.SharedPreferencesUser;
import com.example.buyinglistocr.util.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ListManager {

    private Context context;

    public ListManager(Context context) {

        this.context = context;

    }

    public void add(final List list, final VolleyCallback volleyCallback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://51.83.70.93/android/BuyingListOCR/ListIndex.php",

            new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    volleyCallback.onSuccess(response);

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
                params.put("tag", "add");
                params.put("name", list.getName());
                params.put("spent", list.getSpent()+"");
                params.put("status" , list.getStatus()+"");
                params.put("idUser", list.getIdUser()+"");
                return params;

            }

        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);

    }

    public void delete(final long id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://51.83.70.93/android/BuyingListOCR/ListIndex.php",

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

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
                params.put("tag", "delete");
                params.put("id", id+"");
                return params;

            }

        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);

    }

    public void update(final List list) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://51.83.70.93/android/BuyingListOCR/ListIndex.php",

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

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
                params.put("tag", "update");
                params.put("name", list.getName());
                params.put("spent", list.getSpent()+"");
                params.put("status" , list.getStatus()+"");
                params.put("idUser", list.getIdUser()+"");
                return params;

            }

        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);


    }

    public void get(final int id, final VolleyCallback volleyCallback){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://51.83.70.93/android/BuyingListOCR/ListIndex.php",

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        volleyCallback.onSuccess(response);

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

                params.put("tag", "get");
                params.put("id", Integer.toString(id));

                return params;

            }

        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);

    }

    public void getAll(final VolleyCallback volleyCallback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://51.83.70.93/android/BuyingListOCR/ListIndex.php",

            new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    volleyCallback.onSuccess(response);

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

                params.put("tag", "getAll");
                params.put("idUser", Integer.toString(SharedPreferencesUser.getInstance(context).getId()));

                return params;

            }

        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);

    }

}