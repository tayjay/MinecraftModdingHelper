package com.taylar.minecraftmoddinghelper;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;

/**
 * Created by tayjm_000 on 2015-12-03.
 */
public class SharedPrefUtil {
    private static final int PREFERENCE_MODE_PRIVATE = 0;
    public static TestActivity INSTANCE;
    public static  SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    private static void init()
    {
        if(INSTANCE==null)
        {
            INSTANCE = new TestActivity();
        }
        if(sharedPreferences==null||editor==null) {
            sharedPreferences = INSTANCE.getPreferences(PREFERENCE_MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
    }

    public static void putString(String name, String val)
    {
        init();
        editor.putString(name,val);
        editor.commit();
    }

    public static String getString(String name, String defValue)
    {
        init();
        return sharedPreferences.getString(name, defValue);
    }

    public static void putInt(String name, int val)
    {
        init();
        editor.putInt(name, val);
        editor.commit();
    }

    public static int getInt(String name, int defValue)
    {
        init();
        return sharedPreferences.getInt(name, defValue);
    }
}
