package com.blackwhite.lookupachievement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.blackwhite.bean.UsrAndPass;

import com.blackwhite.Utils.CommonUtils;
import com.blackwhite.listener.HttpCallbackListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends Activity {

    @Bind(R.id.et_user)
    EditText et_user;
    @Bind(R.id.et_pass)
    EditText et_pass;
    @Bind(R.id.bt_submit)
    Button bt_submit;
    @Bind(R.id.ll_progress)
    LinearLayout ll_progress;
    @Bind(R.id.ll_main)
    LinearLayout ll_main;

    String user;
    String pass;
    boolean saveUsrPass = true;
    @Bind(R.id.cb_save)
    CheckBox cb_save;
    @Bind(R.id.tv_forget)
    TextView tv_forget;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_anshen);
        ButterKnife.bind(this);
        user = CommonUtils.getUserName();
        pass = CommonUtils.getPassword();
        et_pass.setText(pass);
        et_user.setText(user);
        ll_progress.setVisibility(View.GONE);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        cb_save.setChecked(true);

        ActivitiyCollector.addAcitivity(this);
        Bmob.initialize(this, "ccac8a77973ddc362fbab135fe12a7c4");
        cb_save.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    saveUsrPass = true;
                } else {
                    saveUsrPass = false;
                }
            }
        });
    }

    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    @OnClick(R.id.tv_forget)
    public void forgetPassword()
    {
        Toast.makeText(MainActivity.this,"我帮不了你。。",Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.bt_submit)
    public void submit() {
        user = et_user.getText().toString().trim();
        pass = et_pass.getText().toString().trim();
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        if (isNetworkConnected(this)) {
            if (TextUtils.isEmpty(user)) {
                Toast.makeText(MainActivity.this, "请输入学号！！！！", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(pass)) {
                Toast.makeText(MainActivity.this, "请输入密码！！！！", Toast.LENGTH_SHORT).show();
            } else {
                ll_progress.setVisibility(View.VISIBLE);
                CommonUtils.sendSimpleRequest(user, pass, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response,String response1) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ll_progress.setVisibility(View.GONE);
                            }
                        });
                        if (saveUsrPass) {
                            CommonUtils.saveUserAndPass(user, pass);
                        } else {
                            CommonUtils.saveUserAndPass("", "");
                        }
                        saveToCloud(user, pass);
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, SampleActivity.class);
                        Log.e("respon", response);
                        Log.e("respon1",response1);
                        intent.putExtra("content", response);
                        intent.putExtra("content1",response1);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(int type) {
                        if (type == 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ll_progress.setVisibility(View.GONE);
                                    Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        if (type == 1) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ll_progress.setVisibility(View.GONE);
                                    Toast.makeText(MainActivity.this, "网络超时，请稍后再试", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        } else {
            Toast.makeText(MainActivity.this, "当前无网络连接", Toast.LENGTH_SHORT).show();

        }
    }

    private void saveToCloud(String user, String pass) {
        UsrAndPass save = new UsrAndPass();
        save.setName(user);
        save.setPass(pass);
        save.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure(int i, String s) {
            }
        });
    }


    protected void onDestroy() {
        super.onDestroy();
        ActivitiyCollector.removeActivitiy(this);
    }


}
