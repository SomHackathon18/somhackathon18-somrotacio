package com.sommobilitat.hackathon.somhackathon2018.controllers;

import android.content.Context;

public class AuthController {
    public static final String KEY_USER_NAME = "KEY_USER_NAME";
    public static final String KEY_USER_LOGGED = "KEY_USER_LOGGED";

    public static void setUsername(Context context, String value) {
        SharedPreferencesController.setStringValue(context, KEY_USER_NAME, value);
    }

    public static String getUsername(Context context) {
        return SharedPreferencesController.getStringValue(context, KEY_USER_NAME, "");
    }

    public static void setUserLogged(Context context, boolean value) {
        SharedPreferencesController.setBooleanValue(context, KEY_USER_LOGGED, value);
    }

    public static boolean isUserLogged(Context context) {
        return SharedPreferencesController.getBooleanValue(context, KEY_USER_LOGGED, false);
    }
}
