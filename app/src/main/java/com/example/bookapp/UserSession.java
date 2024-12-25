package com.example.bookapp;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSession {
    private static final String PREF_NAME = "UserSessionPrefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_GENERATED_CODE = "generatedCode";
    private static final String KEY_CODE_VALIDITY = "codeValidity";
    private static final String KEY_TIME_VALIDITY = "timeValidity";

    private static SharedPreferences sharedPreferences;

    // Initialize SharedPreferences
    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Store email
    public static void setEmail(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    // Get email
    public static String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    // Store generated verification code
    public static void storeGeneratedCode(String code) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_GENERATED_CODE, code);
        editor.apply();
    }

    // Get generated verification code
    public static String getGeneratedCode() {
        return sharedPreferences.getString(KEY_GENERATED_CODE, null);
    }

    // Store code validity (whether the code is still valid)
    public static void setCodeValidity(boolean isValid) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_CODE_VALIDITY, isValid);
        editor.apply();
    }

    // Get code validity
    public static boolean getCodeValidity() {
        return sharedPreferences.getBoolean(KEY_CODE_VALIDITY, false);
    }

    // Store time validity (whether the code is still within the valid time frame)
    public static void setTimeValidity(boolean isValid) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_TIME_VALIDITY, isValid);
        editor.apply();
    }

    // Get time validity
    public static boolean getTimeIsValid() {
        return sharedPreferences.getBoolean(KEY_TIME_VALIDITY, false);
    }
}