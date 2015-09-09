package com.blackwhite.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.blackwhite.listener.HttpCallbackListener;
import com.blackwhite.lookupachievement.MyApplication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by BlackWhite on 15/9/9.
 */
public class CommonUtils {


    public static void sendSimpleRequest(final String user, final String pass, final String check, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    Log.e("connection", "connection");
                    String add = "http://115.29.48.92/qilin/getgrade/qilin/";
                    add = add + user + "/" + pass + "/" + check;
                    Log.e("add", add);
                    URL url = new URL(add);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder res = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        res.append(line);
                    }
                    if (listener != null) {
                        listener.onFinish(res.toString());
                    }
                } catch (Exception e) {
                    if (e instanceof java.net.SocketTimeoutException) {
                        if (listener != null) {
                            listener.onError(1);
                        }
                    } else {
                        if (listener != null) {
                            listener.onError(0);
                        }
                    }
                    Log.e("error", e.toString());
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }

            }
        }).start();
    }

    public static String convert(String utfString) {
        StringBuilder sb = new StringBuilder();
        int i;
        int pos = 0;
        while ((i = utfString.indexOf("\\u", pos)) != -1) {
            sb.append(utfString.substring(pos, i));
            if (i + 5 < utfString.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
            }
        }
        return sb.toString();
    }
    public static void saveUserAndPass(String user,String pass)
    {
        SharedPreferences.Editor editor =  MyApplication.getContext().getSharedPreferences("secret", Context.MODE_PRIVATE).edit();
        editor.putString("username",user);
        editor.putString("password",pass);
        editor.commit();
    }
    public static String getUserName()
    {
        String name;
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("secret",Context.MODE_PRIVATE);
        name = preferences.getString("username","");
        return name;
    }
    public static String getPassword()
    {
        String pass ;
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("secret", Context.MODE_PRIVATE);
        pass = preferences.getString("password","");
        return pass;
    }

}
