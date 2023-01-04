package com_calculator.java_calculator.checkcalculator.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com_calculator.java_calculator.checkcalculator.activities.auth.LoginActivity;
import com_calculator.java_calculator.checkcalculator.models.User;

public class SharedPrefManager{  //the constants
        private static final String SHARED_PREF_NAME = "sharedPreferencesUser";
        private static final String KEY_ID = "keyID";
        public static final String KEY_NIGHT_MODE = "keyDayNightMode";
        public static final String KEY_LANGUAGE = "keyLanguage";

        private static SharedPrefManager mInstance;
        private static Context mContext;

        private SharedPrefManager(Context context) {
            this.mContext = context;
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
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(KEY_ID, user.getId());
            editor.apply();
        }

        //this method will checker whether user is already logged in or not
        public boolean isLoggedIn() {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            return sharedPreferences.getInt(KEY_ID, -1) != -1;
        }

        public void setLanguage(String language){
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_LANGUAGE, language);
            editor.apply();
        }

        public String getLanguage(){
            String language = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).getString(KEY_LANGUAGE, "");
            return language;
        }

        public void setMode(boolean isChecked){
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(KEY_NIGHT_MODE, isChecked);
            editor.apply();
        }

        public Boolean getMode(){
            Boolean state = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).getBoolean(KEY_NIGHT_MODE, false);
            return state;
        }

        //this method will give the logged in user
        public User getUser() {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            return new User(sharedPreferences.getInt(KEY_ID, -1));
        }

        //this method will logout the user
        public void logout() {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            mContext.startActivity(new Intent(mContext, LoginActivity.class));
        }
}
