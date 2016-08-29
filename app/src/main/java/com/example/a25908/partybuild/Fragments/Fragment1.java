package com.example.a25908.partybuild.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.a25908.partybuild.Activitys.PartyCommitteeActivity;
import com.example.a25908.partybuild.Activitys.StudyActivity;
import com.example.a25908.partybuild.Adapters.MyGridAdapter;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Views.ImageCycleView;
import com.lidroid.xutils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yusi
 * 党建首页
 */
public class Fragment1 extends Fragment {
//    @ViewInject(R.id.icv_topView)
//    ImageCycleView mImageCycleView;//轮播控件

    //    @ViewInject(R.id.gridview)
//    GridView gridView;
    View v;
    int[] imgs={R.mipmap.book,R.mipmap.book,R.mipmap.book,R.mipmap.book,
            R.mipmap.book,R.mipmap.book,R.mipmap.book,R.mipmap.book,
            R.mipmap.book};
    String[] text1={"开发中","开发中","开发中","开发中",
            "开发中","开发中","开发中","开发中",
            "开发中"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_fragment1, container, false);
        ViewUtils.inject(getActivity());
        /**
         * 轮播
         */
        ImageCycleView mImageCycleView= (ImageCycleView) v.findViewById(R.id.icv_topView);//轮播控件
        List<ImageCycleView.ImageInfo> list = new ArrayList<>();
        list.add(new ImageCycleView.ImageInfo(R.mipmap.banner, "", ""));
        list.add(new ImageCycleView.ImageInfo(R.mipmap.banner, "", ""));
        mImageCycleView.loadData(list, new ImageCycleView.LoadImageCallBack() {
            @Override
            public ImageView loadAndDisplay(ImageCycleView.ImageInfo imageInfo) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageResource(Integer.parseInt(imageInfo.image.toString()));
                return imageView;
            }
        });
        gvShow();
        return v;
    }

    public void gvShow(){
        GridView gridView= (GridView) v.findViewById(R.id.gridview);
        MyGridAdapter adapter=new MyGridAdapter(getActivity(),text1,null,imgs);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0://党委通知
                        startActivity(new Intent(getActivity(), PartyCommitteeActivity.class));
                        break;
                    case 1://掌上党校
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        startActivity(new Intent(getActivity(), StudyActivity.class));
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                }
            }
        });
    }

}
