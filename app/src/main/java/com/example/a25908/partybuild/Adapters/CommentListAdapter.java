package com.example.a25908.partybuild.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Views.RoundImageView;

import java.util.List;
import java.util.Map;

/**
 * @author yusi
 * 详情评论Adapter
 */
public class CommentListAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String,Object>> list;
    public CommentListAdapter(Context context, List<Map<String,Object>> list1) {
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
            view = LayoutInflater.from(context).inflate(R.layout.activity_comm_listitem, null);
            vh = new ViewHolder();
            vh.comm_img=(RoundImageView) view.findViewById(R.id.comm_img);
            vh.comm_name=(TextView) view.findViewById(R.id.comm_name);
            vh.comm_txt=(TextView) view.findViewById(R.id.comm_txt);
            vh.comm_time=(TextView) view.findViewById(R.id.comm_time);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        vh.comm_img.setImageResource(R.mipmap.banner);
        vh.comm_name.setText(list.get(position).get("txt1")+"");
        vh.comm_txt.setText(list.get(position).get("txt2")+"");
        vh.comm_time.setText(list.get(position).get("time")+"");
        return view;
    }



    public class ViewHolder {
        RoundImageView comm_img;
        TextView comm_name;
        TextView comm_txt;
        TextView comm_time;
    }
}
