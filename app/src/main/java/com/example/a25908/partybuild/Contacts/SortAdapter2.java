package com.example.a25908.partybuild.Contacts;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.a25908.partybuild.Activitys.RemindActivity;
import com.example.a25908.partybuild.Model.SortModel;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Utils.FileUtils;
import com.example.a25908.partybuild.Views.Toast;

import java.util.HashMap;
import java.util.List;

/**
 * 提醒名册的适配器
 */
public class SortAdapter2 extends BaseAdapter implements SectionIndexer
{
	private List<SortModel> list = null;
	private Context mContext;
	private int Body;
	private boolean bl;
	ViewHolder viewHolder;
	public void setBl(boolean bl2){
		bl=bl2;
	}
	public int getBody2(){
		return Body;
	}
	// 用来控制CheckBox的选中状况
	private static HashMap<Integer, Boolean> isSelected;
	public void setSaveCB(boolean bl3,int p){
		list.get(p).checked = bl3;
	}
	public SortAdapter2(Context mContext , List<SortModel> list)
	{
		super();
		this.list = list;
		this.mContext = mContext;
		isSelected = new HashMap<Integer, Boolean>();
		bl=false;
		// 初始化数据
		initDate();
	}
	// 初始化isSelected的数据
	private void initDate() {
		for (int i = 0; i < list.size(); i++) {
			getIsSelected().put(i, false);
		}
	}

	// when the data changed , call updateListView() to update
	public void updateListView(List<SortModel> list)
	{
		this.list = list;
		notifyDataSetChanged();
	}


	public int getCount()
	{
		return this.list.size();
	}

	public Object getItem(int pos)
	{
		return this.list.get(pos);
	}

	public long getItemId(int pos)
	{
		return pos;
	}

	public View getView(final int pos, View view, ViewGroup group)
	{
		viewHolder = null;
		final SortModel mContent = list.get(pos);
		if (view == null)
		{
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.user_list_item2, null);

			viewHolder.tvImg = (ImageView) view.findViewById(R.id.user_head);
			viewHolder.tvId = (TextView) view.findViewById(R.id.txt_user_id);
			viewHolder.tvName = (TextView) view.findViewById(R.id.txt_user_name);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.txt_catalog);
			viewHolder.tvInfo = (TextView) view.findViewById(R.id.txt_user_list_info);
			viewHolder.checkBox = (CheckBox) view.findViewById(R.id.pay_cb);
			viewHolder.linearLayout = (LinearLayout) view.findViewById(R.id.lin_tx);
			view.setTag(viewHolder);
		}
		else
			viewHolder = (ViewHolder) view.getTag();

		// get position and get the first letter
		int section = getSectionForPosition(pos);

		if(pos == getPositionForSection(section))
		{
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.sortLetters);
		}
		else
			viewHolder.tvLetter.setVisibility(View.GONE);
		try{
			if(TextUtils.isEmpty(this.list.get(pos).img)){
				viewHolder.tvImg.setImageResource(R.mipmap.appicon);
			}else{
				viewHolder.tvImg.setImageBitmap(FileUtils.stringtoBitmap(this.list.get(pos).img));
			}
		}catch (NullPointerException e){
			viewHolder.tvImg.setBackgroundResource(R.mipmap.appicon);
		}
		viewHolder.tvId.setText(this.list.get(pos).id);
		viewHolder.tvName.setText(this.list.get(pos).name);
		viewHolder.tvInfo.setText(this.list.get(pos).info);
			viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (bl){
						if (isSelected.get(pos)){
							Body = pos;
							isSelected.put(pos, false);
							setIsSelected(isSelected);
							RemindActivity.handler.sendEmptyMessage(1);
						}
						else{
							RemindActivity.handler.sendEmptyMessage(0);
							Toast.show("最多能选择10个人");
						}
						return;
					}else {
						if (isSelected.get(pos)) {
							Body = pos;
							isSelected.put(pos, false);
							setIsSelected(isSelected);
							RemindActivity.handler.sendEmptyMessage(1);
						} else {
							Body = pos;
							isSelected.put(pos, true);
							setIsSelected(isSelected);
							RemindActivity.handler.sendEmptyMessage(1);
						}
					}
				}
			});

		// 根据isSelected来设置checkbox的选中状况
		viewHolder.checkBox.setChecked(getIsSelected().get(pos));
		return view;
	}

	public void setkaiguan(int b){
		viewHolder.checkBox.setChecked(getIsSelected().get(b));
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		SortAdapter2.isSelected = isSelected;
	}

	final static class ViewHolder
	{
		ImageView tvImg;
		TextView tvId;
		TextView tvLetter;
		TextView tvName;
		TextView tvInfo;
		CheckBox checkBox;
		LinearLayout linearLayout;
	}

	public int getPositionForSection(int section)
	{
		for (int i = 0; i < getCount(); i++)
		{
			String sortStr = list.get(i).sortLetters;
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section)
				return i;
		}

		return -1;
	}

	public int getSectionForPosition(int arg0)
	{
		return this.list.get(arg0).sortLetters.charAt(0);
	}


	public Object[] getSections()
	{
		return null;
	}

	private String getAlpha(String str)
	{
		String  sortStr = str.trim().substring(0, 1).toUpperCase();
		if (sortStr.matches("[A-Z]"))
			return sortStr;
		else
			return "#";
	}

}
