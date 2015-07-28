package com.xuemooc.edxapp.module.prefs;

import android.content.Context;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xuemooc.edxapp.model.api.AuthResponse;

/**
 * Created by hackeris on 15/7/28.
 */
public class PrefManager {

    private Context mContext;
    private String mPrefName;

    public PrefManager(Context context, String prefName){

        this.mContext = context;
        this.mPrefName = prefName;
    }

    /**
     * Puts given key-value pair to the Shared Preferences.
     * @param key
     * @param value - String
     */
    public void put(String key, String value) {
        Editor edit = mContext.getSharedPreferences(mPrefName, Context.MODE_PRIVATE).edit();
        edit.putString(key, value).commit();
    }

    /**
     * Puts given key-value pair to the Shared Preferences.
     * @param key
     * @param value - boolean
     */
    public void put(String key, boolean value) {
        Editor edit = mContext.getSharedPreferences(mPrefName, Context.MODE_PRIVATE).edit();
        edit.putBoolean(key, value).commit();
    }

    /**
     * Puts given key-value pair to the Shared Preferences.
     * @param key
     * @param value - long
     */
    public void put(String key, long value) {
        Editor edit = mContext.getSharedPreferences(mPrefName, Context.MODE_PRIVATE).edit();
        edit.putLong(key, value).commit();
    }

    /**
     * Puts given key-value pair to the Shared Preferences.
     * @param key
     * @param value - float
     */
    public void put(String key, float value) {
        Editor edit = mContext.getSharedPreferences(mPrefName, Context.MODE_PRIVATE).edit();
        edit.putFloat(key, value).commit();
    }

    /**
     * Returns String value for the given key, null if no value is found.
     * @param key
     * @return String
     */
    public String getString(String key) {
        if(mContext!=null){
            return mContext.getSharedPreferences(mPrefName, Context.MODE_PRIVATE)
                    .getString(key, null);
        }
        return null;
    }


    /**
     * Returns boolean value for the given key, can set default value as well.
     * @param key,default value
     * @return boolean
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        return mContext.getSharedPreferences(mPrefName, Context.MODE_PRIVATE)
                .getBoolean(key, defaultValue);
    }

    /**
     * Returns long value for the given key, -1 if no value is found.
     * @param key
     * @return long
     */
    public long getLong(String key) {
        return mContext.getSharedPreferences(mPrefName, Context.MODE_PRIVATE)
                .getLong(key, -1);
    }

    /**
     * Returns float value for the given key, -1 if no value is found.
     * @param key
     * @return float
     */
    public float getFloat(String key) {
        return mContext.getSharedPreferences(mPrefName, Context.MODE_PRIVATE)
                .getFloat(key, -1);
    }

    /**
     * Returns float value for the given key, defaultValue if no value is found.
     * @param key
     * @param defaultValue
     * @return float
     */
    public float getFloat(String key, float defaultValue) {
        return mContext.getSharedPreferences(mPrefName, Context.MODE_PRIVATE)
                .getFloat(key, defaultValue);
    }

    /**
     * Returns current user's profile from the preferences.
     * @return
     */
    public AuthResponse getCurrentAuth() {
        String json = getString(PrefManager.Key.AUTH_JSON);
        if (json == null) {
            return null;
        }

        Gson gson = new GsonBuilder().create();
        AuthResponse res = gson.fromJson(json, AuthResponse.class);

        return res;
    }

    /**
     * Clears auth token info and current profile information from preferences.
     */
    public void clearAuth() {
        put(PrefManager.Key.PROFILE_JSON, null);
        put(PrefManager.Key.AUTH_JSON, null);
    }



    /**
     * Contains preference name constants.
     *
     */
    public static final class Pref {
        public static final String LOGIN = "pref_login";
        public static final String WIFI = "pref_wifi";
        public static final String VIDEOS = "pref_videos";
        public static final String FEATURES = "features";
        public static final String APP_INFO = "pref_app_info";
    }

    /**
     * Contains preference key constants.
     *
     */
    public static final class Key {
        public static final String PROFILE_JSON = "profile_json";
        public static final String AUTH_JSON = "auth_json";
        public static final String DOWNLOAD_ONLY_ON_WIFI = "download_only_on_wifi";
        public static final String DOWNLOAD_OFF_WIFI_SHOW_DIALOG_FLAG = "download_off_wifi_dialog_flag";
        public static final String COUNT_OF_VIDEOS_DOWNLOADED = "count_videos_downloaded";
        public static final String LASTACCESSED_MODULE_ID = "last_access_module_id";
        public static final String SHARE_COURSES = "share_courses";
        public static final String SPEED_TEST_KBPS = "speed_test_kbps";
        public static final String APP_VERSION = "app_version_name";
    }
}
