package com.example.a25908.partybuild.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;

import java.io.IOException;
import java.util.List;

import Decoder.BASE64Decoder;

/**
 * @author yusi
 * 学习园地界面Adapter
 */
public class StudyListAdapter extends BaseAdapter {
    private Context context;
    private List<DataManager.paertyComm.DataBean.CommentListBean> list;
    public StudyListAdapter(Context context, List<DataManager.paertyComm.DataBean.CommentListBean> list1) {
        this.context = context;
        this.list = list1;
    }

    @Override
    public int getCount() {
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
            vh.study_bt3=(TextView) view.findViewById(R.id.study_bt3);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }


        try {
            BASE64Decoder decode = new BASE64Decoder();
            byte[] b = decode.decodeBuffer(list.get(position).title_img);
            Glide.with(context).load(b).diskCacheStrategy(DiskCacheStrategy.ALL).into(vh.study_img);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        vh.study_img.setImageBitmap(FileUtils.stringtoBitmap(list.get(position).title_img));
        vh.study_txt1.setText(list.get(position).title+"");
        vh.study_txt2.setText(list.get(position).describes+"");
        vh.study_time.setText(list.get(position).add_time+"");

        vh.study_bt1.setText("浏览 "+list.get(position).browse+" ");

//        studyType;//学习状态（0为未学习 1为已学习）
        if(list.get(position).studyType.equals("0")){
            vh.study_bt3.setText("未学习");
        }else{
            vh.study_bt3.setText("已学习");
        }

        return view;
    }



    public class ViewHolder {
        ImageView study_img;
        TextView study_txt1;
        TextView study_txt2;
        TextView study_time;
        TextView study_bt1;
        TextView study_bt3;
    }
}
