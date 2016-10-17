package com.example.a25908.partybuild.Adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Utils.FileUtils;

import java.util.List;


/**
 * ExpandableListView子类分组的适配器
 * Created by weixaun on 16/10/11.
 */

public class ChildAdapter extends BaseExpandableListAdapter {

    private Context mContext;// 上下文

    private List<DataManager.ZZplayer.DataBean.UserlistPageBean.DepartmentlistBean> mChilds;// 数据源

    public ChildAdapter(Context context, List<DataManager.ZZplayer.DataBean.UserlistPageBean.DepartmentlistBean> childs) {
        this.mContext = context;
        this.mChilds = childs;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChilds.get(groupPosition).Userlist != null ? mChilds
                .get(groupPosition).Userlist.size() : 0;
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        if (mChilds.get(groupPosition).Userlist != null
                && mChilds.get(groupPosition).Userlist.size() > 0)
            return mChilds.get(groupPosition).Userlist
                    .get(childPosition).USERNAME;
        return null;
    }
    public String getChildImg(int groupPosition, int childPosition) {
        if (mChilds.get(groupPosition).Userlist != null
                && mChilds.get(groupPosition).Userlist.size() > 0)
            return mChilds.get(groupPosition).Userlist
                    .get(childPosition).HEAD_IMG;
        return "";
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isExpanded, View convertView, ViewGroup parent) {
        ChildHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.child_child_item, null);
            holder = new ChildHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        holder.update(getChild(groupPosition, childPosition));
        String string = getChildImg(groupPosition, childPosition);
        if (TextUtils.isEmpty(string)){
            holder.update3();
        }else {
            holder.update2(getChildImg(groupPosition, childPosition));
        }

        return convertView;
    }


    /**
     * @author weixaun
     *
     *         Holder优化
     * */
    class ChildHolder {

        private TextView childChildTV;
        private ImageView user_head2;

        public ChildHolder(View v) {
            childChildTV = (TextView) v.findViewById(R.id.txt_user_name2);
            user_head2 = (ImageView) v.findViewById(R.id.user_head2);
        }

        public void update(String str) {
            childChildTV.setText(str);
//            childChildTV.setTextColor(Color.parseColor("#111"));
        }
        public void update2(String str) {
            user_head2.setImageBitmap(FileUtils.stringtoBitmap(str));
//            childChildTV.setTextColor(Color.parseColor("#00ffff"));
        }
        public void update3() {
            user_head2.setImageResource(R.mipmap.appicon);
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (mChilds != null && mChilds.size() > 0)
            return mChilds.get(groupPosition);
        return null;
    }

    @Override
    public int getGroupCount() {
        return mChilds != null ? mChilds.size() : 0;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        GroupHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.child_group_item, null);
            holder = new GroupHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.update(mChilds.get(groupPosition));
        return convertView;
    }

    /**
     * @author weixaun
     *
     *         Holder优化
     * */
    class GroupHolder {

        private TextView childGroupTV;

        public GroupHolder(View v) {
            childGroupTV = (TextView) v.findViewById(R.id.childGroupTV);
        }

        public void update(DataManager.ZZplayer.DataBean.UserlistPageBean.DepartmentlistBean model) {
            childGroupTV.setText(model.department_name);
//            childGroupTV.setTextColor(model.getGroupColor());
        }

    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        /**
         * ==============================================
         * 此处必须返回true，否则无法响应子项的点击事件===============
         * ==============================================
         **/
        return true;
    }
}
