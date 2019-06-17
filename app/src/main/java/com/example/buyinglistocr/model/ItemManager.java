package com.example.buyinglistocr.model;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.buyinglistocr.util.RequestHandler;
import com.example.buyinglistocr.util.SharedPreferencesList;
import com.example.buyinglistocr.util.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ItemManager {

    private Context context;

    public ItemManager(Context context) {

        this.context = context;

    }

    public int add(final Item item, final VolleyCallback volleyCallback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://51.83.70.93/android/BuyingListOCR/ItemIndex.php",

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

                params.put("tag","add");
                params.put("name", item.getName());
                params.put("quantityDesired", Integer.toString(item.getQuantityDesired()));
                params.put("quantityGot", Integer.toString(item.getQuantityGot()));
                params.put("status", Integer.toString(item.getStatus()));
                params.put("idList", Integer.toString(item.getIdList()));

                return params;

            }

        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);

        return 0;

    }

    public void getAll(final VolleyCallback volleyCallback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://51.83.70.93/android/BuyingListOCR/ItemIndex.php",

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
                params.put("idList", Integer.toString(SharedPreferencesList.getInstance(context).getId()));

                return params;

            }

        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);

    }

    public void update(final Item item) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://51.83.70.93/android/BuyingListOCR/ItemIndex.php",

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getBoolean("error")) {

                                Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_LONG).show();

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

                params.put("tag","update");
                params.put("id", Integer.toString(item.getId()));
                params.put("name", item.getName());
                params.put("quantityDesired", Integer.toString(item.getQuantityDesired()));
                params.put("quantityGot", Integer.toString(item.getQuantityGot()));
                params.put("status", Integer.toString(item.getStatus()));
                params.put("idList", Integer.toString(item.getIdList()));

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
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("tag","delete");
                params.put("id", ""+id);
                return params;

            }

        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);



    }

    public ArrayList<Item> get(long id) { return new ArrayList<>(); }

    public Item getItem(long id) { return null; }

}

