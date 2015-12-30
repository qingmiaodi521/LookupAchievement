package com.blackwhite.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.blackwhite.Utils.CommonUtils;
import com.blackwhite.bean.JsonBean;
import com.blackwhite.lookupachievement.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SampleFragment extends Fragment {

    String content;
    JsonBean jsonBean;

    TextView tv_content;
    TextView tv_scotitle;
    ListView lv_result;


    public static SampleFragment newInstance(String content) {
        SampleFragment f = new SampleFragment();
        Bundle b = new Bundle();
        b.putString("content", content);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        content = getArguments().getString("content");
        if (!content.equals("not_open")) {
            View rootView = inflater.inflate(R.layout.page, container, false);
            tv_content = (TextView) rootView.findViewById(R.id.tv_content);
            lv_result = (ListView) rootView.findViewById(R.id.lv_result);
            content = CommonUtils.convert(content);
            content = content + '"' + "}]}";
            Log.e("content", content);
            parseJsonWithGson(content);
            setListView(jsonBean);
            ButterKnife.bind(this, rootView);
            return rootView;
        }else{
            View rootView = inflater.inflate(R.layout.page, container, false);
            tv_content = (TextView) rootView.findViewById(R.id.tv_content);
            tv_scotitle = (TextView) rootView.findViewById(R.id.tv_scotitle);
            tv_scotitle.setText("");
            tv_content.setText("成绩查询尚未开放，请开放后再尝试!");
            ButterKnife.bind(this, rootView);
            return rootView;
        }
    }

    private void setListView(JsonBean jsonBean) {
        tv_content.setText(jsonBean.getCredit() + "");
        ResultAdapter adapter = new ResultAdapter();
        lv_result.setAdapter(adapter);
        lv_result.setItemsCanFocus(false);
        adapter.updateData(jsonBean.getSubjects());
    }

    private void parseJsonWithGson(String content) {
        Gson gson = new Gson();
        Type type = new TypeToken<JsonBean>() {
        }.getType();
        jsonBean = gson.fromJson(content, type);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.itme_detail, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.tv_sub.setText(fund.getSubject());
            viewHolder.tv_score.setText(fund.getScore());
            viewHolder.tv_grade.setText(fund.getGrade());
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.tv_sub)
            TextView tv_sub;
            @Bind(R.id.tv_score)
            TextView tv_score;
            @Bind(R.id.tv_grade)
            TextView tv_grade;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

}