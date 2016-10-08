package com.example.a25908.partybuild.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;

import java.io.IOException;
import java.util.List;

import Decoder.BASE64Decoder;

/**
 * 党员扶持的adapter
 * Created by weixuan on 2016/9/2.
 */

public class SupportAdapter extends BaseAdapter {
    private Context context;
    private List<DataManager.paertyComm.DataBean.CommentListBean> list;

    public SupportAdapter(Context context,List<DataManager.paertyComm.DataBean.CommentListBean> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        sViewHolder viewHolder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item_support,null);
            viewHolder = new sViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.item_sa_iv2);
            viewHolder.title = (TextView) view.findViewById(R.id.item_sa_tx1);
            viewHolder.context = (TextView) view.findViewById(R.id.item_sa_tx2);
            viewHolder.time = (TextView) view.findViewById(R.id.item_time2);
            viewHolder.item_dt_dzan = (TextView) view.findViewById(R.id.item_dt_dzan);

            view.setTag(viewHolder);
        }
        else {
            viewHolder = (sViewHolder) view.getTag();
        }
        try {
            BASE64Decoder decode = new BASE64Decoder();
            byte[] b = decode.decodeBuffer(list.get(i).title_img);
            Glide.with(context).load(b).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.imageView);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        viewHolder.imageView.setImageBitmap(FileUtils.stringtoBitmap(list.get(i).title_img));
        viewHolder.time.setText(list.get(i).add_time+"");
        viewHolder.title.setText(list.get(i).title+"");
        viewHolder.context.setText(list.get(i).describes+"");
        viewHolder.item_dt_dzan.setText("浏览 "+list.get(i).browse+"");
        return view;
    }

    class sViewHolder{
        public ImageView imageView;//右边图片
        public TextView title;//标题
        public TextView context;//内容
        public TextView item_dt_dzan;//赞
        public TextView time;//时间

    }
}
