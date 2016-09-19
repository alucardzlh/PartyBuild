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
import com.example.a25908.partybuild.Views.RoundImageView;

import java.util.List;
import java.util.Map;

/**
 * 在线答疑的适配器
 * Created by weixaun on 2016/9/2.
 */

public class AnswerAdapter extends BaseAdapter {
    private Context context;
    private List<Map> list;

    public AnswerAdapter(Context context,List<Map> list){
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
        aViewHodler viewHodler = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item_answer,null);
            viewHodler = new aViewHodler();
            viewHodler.item_an_da = (TextView) view.findViewById(R.id.item_an_da);
            viewHodler.item_an_tx = (RoundImageView) view.findViewById(R.id.item_an_tx);
            viewHodler.item_an_time = (TextView) view.findViewById(R.id.item_an_time);
            viewHodler.item_an_wait = (ImageView) view.findViewById(R.id.item_an_wait);
            viewHodler.item_an_wen = (TextView) view.findViewById(R.id.item_an_wen);
            viewHodler.item_an_huida = (LinearLayout) view.findViewById(R.id.item_an_huida);
            view.setTag(viewHodler);
        }
        else {
            viewHodler = (aViewHodler) view.getTag();
        }
        viewHodler.item_an_wen.setText(list.get(i).get("wen")+"");
        viewHodler.item_an_time.setText(list.get(i).get("time")+"");
        viewHodler.item_an_tx.setImageResource(R.drawable.ceshi_item1);
        if (list.get(i).get("da") == null){
            viewHodler.item_an_huida.setVisibility(View.GONE);
            viewHodler.item_an_wait.setVisibility(View.VISIBLE);
        }
        else {
            viewHodler.item_an_huida.setVisibility(View.VISIBLE);
            viewHodler.item_an_da.setText(list.get(i).get("da")+"");
            viewHodler.item_an_wait.setVisibility(View.GONE);
        }
        return view;
    }

    class aViewHodler{
        public TextView item_an_wen;//问题
        public RoundImageView item_an_tx;//头像
        public ImageView item_an_wait;//等待回答
        public TextView item_an_da;//回答
        public TextView item_an_time;//时间
        public LinearLayout item_an_huida;//回答
    }
}
