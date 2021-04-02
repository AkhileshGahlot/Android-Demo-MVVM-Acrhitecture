package com.example.myapplicationstructure.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public final class Config {
    private static final String PREFERENCES = "preferences";
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static void init(Context context) {
        preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static String getBranchCode() {
        return preferences.getString("branch_code", "");
    }

    public static void setBranchCode(String fcmToken) {
        editor.putString("branch_code", fcmToken).apply();
    }

    public static String getUserName() {
        return preferences.getString("teacher_name", "");
    }

    public static void setUserName(String fcmToken) {
        editor.putString("teacher_name", fcmToken).apply();
    }

    public static String getJwtToken() {
        return preferences.getString("jwtToken", "");
    }

    public static void setJwtToken(String fcmToken) {
        editor.putString("jwtToken", fcmToken).apply();
    }

     public static String getFcmToken() {
        return preferences.getString("fcmToken", "");

    }

    public static void setFcmToken(String fcmToken) {
        editor.putString("fcmToken", fcmToken).apply();
    }

    public static void savePreferences() {
        editor.commit();
    }

    public static void clearPreferences() {
        editor.clear();
        savePreferences();
    }

    public static void removeValueForKey(String key) {
        if (!TextUtils.isEmpty(key)) {
            editor.remove(key);
            savePreferences();
        }

    }
}
