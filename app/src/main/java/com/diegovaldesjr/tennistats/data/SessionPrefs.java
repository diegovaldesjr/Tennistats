package com.diegovaldesjr.tennistats.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.diegovaldesjr.tennistats.io.response.LoginResponse;

/**
 * Created by diego on 29/12/2017.
 */

public class SessionPrefs {

    private static SessionPrefs INSTANCE;

    public static final String PREFS_NAME = "TENNISTATS_PREFS";
    public static final String PREF_AFFILIATE_ID = "PREF_USER_ID";
    public static final String PREF_AFFILAITE_TOKEN = "PREF_AFFILAITE_TOKEN";

    private final SharedPreferences mPrefs;
    private boolean mIsLoggedIn = false;

    public static SessionPrefs get(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SessionPrefs(context);
        }
        return INSTANCE;
    }

    public SessionPrefs(Context context) {
        mPrefs = context.getApplicationContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        mIsLoggedIn = !TextUtils.isEmpty(mPrefs.getString(PREF_AFFILAITE_TOKEN, null));
    }

    public void saveAffiliate(LoginResponse loginResponse) {
        if (loginResponse != null) {
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putString(PREF_AFFILIATE_ID, loginResponse.getUsername());
            editor.putString(PREF_AFFILAITE_TOKEN, loginResponse.getAccess_token());
            editor.apply();

            mIsLoggedIn = true;
        }
    }

    public void logOut(){
        mIsLoggedIn = false;
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PREF_AFFILIATE_ID, null);
        editor.putString(PREF_AFFILAITE_TOKEN, null);
        editor.apply();
    }

    public boolean isLoggedIn(){
        return mIsLoggedIn;
    }

    public String getUsername(){
        return mPrefs.getString(PREF_AFFILIATE_ID,null);
        //return context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString("PREF_AFFILIATE_ID","");
    }

    public String getToken(){
        return mPrefs.getString(PREF_AFFILAITE_TOKEN,null);
        //return context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).getString("PREF_AFFILIATE_ID","");
    }
}
