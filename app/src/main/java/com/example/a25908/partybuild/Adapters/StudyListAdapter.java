package com.example.a25908.partybuild.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a25908.partybuild.R;

import java.util.List;
import java.util.Map;

public class StudyListAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String,Object>> list;
    public StudyListAdapter(Context context, List<Map<String,Object>> list1) {
        this.context = context;
        this.list = list1;
    }

    @Override
    public int getCount() {
        Log.e("ssss", "getCount-------------" + list.size());
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder vh = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.activity_study_listitem, null);
            vh = new ViewHolder();
            vh.study_img=(ImageView) view.findViewById(R.id.study_img);
            vh.study_txt1=(TextView) view.findViewById(R.id.study_txt1);
            vh.study_txt2=(TextView) view.findViewById(R.id.study_txt2);
            vh.study_time=(TextView) view.findViewById(R.id.study_time);
            vh.study_bt1=(TextView) view.findViewById(R.id.study_bt1);
            vh.study_bt2=(TextView) view.findViewById(R.id.study_bt2);
            vh.study_bt3=(TextView) view.findViewById(R.id.study_bt3);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        vh.study_img.setImageResource(R.mipmap.banner);
        vh.study_txt1.setText(list.get(position).get("txt1")+"");
        vh.study_txt2.setText(list.get(position).get("txt2")+"");
        vh.study_time.setText(list.get(position).get("time")+"");
        return view;
    }



    public class ViewHolder {
        ImageView study_img;
        TextView study_txt1;
        TextView study_txt2;
        TextView study_time;
        TextView study_bt1;
        TextView study_bt2;
        TextView study_bt3;
    }
}
