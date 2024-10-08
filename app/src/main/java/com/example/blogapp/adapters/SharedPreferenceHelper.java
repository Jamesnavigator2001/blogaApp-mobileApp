package com.example.blogapp.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.blogapp.httpRequests.ResponseHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SharedPreferenceHelper {
    private static final String PREF_NAME = "myPrefs";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String KEY_REGISTRATION_NUMBER = "RegistrationNumber"; // Consistent key

    public SharedPreferenceHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveUserData(String registrationNumber, String userName, String token, List<ResponseHandler.LoginResponse.Course> courses) {
        if (editor != null) {
            editor.putString(KEY_REGISTRATION_NUMBER, registrationNumber); // Save registration number
            editor.putString("UserName", userName);
            editor.putString("token", token);
            Gson gson = new Gson();
            String courseJson = gson.toJson(courses);
            editor.putString("courses" , courseJson);
            editor.apply();
        } else {
            Log.e("Saving data", "Editor is null");
        }
    }


    public void saveTokenExpiry(long expiryTime){
        if(editor != null){
            editor.putLong("expiry time",expiryTime);
            editor.apply();
        }
    }

    public String  getAccessToken(){return sharedPreferences.getString("token", "");}
    public String getUserName(){return sharedPreferences.getString("UserName","");}
    public String getUserRegistrationNumber() {
        return sharedPreferences.getString(KEY_REGISTRATION_NUMBER, ""); // Retrieve registration number
    }
    public long getTokenExpiry() {
        return sharedPreferences.getLong("token_expiry", 0);
    }

    public  List<ResponseHandler.LoginResponse.Course> getCourses() {
        String coursesJson = sharedPreferences.getString("courses" , "");
        Gson gson = new Gson();
        Type type = new TypeToken<List<ResponseHandler.LoginResponse.Course>>(){}.getType();
        return gson.fromJson(coursesJson, type);
    }
}
