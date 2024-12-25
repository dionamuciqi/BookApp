package com.example.bookapp;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSession {
    private static final String PREF_NAME = "UserSessionPrefs";  // SharedPreferences file name
    private static final String KEY_EMAIL = "email";  // Key to store user's email
    private static final String KEY_GENERATED_CODE = "generatedCode";  // Key to store the generated verification code
    private static final String KEY_CODE_VALIDITY = "codeValidity";  // Key to store the validity of the verification code
    private static final String KEY_TIME_VALIDITY = "timeValidity";  // Key to store whether the code is within its valid time frame

    private static SharedPreferences sharedPreferences;  // SharedPreferences instance

    // Initialize SharedPreferences
    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);  // Open SharedPreferences in private mode
    }

    // Store email
    public static void setEmail(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email);  // Store the email in SharedPreferences
        editor.apply();  // Commit changes asynchronously
    }

    // Get email
    public static String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, null);  // Return the email, or null if not found
    }

    // Store generated verification code
    public static void storeGeneratedCode(String code) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_GENERATED_CODE, code);  // Store the generated code
        editor.apply();  // Commit changes asynchronously
    }

    // Get generated verification code
    public static String getGeneratedCode() {
        return sharedPreferences.getString(KEY_GENERATED_CODE, null);  // Return the generated code, or null if not found
    }

    // Store code validity (whether the code is still valid)
    public static void setCodeValidity(boolean isValid) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_CODE_VALIDITY, isValid);  // Store the validity of the code (true/false)
        editor.apply();  // Commit changes asynchronously
    }

    // Get code validity
    public static boolean getCodeValidity() {
        return sharedPreferences.getBoolean(KEY_CODE_VALIDITY, false);  // Return true if the code is valid, false if not
    }

    // Store time validity (whether the code is still within the valid time frame)
    public static void setTimeValidity(boolean isValid) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_TIME_VALIDITY, isValid);  // Store the time validity of the code (true/false)
        editor.apply();  // Commit changes asynchronously
    }

    // Get time validity
    public static boolean getTimeIsValid() {
        return sharedPreferences.getBoolean(KEY_TIME_VALIDITY, false);  // Return true if the time validity is true, false otherwise
    }
}