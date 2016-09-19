package com.example.a25908.partybuild.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Views.BaseViewHolder;

/**
 * @Description:gridview的Adapter
 * @author http://blog.csdn.net/finddreams
 */
public class MyGridAdapter extends BaseAdapter {
	private Context mContext;
	private String[] arrays1;
	private String[] arrays2;
	private int[] imgs;

	public MyGridAdapter(Context context, String[] array1, String[] array2 , int[] imgs1) {
		super();
		this.mContext = context;
		arrays1=array1;
		arrays2=array2;
		imgs=imgs1;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(arrays1.length%4==0){
			return arrays1.length;
		}else{
			return (4-arrays1.length%4)+arrays1.length;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.grid_item, parent, false);
		}
		if(arrays1.length%4!=0){
			if((arrays1.length-1)<position){
				return convertView;
			}
		}
		TextView txt = BaseViewHolder.get(convertView, R.id.txt);
		ImageView img = BaseViewHolder.get(convertView, R.id.img);

		img.setBackgroundResource(imgs[position]);
		txt.setText(arrays1[position]);
		return convertView;
	}

}
