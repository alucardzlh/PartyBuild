package com.example.a25908.partybuild.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;

import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ExpandableListView父类分组的适配器
 * Created by weixuan on 16/10/11.
 */

public class ParentAdapter extends BaseExpandableListAdapter {

    private Context mContext;// 上下文

    private List<DataManager.ZZplayer.DataBean.UserlistPageBean> mParents;// 数据源

    private OnChildTreeViewClickListener mTreeViewClickListener;// 点击子ExpandableListView子项的监听

    public ParentAdapter(Context context, List<DataManager.ZZplayer.DataBean.UserlistPageBean> parents) {
        this.mContext = context;
        this.mParents = parents;
    }

    @Override
    public DataManager.ZZplayer.DataBean.UserlistPageBean.DepartmentlistBean getChild(int groupPosition, int childPosition) {
        return mParents.get(groupPosition).Departmentlist.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mParents.get(groupPosition).Departmentlist != null ? mParents
                .get(groupPosition).Departmentlist.size() : 0;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isExpanded, View convertView, ViewGroup parent) {

        final ExpandableListView eListView = getExpandableListView();

        ArrayList<DataManager.ZZplayer.DataBean.UserlistPageBean.DepartmentlistBean> childs = new ArrayList<DataManager.ZZplayer.DataBean.UserlistPageBean.DepartmentlistBean>();

        final DataManager.ZZplayer.DataBean.UserlistPageBean.DepartmentlistBean child = getChild(groupPosition, childPosition);

        childs.add(child);

        final ChildAdapter childAdapter = new ChildAdapter(this.mContext,
                childs);

        eListView.setAdapter(childAdapter);

        /**
         * @author weixaun
         *
         *         点击子ExpandableListView子项时，调用回调接口
         * */
        eListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView arg0, View arg1,
                                        int groupIndex, int childIndex, long arg4) {

                if (mTreeViewClickListener != null) {

                    mTreeViewClickListener.onClickPosition(groupPosition,
                            childPosition, childIndex);
                }
                return false;
            }
        });


        /**
         * @author weixuan
         *
         *         子ExpandableListView展开时，因为group只有一项，所以子ExpandableListView的总高度=
         *         （子ExpandableListView的child数量 + 1 ）* 每一项的高度
         * */
        eListView.setOnGroupExpandListener(new OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

                LayoutParams lp = new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, (child
                        .Userlist.size() + 1)
                        * (int) mContext.getResources().getDimension(
                        R.dimen.parent_expandable_list_height));
                eListView.setLayoutParams(lp);
            }
        });

        /**
         * @author weixaun
         *
         *         子ExpandableListView关闭时，此时只剩下group这一项，
         *         所以子ExpandableListView的总高度即为一项的高度
         * */
        eListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

                LayoutParams lp = new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, (int) mContext
                        .getResources().getDimension(
                                R.dimen.parent_expandable_list_height));
                eListView.setLayoutParams(lp);
            }
        });
        return eListView;

    }

    /**
     * @author weixuan
     *
     *         动态创建子ExpandableListView
     * */
    public ExpandableListView getExpandableListView() {
        ExpandableListView mExpandableListView = new ExpandableListView(
                mContext);
        LayoutParams lp = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, (int) mContext
                .getResources().getDimension(
                        R.dimen.parent_expandable_list_height));
        mExpandableListView.setLayoutParams(lp);
//        mExpandableListView.setDividerHeight(0);// 取消group项的分割线
//        mExpandableListView.setChildDivider(null);// 取消child项的分割线
        mExpandableListView.setGroupIndicator(null);// 取消展开折叠的指示图标
        return mExpandableListView;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mParents.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return mParents != null ? mParents.size() : 0;
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
                    R.layout.parent_group_item, null);
            holder = new GroupHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.update(mParents.get(groupPosition));
        return convertView;
    }

    /**
     * @author weixaun
     *
     *         Holder优化
     * */
    class GroupHolder {

        private TextView parentGroupTV;

        public GroupHolder(View v) {
            parentGroupTV = (TextView) v.findViewById(R.id.parentGroupTV);
        }

        public void update(DataManager.ZZplayer.DataBean.UserlistPageBean model) {
            parentGroupTV.setText(model.unit_name);
//            parentGroupTV.setTextColor(model.getGroupColor());
        }
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    /**
     * @author weixuan
     *
     *         设置点击子ExpandableListView子项的监听
     * */
    public void setOnChildTreeViewClickListener(
            OnChildTreeViewClickListener treeViewClickListener) {
        this.mTreeViewClickListener = treeViewClickListener;
    }

    /**
     * @author weixuan
     *
     *         点击子ExpandableListView子项的回调接口
     * */
    public interface OnChildTreeViewClickListener {

        void onClickPosition(int parentPosition, int groupPosition,
                             int childPosition);
    }
}
