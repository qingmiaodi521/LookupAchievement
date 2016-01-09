package com.blackwhite.Utils;

import android.util.Log;

import com.blackwhite.listener.HttpCallbackListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by BlackWhite on 16/1/4.
 * 网络请求类，发送HTTP请求并返回数据。
 */
public class HttpUtils {


    public static void sendSimpleRequest(final String user, final String pass, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                HttpURLConnection connection1=null;
                try {
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
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.connect();
                    if (connection.getResponseCode() == 500) {
                        res = new StringBuilder();
                        res.append("not_open");
                        Log.e("ans","notopen");

                    } else
                        {
                        InputStream in = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        res = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            res.append(line);
                        }
                            Log.e("res:",res.toString());
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
                    if (res.toString().equals("{}") && listener != null) {
                        listener.onError(0);
                    } else if (listener != null) {
                        listener.onFinish(res.toString(), res1.toString());
                    }
                } catch (Exception e) {
                    if (e instanceof java.net.SocketTimeoutException) {
                        if (listener != null) {
                            listener.onError(1);
                        }
                    } else {
                        Log.e("error:",e.toString());
                        if (listener != null) {
                            listener.onError(0);
                        }
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (connection1 != null){
                        connection1.disconnect();
                    }
                }

            }
        }).start();
    }
}
