package com.example.a25908.partybuild.Contacts;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.a25908.partybuild.Model.SortModel;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Utils.FileUtils;

import java.util.List;


public class SortAdapter extends BaseAdapter implements SectionIndexer
{
	private List<SortModel> list = null;
	private Context mContext;

	public SortAdapter( Context mContext , List<SortModel> list)
	{
		super();
		this.list = list;
		this.mContext = mContext;
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

	public View getView(int pos, View view, ViewGroup group)
	{
		ViewHolder viewHolder = null;
		final SortModel mContent = list.get(pos);
		if (view == null)
		{
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.user_list_item, null);

			viewHolder.tvImg = (ImageView) view.findViewById(R.id.user_head);
			viewHolder.tvId = (TextView) view.findViewById(R.id.txt_user_id);
			viewHolder.tvName = (TextView) view.findViewById(R.id.txt_user_name);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.txt_catalog);
			viewHolder.tvInfo = (TextView) view.findViewById(R.id.txt_user_list_info);
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
			if(this.list.get(pos).img.equals("")){
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

		return view;
	}

	final static class ViewHolder
	{
		ImageView tvImg;
		TextView tvId;
		TextView tvLetter;
		TextView tvName;
		TextView tvInfo;
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
