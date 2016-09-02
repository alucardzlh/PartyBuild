package com.example.a25908.partybuild.Adapters;

import android.content.Context;
import android.util.Log;
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
 * @author yusi
 * 人物长廊界面Adapter
 */
public class PeopleGalleryAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    public PeopleGalleryAdapter(Context context,List<String> list) {
        this.context = context;
        this.list = list;
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
            view = LayoutInflater.from(context).inflate(R.layout.activity_people_gallery_item, null);
            vh = new ViewHolder();
            vh.imgLayou=(LinearLayout) view.findViewById(R.id.imgLayou);
            vh.imgtxt=(TextView) view.findViewById(R.id.imgtxt);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        vh.imgLayou.setBackgroundResource(R.mipmap.banner);
        vh.imgtxt.setText(list.get(position));
        return view;
    }

    public class ViewHolder {
        LinearLayout imgLayou;
        TextView imgtxt;
    }
}
