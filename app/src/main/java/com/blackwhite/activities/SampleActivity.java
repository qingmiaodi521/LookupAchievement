package com.blackwhite.activities;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.blackwhite.Adapter.ViewPagerAdapter;
import com.blackwhite.bean.Suggest;
import com.blackwhite.lookupachievement.R;
import com.blackwhite.update.UpdateManager;
import com.blackwhite.views.WheelView;

import java.util.Arrays;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;


public class SampleActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private ListView mDrawerList;
    ViewPager pager;
    private String titles[] = new String[]{"本学期成绩", "所有学年成绩"};
    private Toolbar toolbar;

    private int[] colors=new int[]{
            R.color.material_deep_teal_500,
            R.color.blue,
            R.color.material_blue_grey_800,
            R.color.red
    };
    SlidingTabLayout slidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_result_page);
        Bmob.initialize(this,getString(R.string.id_key));//初始化bmob
        ActivitiesCollector.addActivity(this);

        String content1 = getIntent().getStringExtra("content");
        String content2 = getIntent().getStringExtra("content1");

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navdrawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_ab_drawer);
        }

        pager = (ViewPager) findViewById(R.id.viewpager);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), titles, content1, content2));

        slidingTabLayout.setViewPager(pager);
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.WHITE;
            }
        });

        setThemes(0);

        drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(drawerToggle);
        final String[] themes = new String[]{
                "默认主题",
                "蓝色主题",
                "灰色主题",
                "红色主题",
        };
        final String[] values = new String[]{
                "主题选择","一键评教","作者信息","意见反馈","检测更新","退出程序"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                        setThemes(0);
                        View outerView = LayoutInflater.from(SampleActivity.this).inflate(R.layout.dialog_wheelview, null);
                        WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
                        wv.setOffset(1);
                        wv.setItems(Arrays.asList(themes));
                        wv.setSeletion(0);
                        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                            @Override
                            public void onSelected(int selectedIndex, String item) {
                                if (selectedIndex>4) selectedIndex=4;
                               setThemes(selectedIndex-1);
                                Log.e("index", selectedIndex+"");
                            }
                        });
                        new android.app.AlertDialog.Builder(SampleActivity.this)
                                .setTitle("WheelView in Dialog")
                                .setView(outerView)
                                .setPositiveButton("OK", null)
                                .show();

                        break;
                    case 1:
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                        Toast.makeText(SampleActivity.this, "稍等即可研发出来", Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                        AlertDialog.Builder builder = new AlertDialog.Builder(SampleActivity.this);
                        builder.setMessage("要不要留个支付宝账号什么的");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.show();
                        break;
                    case 3:
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(SampleActivity.this);
                        View views = LayoutInflater.from(SampleActivity.this).inflate(R.layout.dialog_suggest,null);
                        builder1.setView(views);
                        builder1.setNegativeButton("取消", null);
                        builder1.setPositiveButton("确定", null);
                        final EditText et_contact= (EditText) (views).findViewById(R.id.et_contact);
                        final EditText et_content= (EditText) (views).findViewById(R.id.et_content);
                        final AlertDialog dialog = builder1.create();
                        dialog.show();
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (TextUtils.isEmpty(et_content.getText().toString())) {
                                    et_content.setError("反馈信息不能为空");
                                } else {
                                    Suggest sug = new Suggest();
                                    sug.setContent(et_content.getText().toString());
                                    sug.setContact(et_contact.getText().toString());
                                    sug.save(SampleActivity.this, new SaveListener() {
                                        @Override
                                        public void onSuccess() {
                                            Toast.makeText(SampleActivity.this,"反馈成功",Toast.LENGTH_SHORT).show();
                                        }
                                        @Override
                                        public void onFailure(int i, String s) {
                                            Toast.makeText(SampleActivity.this,"反馈失败",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    dialog.dismiss();
                                }
                            }
                        });
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        break;
                    case 4:
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                        UpdateManager manager = new UpdateManager(SampleActivity.this);
                        manager.checkUpdate();
                        break;
                    case 5:
                        ActivitiesCollector.removeAllActivities();
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivitiesCollector.removeAllActivities();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivitiesCollector.removeActivity(this);
    }

    public void setThemes(int index){
        mDrawerList.setBackgroundColor(getResources().getColor(colors[index]));
        toolbar.setBackgroundColor(getResources().getColor(colors[index]));
        slidingTabLayout.setBackgroundColor(getResources().getColor(colors[index]));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}
