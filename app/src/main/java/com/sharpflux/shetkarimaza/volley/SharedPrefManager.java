package com.sharpflux.shetkarimaza.volley;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.sharpflux.shetkarimaza.activities.TabLayoutLogRegActivity;
import com.sharpflux.shetkarimaza.model.User;

public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "shetkariMaza";
    private static final String KEY_USERNAME = "CustomerFullName";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_MOBILE = "MobileNo";
    private static final String KEY_MIDDLENAME= "middlename";
    private static final String KEY_LASTNAME = "lastname";
    private static final String KEY_REGISTRATIONTYPEID = "RegistrationTypeId";
    private static final String KEY_REGISTRATIONCOMPLETE= "IsRegistrationComplete";
    private static final String KEY_ISVERIFIED= "IsVerified";
    //private static final String KEY_GENDER = "keygender";
    private static final String KEY_ID = "CustomerId";
    private static final String KEY_LANGUAGE = "currentLang";
    //private static final String KEY_ISCOMPLETE = "IsCompleteRegistration";
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences

    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_MOBILE, user.getMobile());
        editor.putString(KEY_MIDDLENAME, user.getMiddlename());
        editor.putString(KEY_LASTNAME, user.getLastname());
        editor.putString(KEY_LANGUAGE, user.getLanguage());
        editor.putString(KEY_REGISTRATIONTYPEID, user.getRegistrationTypeId());
        editor.putBoolean(KEY_REGISTRATIONCOMPLETE, user.getRegistrationComplete());
        editor.putBoolean(KEY_ISVERIFIED, user.getVerified());
        editor.apply();
    }


    public void IsRegistrationComplete(Boolean IsComplete) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_REGISTRATIONCOMPLETE,IsComplete);
        editor.apply();
    }

    public void ChangeLanguage(String Language) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LANGUAGE,Language);
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }
   /* public boolean isCompleteRegistration() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ISCOMPLETE, null) != null;
    }*/
    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_MOBILE, null),
                sharedPreferences.getString(KEY_MIDDLENAME, null),
                sharedPreferences.getString(KEY_LASTNAME, null),
                sharedPreferences.getString(KEY_LANGUAGE, null),
                sharedPreferences.getString(KEY_REGISTRATIONTYPEID, null),
                sharedPreferences.getBoolean(KEY_REGISTRATIONCOMPLETE, false),
                sharedPreferences.getBoolean(KEY_ISVERIFIED, false)

        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(mCtx, TabLayoutLogRegActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(intent);
    }

    public boolean isFirstTime() {
        boolean firstTime=true;
        if (firstTime ) {
            SharedPreferences mPreferences = mCtx.getSharedPreferences("first_time", Context.MODE_PRIVATE);
            firstTime = mPreferences.getBoolean("firstTime", true);
            if (firstTime) {
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putBoolean("firstTime", false);
                editor.commit();
            }
        }
        return firstTime;
    }
}
