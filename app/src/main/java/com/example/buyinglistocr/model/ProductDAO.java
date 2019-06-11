package com.example.buyinglistocr.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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


    public long add(final Product product) {



        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://51.83.70.93/android/BuyingListOCR/ProductIndex.php",

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
                params.put("tag", "add");
                params.put("name", product.getName());
                params.put("idShop", product.getIdShop()+"");

                return params;

            }

        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);

        return 0;

    }


    public Product getProduct(long id) {

       return null;

    }
    public ArrayList<Product> getAll(final long shopKey) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://51.83.70.93/android/BuyingListOCR/ProductIndex.php",

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
                params.put("tag", "getAll");
                params.put("idShop",shopKey+"");

                return params;

            }

        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);

        return null;
    }

    public void clear() {
    }

}