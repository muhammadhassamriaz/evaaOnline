package com.ciesto.evaafashion.Other;

import android.content.Context;
import android.content.SharedPreferences;

public class ExtraPreferences
{
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;


    private static final String PREF_NAME = "MyDetailsUser";
    private static final String LANGUAGE= "language";
    private static final String CARTCOUNT= "cart";
    private static final String WHISHLISTCOUNT= "wishlistcount";
    private static final String PRODUCTID= "productid";
    public static final String DEVICEKEY = "devicykey";



    public ExtraPreferences(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.commit();
    }

    public void setLanguage(String language){
        editor.putString(LANGUAGE, language);
        editor.commit();
    }

    public String getLanguage(){
        return  pref.getString(LANGUAGE,"");
    }

    public void setCartcount(String cart){
        editor.putString(CARTCOUNT, cart);
        editor.commit();
    }

    public String getCartcount(){
        return  pref.getString(CARTCOUNT,"");
    }

  public void setWhishlistcount(String cart){
        editor.putString(WHISHLISTCOUNT, cart);
        editor.commit();
    }

    public String getWhishlistcount(){
        return  pref.getString(WHISHLISTCOUNT,"");
    }

    public void setProductid(String productid){
        editor.putString(PRODUCTID, productid);
        editor.commit();
    }

    public String getProductid(){
        return  pref.getString(PRODUCTID,"");
    }

    public void setDevicekey(String key) {
        editor.putString(DEVICEKEY, key);
        editor.commit();
    }


    public String getDevicekey() {
        String value = pref.getString(DEVICEKEY, "");
        return value;
    }
}
