package com.example.a25908.partybuild.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Utils.FileUtils;
import com.example.a25908.partybuild.Views.RoundImageView;

import java.util.List;

/**
 * @author yusi
 * 评论lsitAdapter
 */
public class commListAdapter extends BaseAdapter {

    ViewHolder vh = null;
    private Context context;
    private List<DataManager.CommentMar.DataBean.CommentListBean> list1;
    public commListAdapter(Context context, List<DataManager.CommentMar.DataBean.CommentListBean> list1) {
        this.context = context;
        this.list1 = list1;
    }

    @Override
    public int getCount() {
        return list1.size();
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
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.comm_list_item, null);
            vh = new ViewHolder();
            vh.comm_Rimg=(RoundImageView) view.findViewById(R.id.comm_Rimg);
            vh.comm_Rname=(TextView) view.findViewById(R.id.comm_Rname);
            vh.comm_Rtime=(TextView) view.findViewById(R.id.comm_Rtime);
            vh.comm_Rcontent=(TextView) view.findViewById(R.id.comm_Rcontent);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
//        vh.comm_Rimg.setImageBitmap(FileUtils.stringtoBitmap(list1.get(position).ADD_TIME));
        vh.comm_Rname.setText(list1.get(position).USERNAME);
        vh.comm_Rtime.setText(list1.get(position).ADD_TIME);
        vh.comm_Rcontent.setText(list1.get(position).CONTENT);

        return view;
    }

    public static class ViewHolder {
        RoundImageView comm_Rimg;
        TextView comm_Rname;
        TextView comm_Rtime;
        TextView comm_Rcontent;
    }

}
