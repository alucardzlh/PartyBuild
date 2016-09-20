package com.example.a25908.partybuild.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Views.BaseViewHolder;

import java.util.ArrayList;

/**
 * @author http://blog.csdn.net/finddreams
 * @Description:gridview的Adapter
 */
public class MyGridAdapterClaim2 extends BaseAdapter {
    private Context mContext;
    ArrayList<Drawable> myList;
    private boolean isShowDelete;//判断是否显示删除图标
    public MyGridAdapterClaim2(Context context, ArrayList<Drawable> myList1) {
        super();
        this.mContext = context;
        this.myList = myList1;
    }
    public void setIsShowDelete(boolean isShowDelete){
        this.isShowDelete=isShowDelete;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return myList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.grid_items_claim, parent, false);

        }
        final ImageView bg= (ImageView) convertView.findViewById(R.id.ivc_items);
        final ImageView imgdelete= (ImageView) convertView.findViewById(R.id.imgdelete);
        final ImageView iv = BaseViewHolder.get(convertView, R.id.ivc_items);
        iv.setImageDrawable(myList.get(position));
        if (bg.getDrawable() != null){
            if(isShowDelete){
                imgdelete.setVisibility(View.VISIBLE);
            }else{
                imgdelete.setVisibility(View.GONE);
//                iv.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        onThumbnailClick(bg);
//                    }
//                });
            }
        }
        return convertView;
    }
    //---------------显示大图
    public void onThumbnailClick(ImageView img) {
        final Dialog dialog = new Dialog(mContext, android.R.style.Theme_Black_NoTitleBar);
        ImageView imgView = getView(img);
        dialog.setContentView(imgView);
        dialog.show();
        // 点击图片消失
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
    }

    private ImageView getView(ImageView img) {
        ImageView imgView = new ImageView(mContext);
        imgView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imgView.setImageDrawable(img.getDrawable());
        return imgView;
    }

}
