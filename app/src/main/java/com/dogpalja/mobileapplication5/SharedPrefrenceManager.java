package com.dogpalja.mobileapplication5;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefrenceManager {

    private static final String FILENAME = "MOBILEAPP5LOGIN";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String IMAGE = "image";
    private static final String ID = "id";

    private static Context mContext;
    private static SharedPrefrenceManager mSharedPrefrenceManager;

    private SharedPrefrenceManager(Context context) {
        this.mContext = context;
    }

    public static synchronized SharedPrefrenceManager getInstance(Context context){
        if(mSharedPrefrenceManager == null){
            mSharedPrefrenceManager = new SharedPrefrenceManager(context);
        }

        return mSharedPrefrenceManager;
    }

    public void updateProfileImage(String imageUrl){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IMAGE,imageUrl);
        editor.apply();

    }

    public void updateEmail(String email){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL,email);
        editor.apply();

    }

    public void storeUserData(User user){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME, user.getUsername());
        editor.putString(EMAIL, user.getEmail());
        editor.putString(IMAGE,user.getImage());
        editor.putInt(ID, user.getId());
        editor.apply();
    }


    public boolean isUserLoggeddIn(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);

        //사용자가 로그인 돼있음
        if(sharedPreferences.getString(USERNAME, null) != null){
            return true;
        }

        return false;
    }

    public void logUserOut(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

    }

    public User getUserData() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        User user = new User(sharedPreferences.getInt(ID,-1),sharedPreferences.getString(EMAIL,null)
                ,sharedPreferences.getString(USERNAME,null),sharedPreferences.getString(IMAGE,null));

        return user;
    }

}
