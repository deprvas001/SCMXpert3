package com.development.scmxpert.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.development.scmxpert.views.login.LoginScreen;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE =0;
    private static final String PREF_NAME = "SCMXpertPref";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String USER_NAME ="user_name";
    public static final String PARTNER_NAME = "partner_name";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String CUSTOMER_NAME = "customer_name";
    public static final String TOKEN = "token";
    public static final String FROM="from";
    public static final String TO="to";
    public static final String GOODS="good";
    public static final String DATE="date";
    public static final String SHIP_NUM="shipment_number";
    public static final String REFERENCE="reference";
    public static final String DEVICE="device";
    public static final String DEPT="dept";



    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create Login Session
    public void createLoginSession(String customer_name,String user_name,String partner,String token,String customer_id){
        editor.putBoolean(IS_LOGIN,true);
        editor.putString(CUSTOMER_NAME,customer_name);
        editor.putString(USER_NAME,user_name);
        editor.putString(PARTNER_NAME,partner);
        editor.putString(TOKEN,token);
        editor.putString(CUSTOMER_ID,customer_id);
        editor.commit();
    }

    public void setFilter(String from,String to,String goods,String ship_date,String ship_number,String reference,String device,String department){
      //  editor.putBoolean(IS_LOGIN,true);
        editor.putString(FROM,from);
        editor.putString(TO,to);
        editor.putString(GOODS,goods);
        editor.putString(DATE,ship_date);
        editor.putString(SHIP_NUM,ship_number);
        editor.putString(REFERENCE,reference);
        editor.putString(DEVICE,device);
        editor.putString(DEPT,department);
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */

    public void checkLogin(){
        //Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginScreen.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }
    }

    /**
     * Get stored session data
     * */

    public HashMap<String,String> getUserDetails(){
        HashMap<String,String> user = new HashMap<String,String>();
        //user name
        user.put(CUSTOMER_NAME,pref.getString(CUSTOMER_NAME,null));
        user.put(USER_NAME,pref.getString(USER_NAME,null));
        user.put(PARTNER_NAME,pref.getString(PARTNER_NAME,null));
        user.put(TOKEN,pref.getString(TOKEN,null));
        user.put(CUSTOMER_ID,pref.getString(CUSTOMER_ID,null));

        return  user;
    }


    public HashMap<String,String> getFilter(){

        HashMap<String,String> user = new HashMap<String,String>();
        //user name
        user.put(FROM,pref.getString(FROM,null));
        user.put(TO,pref.getString(TO,null));
        user.put(GOODS,pref.getString(GOODS,null));
        user.put(DATE,pref.getString(DATE,null));
        user.put(SHIP_NUM,pref.getString(DATE,null));
        user.put(REFERENCE,pref.getString(DATE,null));
        user.put(DEVICE,pref.getString(DATE,null));
        user.put(DEPT,pref.getString(DATE,null));


        return  user;
    }

    /**
     * Clear session details
     * */

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        //After logout redirect user to login screen.
        Intent intent = new Intent(_context,LoginScreen.class);
        //Closing all the activities
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //Add new flag to start new activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        _context.startActivity(intent);

    }


    //Quick check for login
    public boolean isLoggedIn(){
        return  pref.getBoolean(IS_LOGIN,false);
    }
}
