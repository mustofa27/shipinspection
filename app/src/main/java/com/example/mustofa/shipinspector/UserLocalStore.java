package com.example.mustofa.shipinspector;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by mustofa on 3/3/2016.
 */
public class UserLocalStore {
    //public static final String SP_NAME = "userdetail";
    SharedPreferences userSharedPref;
    public UserLocalStore(Context context)
    {
        //userSharedPref = context.getSharedPreferences(SP_NAME,0);
        userSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public void setLoggedInUser(User user)
    {
        SharedPreferences.Editor spEditor = userSharedPref.edit();
        spEditor.putInt("id_user",user.getId_user());
        spEditor.putString("username",user.getUsername());
        spEditor.putString("email",user.getEmail());
        spEditor.putString("password",user.getPassword());
        spEditor.putString("role",user.getRole());
        spEditor.commit();
    }
    public User getLoggedInUser()
    {
        return new User(userSharedPref.getInt("id_user", -1),userSharedPref.getString("username",""),
                userSharedPref.getString("email",""),userSharedPref.getString("password",""),
                userSharedPref.getString("role",""));
    }
    public void setUserLoggedIn(boolean state)
    {
        SharedPreferences.Editor spEditor = userSharedPref.edit();
        spEditor.putBoolean("status",state);
        spEditor.commit();
    }
    public boolean getUserLoggedIn()
    {
        return userSharedPref.getBoolean("status",false);
    }
    public void clearUserData()
    {
        SharedPreferences.Editor spEditor = userSharedPref.edit();
        spEditor.clear();
    }
}
