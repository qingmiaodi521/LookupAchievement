package com.blackwhite.lookupachievement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.blackwhite.Utils.CommonUtils;
import com.blackwhite.bean.JsonBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by BlackWhite on 15/9/8.
 */
public class ResultPage extends Activity {

    @Bind(R.id.content)
    TextView tv_content;

    String content;
    @Bind(R.id.lv_result)
    ListView lv_result;

    JsonBean jsonBean;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_page);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        content = intent.getStringExtra("neirong");
        content = CommonUtils.convert(content);
        content = content + '"' + "}]}";
        Log.e("content", content);
        tv_content.setText(content);
        parseJsonWithGson(content);
        setListView(jsonBean);
    }

    private void setListView(JsonBean jsonBean) {
        tv_content.setText("你的绩点为：" + jsonBean.getCredit() + "");
        ResultAdapter adapter = new ResultAdapter();
        lv_result.setAdapter(adapter);
        adapter.updateData(jsonBean.getSubjects());
    }

    private void parseJsonWithGson(String content) {
        Gson gson = new Gson();
        Type type = new TypeToken<JsonBean>() {
        }.getType();
        jsonBean = gson.fromJson(content, type);
        List<JsonBean.SubjectsEntity> subjects = jsonBean.getSubjects();
        for (JsonBean.SubjectsEntity sub : subjects) {
            Log.e("grade", sub.getGrade());
            Log.e("score", sub.getScore());
            Log.e("subject", sub.getSubject());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    class ResultAdapter extends BaseAdapter {

        List<JsonBean.SubjectsEntity> data;

        public ResultAdapter() {
            data = new ArrayList<>();
        }

        public void updateData(List<JsonBean.SubjectsEntity> list) {
            data.clear();
            if (list != null)
                data.addAll(list);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if (data == null) return 0;
            else return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            final JsonBean.SubjectsEntity fund = data.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(ResultPage.this).inflate(R.layout.list_result, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.tv_sub.setText(fund.getSubject());
            viewHolder.tv_score.setText(fund.getScore());
            viewHolder.tv_garde.setText(fund.getGrade());
            return convertView;
        }

        /**
         * This class contains all butterknife-injected Views & Layouts from layout file 'list_result.xml'
         * for easy to all layout elements.
         *
         * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
         */
         class ViewHolder {
            @Bind(R.id.tv_sub)
            TextView tv_sub;
            @Bind(R.id.tv_score)
            TextView tv_score;
            @Bind(R.id.tv_grade)
            TextView tv_garde;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }



}
