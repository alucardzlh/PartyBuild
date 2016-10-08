package com.example.a25908.partybuild.Adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;

import java.util.List;

/**
 * @author yusi
 * 党的章程一级界面Adapter
 */
public class TheConstitutionOfThePartyAdapter extends BaseAdapter {
    private Context context;
    private List<DataManager.TCTPdanggui.DataBean.CommentListBean> list1;
    public TheConstitutionOfThePartyAdapter(Context context, List<DataManager.TCTPdanggui.DataBean.CommentListBean> list1) {
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
        ViewHolder vh = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.activity_the_constitution_of_the_party_item, null);
            vh = new ViewHolder();
            vh.tcop_tname=(TextView) view.findViewById(R.id.tcop_tname);
            vh.tcop_content=(TextView) view.findViewById(R.id.tcop_content);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        vh.tcop_tname.setText(list1.get(position).TITLE);
        vh.tcop_content.setText(Html.fromHtml(list1.get(position).CONTENT));
        return view;
    }

    public class ViewHolder {
        TextView tcop_tname;
        TextView tcop_content;
    }
}
