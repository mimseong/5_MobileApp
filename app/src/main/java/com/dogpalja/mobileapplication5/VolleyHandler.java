package com.dogpalja.mobileapplication5;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyHandler {

    private RequestQueue mRequestQueue;
    private static Context mContext;
    private static VolleyHandler mVolleyHandler;

    private VolleyHandler(Context mContext) {
        this.mContext = mContext;
        this.mRequestQueue = getRequestQueue();
    }

    public static synchronized VolleyHandler getInstance(Context context){
        if(mVolleyHandler == null){
            mVolleyHandler = new VolleyHandler(context);
        }

        return mVolleyHandler;
    }


    public RequestQueue getRequestQueue(){

        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());

        }
        return mRequestQueue;
    }

    public <T> void addRequestToQueue(Request<T> req){
        getRequestQueue().add(req);
    }
}
