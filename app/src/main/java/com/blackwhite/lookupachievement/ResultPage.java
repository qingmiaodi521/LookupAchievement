package com.blackwhite.lookupachievement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by BlackWhite on 15/9/8.
 */
public class ResultPage extends Activity {

    @Bind(R.id.content)
    TextView tv_content;

    String contentxx = "空";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_page);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        contentxx = intent.getStringExtra("neirong");
        Log.e("content",contentxx+"空");
        tv_content.setText(contentxx);
    }
}
