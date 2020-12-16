package com.priyanka.dynamiclayouts;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    private String email;

    private String password;

    private SharedPreferences sharedPreferences;


    public SharedPreference(Context context){
        sharedPreferences = context.getSharedPreferences("auth",Context.MODE_PRIVATE);
    }

    public String getEmail() {
        email = sharedPreferences.getString("email","");
        return email;
    }

    public void setEmail(String email) {
        sharedPreferences.edit().putString("email",email).commit();
    }

    public String getPassword() {
        password=sharedPreferences.getString("password","");
        return password;
    }

    public void setPassword(String password) {
        sharedPreferences.edit().putString("password",password).commit();
    }
}
