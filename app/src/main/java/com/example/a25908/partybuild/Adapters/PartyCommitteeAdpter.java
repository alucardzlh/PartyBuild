package com.example.a25908.partybuild.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a25908.partybuild.Model.DataManager.PartyCommittee;
import com.example.a25908.partybuild.R;

import java.util.List;

/**
 * 党委通知的adapter
 * Created by Minus K on 2016/8/29.
 */

public class PartyCommitteeAdpter extends BaseAdapter {
    private Context mContext;
    private List<PartyCommittee> list;
    public PartyCommitteeAdpter(Context context, List<PartyCommittee> list){
        this.mContext = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return 0;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_dangwei,null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.item_pc_iv);
            viewHolder.title = (TextView) view.findViewById(R.id.item_pc_title);
            viewHolder.context = (TextView) view.findViewById(R.id.item_pc_context);
            viewHolder.browse = (TextView) view.findViewById(R.id.item_liulan);
            viewHolder.time = (TextView) view.findViewById(R.id.item_time);

            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.imageView.setBackgroundResource(R.drawable.ceshi_item1);
        viewHolder.title.setText(list.get(i).title);
        viewHolder.context.setText(list.get(i).context);
        viewHolder.browse.setText(list.get(i).browse);
        viewHolder.time.setText(list.get(i).time);
        return view;
    }

    class ViewHolder{
        public ImageView imageView;//右边图片
        private TextView title;//标题
        private TextView context;//内容
        private TextView  browse;//浏览数
        private TextView time;//时间

    }
}
