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
import com.example.a25908.partybuild.Utils.FileUtils;

import java.util.List;


/**
 * 子类子类列表的适配器
 * Created by weixaun on 16/10/11.
 */

public class CListAdapter extends BaseAdapter {

    private Context mContext;
    private List<DataManager.ZZplayer.DataBean.UserlistPageBean.DepartmentlistBean.UserlistBean> mChilds;

    public CListAdapter(Context context, List<DataManager.ZZplayer.DataBean.UserlistPageBean.DepartmentlistBean.UserlistBean> childs) {
        this.mContext = context;
        this.mChilds = childs;
    }

    @Override
    public int getCount() {
        return mChilds != null ? mChilds.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        if ((getCount() > 0) && (position > 0 && position < mChilds.size())) {
            return mChilds.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.child_child_item, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.update(mChilds.get(position).USERNAME);
        if (mChilds.get(position).HEAD_IMG.equals("")){
            holder.update3();
        }else {
            holder.update2(mChilds.get(position).HEAD_IMG);
        }

        return convertView;
    }

    class Holder {

        private ImageView iv;
        private TextView tv;

        public Holder(View v) {
            iv = (ImageView) v.findViewById(R.id.user_head2);
            tv = (TextView) v.findViewById(R.id.txt_user_name2);
        }

        public void update(String text) {
            tv.setText(text);
        }
        public void update2(String text) {
            iv.setImageBitmap(FileUtils.stringtoBitmap(text));
        }
        public void update3() {
            iv.setImageResource(R.mipmap.appicon);
        }
    }
}
