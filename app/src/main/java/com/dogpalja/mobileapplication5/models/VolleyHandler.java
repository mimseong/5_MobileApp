package com.dogpalja.mobileapplication5.models;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyHandler {

    private RequestQueue mrequestQueue;
    private static Context mcontext;
    private static VolleyHandler mvolleyHandler;

    private VolleyHandler(Context mcontext) {
        this.mcontext = mcontext;
        this.mrequestQueue = getRequestQueue();
    }

    public static synchronized VolleyHandler getInstance(Context context){
        if(mvolleyHandler == null){
            mvolleyHandler = new VolleyHandler(context);
        }

        return mvolleyHandler;
    }


    public RequestQueue getRequestQueue(){

        if(mrequestQueue == null){
            mrequestQueue = Volley.newRequestQueue(mcontext.getApplicationContext());

        }
        return mrequestQueue;
    }

    public <T> void addRequestToQueue(Request<T> req){
        getRequestQueue().add(req);
    }
}
