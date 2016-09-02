package com.example.a25908.partybuild.Fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.a25908.partybuild.Activitys.AnswerActivity;
import com.example.a25908.partybuild.Activitys.FilesActivity;
import com.example.a25908.partybuild.Activitys.PalmPartySchoolActivity;
import com.example.a25908.partybuild.Activitys.PartyCommitteeActivity;
import com.example.a25908.partybuild.Activitys.QuestionSurveyActivity;
import com.example.a25908.partybuild.Activitys.StudyActivity;
import com.example.a25908.partybuild.Activitys.SupportActivity;
import com.example.a25908.partybuild.Adapters.MyGridAdapter;
import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.URLconstant;
import com.example.a25908.partybuild.Views.ImageCycleView;
import com.lidroid.xutils.ViewUtils;
import com.yolanda.nohttp.RequestMethod;

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
    int[] imgs={R.mipmap.item1,R.mipmap.item2,R.mipmap.item3,R.mipmap.item4,
            R.mipmap.item5,R.mipmap.item6,R.mipmap.item7,R.mipmap.item8,
            R.mipmap.item9};
    String[] text1={"党委通知","掌上党校","党费缴纳","党建视频",
            "学习园地","在线答疑","党员扶持","文档中心",
            "问卷调查"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_fragment1, container, false);
        ViewUtils.inject(getActivity());
        GsonRequest NewsRequest=new GsonRequest(URLconstant.URLINSER+URLconstant.NEWSURL, RequestMethod.GET);//新闻数据
        NewsRequest.setConnectTimeout(10000);
        NewsRequest.setReadTimeout(10000);
        NewsRequest.add("token", MD5.MD5s("" + new Build().MODEL));
        NewsRequest.add("KeyNo","");
        NewsRequest.add("deviceId",(new Build()).MODEL);
        NewsRequest.add("pageIndex",1);
        NewsRequest.add("pageSize",5);
        CallServer.getInstance().add(getActivity(),NewsRequest, GsonCallBack.getInstance(),0x111,true,false,true);
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
                        startActivity(new Intent(getActivity(), PalmPartySchoolActivity.class));
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4://学习园地
                        startActivity(new Intent(getActivity(), StudyActivity.class));
                        break;
                    case 5:
                        //在线答疑
                        startActivity(new Intent(getActivity(), AnswerActivity.class));
                        break;
                    case 6:
                        //党员扶持
                        startActivity(new Intent(getActivity(), SupportActivity.class));
                        break;
                    case 7://文档中心
                        startActivity(new Intent(getActivity(), FilesActivity.class));
                        break;
                    case 8:
                        //问卷调查
                        startActivity(new Intent(getActivity(), QuestionSurveyActivity.class));
                        break;

                }
            }
        });
    }

}
