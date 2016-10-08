package com.example.a25908.partybuild.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Views.RoundImageView;

import java.util.List;

/**
 * @author yusi
 * 评论lsitAdapter
 */
public class mydataupdataListAdapter extends BaseAdapter {

    ViewHolder vh = null;
    private Context context;
    private List<String> list1;
    public mydataupdataListAdapter(Context context, List<String> list1) {
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
            view = LayoutInflater.from(context).inflate(R.layout.list_item_mydata, null);
            vh = new ViewHolder();
            vh.nn1=(TextView) view.findViewById(R.id.nn1);
            vh.nn2=(ImageView) view.findViewById(R.id.nn2);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        vh.nn1.setText(list1.get(position));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh.nn2.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    public static class ViewHolder {
        TextView nn1;
        ImageView nn2;
    }

}
