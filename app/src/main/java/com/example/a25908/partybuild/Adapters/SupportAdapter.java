package com.example.a25908.partybuild.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a25908.partybuild.R;

import java.util.List;
import java.util.Map;

/**
 * 党员扶持的adapter
 * Created by weixuan on 2016/9/2.
 */

public class SupportAdapter extends BaseAdapter {
    private Context context;
    private List<Map> list;

    public SupportAdapter(Context context,List<Map> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        sViewHolder viewHolder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item_support,null);
            viewHolder = new sViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.item_sa_iv2);
            viewHolder.title = (TextView) view.findViewById(R.id.item_sa_tx1);
            viewHolder.context = (TextView) view.findViewById(R.id.item_sa_tx2);
            viewHolder.time = (TextView) view.findViewById(R.id.item_time2);
            viewHolder.zan = (LinearLayout) view.findViewById(R.id.item_sa_zan);

            view.setTag(viewHolder);
        }
        else {
            viewHolder = (sViewHolder) view.getTag();
        }
        viewHolder.imageView.setImageResource(R.drawable.ceshi_item1);
        viewHolder.time.setText(list.get(i).get("time")+"");
        viewHolder.title.setText(list.get(i).get("tx")+"");
        viewHolder.context.setText(list.get(i).get("tx2")+"");

        return view;
    }

    class sViewHolder{
        public ImageView imageView;//右边图片
        public TextView title;//标题
        public TextView context;//内容
        public LinearLayout zan;//赞
        public TextView time;//时间

    }
}
