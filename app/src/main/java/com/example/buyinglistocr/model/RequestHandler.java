package com.example.buyinglistocr.model;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestHandler {

    private static RequestHandler instance;
    private RequestQueue requestQueue;
    private static Context context;

    private RequestHandler(Context context) {

        this.context = context;
        requestQueue = getRequestQueue();

    }

    public static synchronized RequestHandler getInstance(Context context) {

        if(instance == null) {

            instance = new RequestHandler(context);

        }

        return instance;

    }

    public RequestQueue getRequestQueue() {

        if(requestQueue == null) {

            requestQueue = Volley.newRequestQueue(context.getApplicationContext());

        }

        return  requestQueue;

    }

    public <T> void addToRequestQueue(Request<T> request) {

        getRequestQueue().add(request);

    }

}
