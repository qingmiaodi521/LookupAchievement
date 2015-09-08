package com.blackwhite.lookupachievement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @Bind(R.id.et_user)
    EditText et_user;
    @Bind(R.id.et_pass)
    EditText et_pass;
    @Bind(R.id.bt_submit)
    Button bt_submit;

    private String respon = "" ;
    private Handler myhander = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x123:
                    respon = (String) msg.obj;
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, ResultPage.class);
                    Log.e("respon", respon);
                    intent.putExtra("neirong",respon);
                    startActivity(intent);
                    break;
            }
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_anshen);
        ButterKnife.bind(this);
        et_pass.setText("234417");
        et_user.setText("1206010236");
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());

    }
    @OnClick(R.id.bt_submit)
    public void submit()
    {
        String user = et_user.getText().toString().trim();
        String pass = et_user.getText().toString().trim();
        sendRequest(user, pass);
    }

    private void sendRequest(final String user,final  String pass) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    Log.e("connection","connection");
                    URL url = new URL("http://115.29.48.92/query");
                    //URL url = new URL("http://www.baidu.com");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    out.writeBytes("csrfmiddlewaretoken=c7FKwEUecJo4aQxKodnV2O13PjlggQxp&"+"uid=" + user + "&pwd=" + pass + "&check=all");
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder res = new StringBuilder();
                    String line;
                    while ((line = reader.readLine())!=null)
                    {
                        res.append(line);
                    }
                    Log.e("message",res.toString());
                    Message message = new Message();
                    message.what = 0x123;
                    message.obj = res.toString();
                    myhander.sendMessage(message);

                } catch (IOException e) {
                    Log.e("error", e.toString());
                }finally {
                    if (connection!=null)
                    {
                        connection.disconnect();
                    }
                }

            }
        }).start();
    }
}
