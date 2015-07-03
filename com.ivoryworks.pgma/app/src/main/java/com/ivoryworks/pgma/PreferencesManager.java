package com.ivoryworks.pgma;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yama on 15/07/03.
 */
public class PreferencesManager {
    private SharedPreferences mPreferences;

    public static PreferencesManager newInstance(Context context) {
        return new PreferencesManager(context);
    }

    public PreferencesManager(Context context) {
        mPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public void setString(String name, String str) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(name, str);
        editor.apply();
    }

    public String getString(String name) {
        return mPreferences.getString(name, null);
    }

    public void remove(String name) {
        mPreferences.edit().remove(name).apply();
    }
}
