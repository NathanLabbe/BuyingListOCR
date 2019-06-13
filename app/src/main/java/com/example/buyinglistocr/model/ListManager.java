package com.example.buyinglistocr.model;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Allow to interact with the "List" table
 */
public class ListManager {

    private Context context;

    private List list;

    private ArrayList<List> lists;

    /**
     * The constructor of the class
     * @param context - The context
     */
    public ListManager(Context context) {

        this.context = context;

        this.list = new List(0, null, 0, 0, 0);



    }

    /**
     * Allow to add a list in the "List" table
     * @param list - The list
     * @return - The id of the list
     */
    public long add(final List list) {

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
                params.put("tag", "add");
                params.put("name", list.getName());
                params.put("spent", list.getSpent()+"");
                params.put("status" , list.getStatus()+"");
                params.put("idUser", list.getIdUser()+"");
                return params;

            }

        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);

        return 0;
    }

    /**
     * Allow to delete a list in the "List" table
     * @param id - The id of the list
     */
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
                params.put("tag", "delete");
                params.put("id", id+"");
                return params;

            }

        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);

    }

    /**
     * Allow to update a list in the "List" table
     * @param list - The list to update
     */
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
            protected Map<String, String> getParams() throws AuthFailureError {

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

    /**
     * Allow to get all list
     * @return - The ArrayList of list
     */
    public ArrayList<List> get() {



        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://51.83.70.93/android/BuyingListOCR/ListIndex.php",

            new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    try {

                        JSONObject jsonObject = new JSONObject(response);

                        JSONArray jsonArray = jsonObject.getJSONArray("lists");

                        for (int i = 0 ; i < jsonArray.length() ; i++) {

                            JSONObject jsonObjectList = jsonArray.getJSONObject(i);

                            List list1 = new List(jsonObjectList.getLong("id"), jsonObjectList.getString("name"), jsonObjectList.getDouble("spent"), jsonObjectList.getInt("status"), jsonObjectList.getLong("idUser"));

                            getLists().add(list1);

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
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("tag", "get");
                params.put("idUser", Integer.toString(SharedPrefManager.getInstance(context).getId()));
                return params;

            }

        };

        this.lists = new ArrayList<>();
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);

        Toast.makeText(context, Long.toString(lists.get(0).getId()), Toast.LENGTH_LONG).show();
        Toast.makeText(context, lists.get(0).getName(), Toast.LENGTH_LONG).show();
        Toast.makeText(context, Double.toString(lists.get(0).getSpent()), Toast.LENGTH_LONG).show();
        Toast.makeText(context, Integer.toString(lists.get(0).getStatus()), Toast.LENGTH_LONG).show();
        Toast.makeText(context, Long.toString(lists.get(0).getIdUser()), Toast.LENGTH_LONG).show();

        return lists;

    }

    /**
     * Allow to get a specific list
     * @param id - the id of the list
     * @return - The list
     */
    public List getList(final long id){
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
                params.put("tag", "getList");
                params.put("id", id+"");
                return params;

            }

        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);



        return null;

    }

    public List getList() {

        return list;

    }

    public void setList(List list) {

        this.list = list;

    }

    public ArrayList<List> getLists() {

        return lists;

    }

    public void setLists(ArrayList<List> lists) {

        this.lists = lists;

    }
}