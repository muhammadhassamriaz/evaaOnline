package com.ciesto.evaafashion.Other;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.ciesto.evaafashion.Activity.LoginActivity;


public class LoginPreferences
{
    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "NeomiFashionUser";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_ID = "userid";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_IMAGE = "image";
    private static final String KEY_NOTIFICATION= "notification";
    private static final String LANGUAGE= "language";


    public LoginPreferences(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.commit();
    }

    public void SetSession(String userId,String name,String email,String mobile,String Image)
    {
        editor.putString(KEY_ID,userId);
        editor.putString(KEY_NAME,name);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_MOBILE,mobile);
        editor.putString(KEY_IMAGE,Image);
        editor.commit();

    }
    public boolean isLoggIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }


    public void setIsLogin(Boolean isLogin) {
        editor.putBoolean(IS_LOGIN, isLogin);
    }

    public String getName() {
        String value = pref.getString(KEY_NAME, null);
        return value;
    }

    public String getMobile() {
        String value = pref.getString(KEY_MOBILE, null);
        return value;
    }

    public String getUserID() {
        String value = pref.getString(KEY_ID, null);
        return value;
    }

    public String getEmail() {
        String value = pref.getString(KEY_EMAIL, null);
        return value;
    }
    public String getKeyImage() {
        String value = pref.getString(KEY_IMAGE, null);
        return value;
    }

    public void logoutUser() {

        editor.clear();
        editor.commit();
        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);



    }
    public void setNotificationStatus(Boolean notification){
        editor.putBoolean(KEY_NOTIFICATION, notification);
        editor.commit();
    }

    public Boolean getNotificationStatus(){
        return pref.getBoolean(KEY_NOTIFICATION,true);
    }

}
