package ocs.com.dr_tips.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Randa on 3/18/2018.
 */

public class PreferenceHelper {
    private Context context;
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    public PreferenceHelper(Context context) {
        this.context = context;
    }

    public void setEmail(String email) {
        set(EMAIL, email);
    }

    public String getEmail() {
        return get(EMAIL, "");
    }


    public void setPassword(String password) {
        set(PASSWORD, password);
    }

    public String getPassword() {
        return get(PASSWORD, "");
    }

    private void set(String key, String value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(key, value);
        editor.commit();
    }

    private void set(String key, boolean value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private boolean get(String key, boolean defaultValue) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getBoolean(key, defaultValue);
    }

    private String get(String key, String defaultValue) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getString(key, defaultValue);
    }


    private void set(String key, int value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    private int get(String key, int defaultValue) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getInt(key, defaultValue);
    }
}
