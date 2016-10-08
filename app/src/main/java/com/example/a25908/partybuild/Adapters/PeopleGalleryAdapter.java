package com.example.a25908.partybuild.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
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
 * @author yusi
 * 人物长廊界面Adapter
 */
public class PeopleGalleryAdapter extends BaseAdapter {
    private Context context;
    private List<DataManager.MyPALMPARTY.DataBean.CommentListBean> list;
    public PeopleGalleryAdapter(Context context,List<DataManager.MyPALMPARTY.DataBean.CommentListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
            view = LayoutInflater.from(context).inflate(R.layout.activity_people_gallery_item, null);
            vh = new ViewHolder();
            vh.imgLayou=(FrameLayout) view.findViewById(R.id.imgLayou);
            vh.imgtxt=(TextView) view.findViewById(R.id.imgtxt);
            vh.img_pgi = (ImageView) view.findViewById(R.id.img_pgi);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        try {
            BASE64Decoder decode = new BASE64Decoder();
            byte[] b = decode.decodeBuffer(list.get(position).title_img);
            Glide.with(context).load(b).diskCacheStrategy(DiskCacheStrategy.ALL).into(vh.img_pgi);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Bitmap bitmap = FileUtils.stringtoBitmap(list.get(position).title_img);
//        vh.img_pgi.setImageBitmap(bitmap);
        vh.imgtxt.setText(list.get(position).title);
        return view;
    }

    public class ViewHolder {
        FrameLayout imgLayou;
        TextView imgtxt;

        ImageView img_pgi;
    }
}
