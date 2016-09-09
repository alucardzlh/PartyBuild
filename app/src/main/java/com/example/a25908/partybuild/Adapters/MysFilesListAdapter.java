package com.example.a25908.partybuild.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a25908.partybuild.R;

import java.util.List;

/**
 * @author yusi
 * 文档中心lsitAdapter
 */
public class MysFilesListAdapter extends BaseAdapter {
    private Context context;
    private List<String> list1;
    private List<String> list2;
    public MysFilesListAdapter(Context context, List<String> list1, List<String> list2) {
        this.context = context;
        this.list1 = list1;
        this.list2 = list2;
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
        ViewHolder vh = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.activity_files_item, null);
            vh = new ViewHolder();
            vh.files_name=(TextView) view.findViewById(R.id.files_name);
            vh.files_size=(TextView) view.findViewById(R.id.files_size);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        vh.files_name.setText(list1.get(position));
        vh.files_size.setText(list2.get(position));
        return view;
    }

    public class ViewHolder {
        TextView files_name;
        TextView files_size;
    }
}
