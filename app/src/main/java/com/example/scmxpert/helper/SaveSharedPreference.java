package com.example.scmxpert.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.scmxpert.model.ShipmentDetail;
import com.example.scmxpert.model.Shippment;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.example.scmxpert.helper.PreferencesUtility.ID_SHIPMENT_DETAILS;
import static com.example.scmxpert.helper.PreferencesUtility.LOGGED_IN_PREF;
import static com.example.scmxpert.helper.PreferencesUtility.PREF_NAME;

public class SaveSharedPreference {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;
    public  SaveSharedPreference(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Set the Login Status
     * @param context
     * @param loggedIn
     */
    public static void setLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.apply();
    }

    /**
     * Get the Login Status
     * @param context
     * @return boolean: login status
     */
    public static boolean getLoggedStatus(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
    }

    public  void setShipmentDetails(Context context, ArrayList<ShipmentDetail>  shipmentDetail){
        Gson gson = new Gson();
        String shipment_detail = gson.toJson(shipmentDetail);
        editor.putString(ID_SHIPMENT_DETAILS, shipment_detail);
        editor.apply();
    }

    public String getShipmentDetails(Context context){
        String shipment = pref.getString(ID_SHIPMENT_DETAILS, null);
        return shipment;
    }

    public void clearShipmentDetails() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

    }

}
