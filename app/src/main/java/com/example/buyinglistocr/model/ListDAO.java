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
 * Allow to interact with the "List" table
 */
public class ListDAO {

    private Context context;

    /**
     * The constructor of the class
     * @param context - The context
     */
    public ListDAO(Context context) {

        this.context = context;

    }

    /**
     * Allow to add a list in the "List" table
     * @param list - The list
     * @return - The id of the list
     */
    public long add(List list) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://51.83.70.93/android/BuyingListOCR/addList.php",

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
                params.put("name", "test4");
                params.put("spent", "81");
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
    public void delete(long id) {


    }

    /**
     * Allow to update a list in the "List" table
     * @param list - The list to update
     */
    public void update(List list) {

        // Open the connection with the database
        mDb = open();

        // Specify the values which will be updated
        ContentValues value = new ContentValues();
        value.put(ListDAO.LIST_NAME, list.getName());
        value.put(ListDAO.LIST_SPENT, list.getSpent());

        // Update the data in the database
        mDb.update(ListDAO.LIST_TABLE_NAME, value, ListDAO.LIST_KEY + " = ?", new String[] { String.valueOf(list.getId()) });

        // Close the connection with the database
        mDb.close();

    }

    /**
     * Allow to get all list
     * @return - The ArrayList of list
     */
    public ArrayList<List> get() {

        // The return value
        ArrayList<List> ret = new ArrayList<>();

        // Open the connection with the database
        mDb = open();

        // Get all data of the table
        Cursor cursor = mDb.rawQuery("select * from " + ListDAO.LIST_TABLE_NAME, null);

        // If the table isn't empty
        if(cursor.getCount() > 0) {

            // Browse all data
            while (cursor.moveToNext()) {

                Long id = cursor.getLong(0);
                String name = cursor.getString(1);
                double spent = cursor.getDouble(2);

                ret.add(new List(id, name, spent));

            }

        }

        // Close the cursor
        cursor.close();

        // Close the connection with the database
        mDb.close();

        return ret;

    }

    /**
     * Allow to get a specific list
     * @param id - the id of the list
     * @return - The list
     */
    public List getList(long id){

        List ret;

        // Open the connection with the database
        mDb = open();

        // Get all data of a specific list
        Cursor cursor = mDb.rawQuery("select * from " + LIST_TABLE_NAME + " where " + LIST_KEY + " = " + id, null);

        // Go to the head of data
        cursor.moveToFirst();

        // Create the new list with the data
        String name = cursor.getString(1);
        double spent = cursor.getDouble(2);

        ret = new List(id, name, spent);

        // Close the cursor
        cursor.close();

        // Close the connection with the database
        mDb.close();

        return ret;

    }

    public void clear() {

        // Open the connection with the database
        mDb = open();

        // delete all data in the database
        mDb.delete(ListDAO.LIST_TABLE_NAME, null, null);

        // Close the connection with the database
        mDb.close();

    }

    /*public void updateSpent(int spent) {
        int res = Integer.parseInt(ListDAO.LIST_SPENT)+ spent;
        mDb = open();

        // Specify the values which will be updated
        ContentValues value = new ContentValues();
        value.put(ListDAO.LIST_SPENT, res);

        // Update the data in the database
        mDb.update(ListDAO.LIST_TABLE_NAME, value, ListDAO.LIST_KEY + " = ?", new String[] { String.valueOf(LIST_KEY) });

        // Close the connection with the database
        mDb.close();


    }*/

}