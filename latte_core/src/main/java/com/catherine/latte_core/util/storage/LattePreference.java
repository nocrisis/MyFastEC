package com.catherine.latte_core.util.storage;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.catherine.latte_core.app.Latte;

/*Activity.getPreferences(int mode)生成Activity名.xml用于Activity内部存储
PreferenceManager.getDefaultSharedPreference(Context)生成 包名_preference.xml
Context.getSharedPreferences(String name,int mode)生成name.xml*/
public class LattePreference {
    private static final SharedPreferences PREFERENCE = PreferenceManager
            .getDefaultSharedPreferences(Latte.getApplicationContext());
    private static final String APP_PREFERENCE_KEY = "profile";

    public static SharedPreferences getAppPreference() {
        return PREFERENCE;
    }

    public static void setAppProfile(String val) {
        getAppPreference()
                .edit()
                .putString(APP_PREFERENCE_KEY, val)
                .apply();
    }

    public static String getAppProfile() {
        return getAppPreference().getString(APP_PREFERENCE_KEY, null);
    }

    public static JSONObject getAppProfileJson() {
        final String profile = getAppProfile();
        return JSON.parseObject(profile);
    }

    public static void removeAppProfile() {
        getAppPreference().edit().remove(APP_PREFERENCE_KEY).apply();
    }

    public static void clearAppPreferences() {
        getAppPreference().edit().clear().apply();
    }

    public static void setAppFlag(String key, boolean flag) {
        getAppPreference().edit().putBoolean(key, flag).apply();
    }

    public static boolean getAppFlag(String key) {
        return getAppPreference().getBoolean(key, false);
    }
}
