package com.blackwhite.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.blackwhite.listener.HttpCallBackUpdateListener;
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


    public static void sendSimpleRequest(final String user, final String pass, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                HttpURLConnection connection1 = null;
                try {
                    Log.e("connection", "connection");
                    String baseUrl = "http://innerac.com/qilin/getgrade/qilin/";
                    String add;
                    String add1;
                    StringBuilder res;
                    add = baseUrl + user + "/" + pass + "/" + "one";
                    add1 = baseUrl + user + "/" + pass + "/" + "all";
                    Log.e("add", add);
                    URL url = new URL(add);
                    URL url1 = new URL(add1);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(20000);
                    connection.setReadTimeout(20000);
                    connection.connect();
                    if (connection.getResponseCode()==500){
                        res = new StringBuilder();
                        res.append("not_open");
                    }else {
                        InputStream in = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        res = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            res.append(line);
                        }
                    }
                    connection1 = (HttpURLConnection) url1.openConnection();
                    connection1.setRequestMethod("GET");
                    connection1.setConnectTimeout(8000);
                    connection1.setReadTimeout(8000);
                    InputStream in1 = connection1.getInputStream();
                    BufferedReader reader1 = new BufferedReader(new InputStreamReader(in1));
                    StringBuilder res1 = new StringBuilder();
                    String line1;
                    while ((line1 = reader1.readLine()) != null) {
                        res1.append(line1);
                    }
                    if (res.toString().equals("{}")&&listener!=null){
                        listener.onError(0);
                    }else
                    if (listener != null) {
                        listener.onFinish(res.toString(), res1.toString());
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

    public static void sendUpdateRequest(final HttpCallBackUpdateListener listener) {
        HttpURLConnection connection ;
        try {
            URL url = new URL("http://www.qilinlirui.com/version.xml");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(20000);
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder res = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                res.append(line);
            }
            Log.e("contentofup", res.toString());
            if (listener != null) {
                listener.onFinish(res.toString());
            }
        } catch (Exception e) {
            listener.onError(e);
        }
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

    public static void saveUserAndPass(String user, String pass) {
        SharedPreferences.Editor editor = MyApplication.getContext().getSharedPreferences("secret", Context.MODE_PRIVATE).edit();
        editor.putString("username", user);
        editor.putString("password", pass);
        editor.commit();
    }

    public static String getUserName() {
        String name;
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("secret", Context.MODE_PRIVATE);
        name = preferences.getString("username", "");
        return name;
    }

    public static String getPassword() {
        String pass;
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("secret", Context.MODE_PRIVATE);
        pass = preferences.getString("password", "");
        return pass;
    }

}
