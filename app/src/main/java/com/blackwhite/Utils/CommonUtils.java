package com.blackwhite.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.blackwhite.listener.HttpCallBackUpdateListener;
import com.blackwhite.activities.MyApplication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by BlackWhite on 15/9/9.
 * 常见工具类，
 */
public class CommonUtils {

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
