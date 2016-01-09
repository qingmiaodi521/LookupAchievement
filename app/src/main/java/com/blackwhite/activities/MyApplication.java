package com.blackwhite.activities;

import android.app.Application;
import android.content.Context;

/**
 * Created by BlackWhite on 15/9/8.
 *
 */
public class MyApplication extends Application{
    private static Context context;
    public void onCreate(){
        context = getApplicationContext();
        super.onCreate();
    }
    public static Context getContext()
    {
        return context;
    }
}
