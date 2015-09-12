package com.blackwhite.lookupachievement;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.blackwhite.Adapter.ViewPagerAdapter;
import com.blackwhite.bean.Suggest;
import com.blackwhite.update.UpdateManager;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;


public class SampleActivity extends ActionBarActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private ListView mDrawerList;
    ViewPager pager;
    private String titles[] = new String[]{"本学期成绩", "所有学年成绩"};
    private Toolbar toolbar;

    SlidingTabLayout slidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_result_page);

        Bmob.initialize(this, "ccac8a77973ddc362fbab135fe12a7c4");

        ActivitiyCollector.addAcitivity(this);

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

        setDefaultColor();

        drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(drawerToggle);
        String[] values = new String[]{
                "默认主题", "蓝色主题", "灰色主题", "一键评教","打死作者","意见反馈","检测更新","退出程序"
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
                        mDrawerList.setBackgroundColor(getResources().getColor(R.color.material_deep_teal_500));
                        toolbar.setBackgroundColor(getResources().getColor(R.color.material_deep_teal_500));
                        slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.material_deep_teal_500));
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case 1:
                          mDrawerList.setBackgroundColor(getResources().getColor(R.color.blue));
                          toolbar.setBackgroundColor(getResources().getColor(R.color.blue));
                          slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.blue));
                          mDrawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case 2:
                        mDrawerList.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_800));
                        toolbar.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_800));
                        slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_800));
                        mDrawerLayout.closeDrawer(Gravity.LEFT);

                        break;
                    case 3:
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                        Toast.makeText(SampleActivity.this, "待评教系统出来即可研发出来了", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                        AlertDialog.Builder builder = new AlertDialog.Builder(SampleActivity.this);
                        builder.setMessage("我承认很丑...无奈设计师和安神私奔了...原谅我吧...PS：为什么没有ios版本的呢。。因为...我不会。。");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.show();

                        break;
                    case 5:
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(SampleActivity.this);
                        final EditText suggestContent = new EditText(SampleActivity.this);
                        final EditText contact = new EditText(SampleActivity.this);
                        suggestContent.setHint("请输入反馈意见");
                        contact.setHint("联系方式给我可好");
                        LinearLayout editTextContainer = new LinearLayout(SampleActivity.this);
                        editTextContainer.setOrientation(LinearLayout.VERTICAL);
                        editTextContainer.addView(suggestContent);
                        editTextContainer.addView(contact);
                        builder1.setView(editTextContainer);
                        builder1.setTitle("意见反馈");
                        builder1.setNegativeButton("取消", null);
                        builder1.setPositiveButton("确定", null);
                        final AlertDialog dialog = builder1.create();
                        dialog.show();
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (TextUtils.isEmpty(suggestContent.getText().toString())) {
                                    Toast.makeText(SampleActivity.this,"意见反馈为空反馈什么~~~",Toast.LENGTH_LONG).show();
                                } else {
                                    Suggest sug = new Suggest();
                                    sug.setContent(suggestContent.getText().toString());
                                    sug.setContact(contact.getText().toString());
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
                    case 6:
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                        UpdateManager manager = new UpdateManager(SampleActivity.this);
                        manager.checkUpdate();
                        break;
                    case 7:
                        ActivitiyCollector.removeAllAcitivity();
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivitiyCollector.removeAllAcitivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivitiyCollector.removeActivitiy(this);
    }

    public void setDefaultColor()
    {
        mDrawerList.setBackgroundColor(getResources().getColor(R.color.material_deep_teal_500));
        toolbar.setBackgroundColor(getResources().getColor(R.color.material_deep_teal_500));
        slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.material_deep_teal_500));
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
