package com.example.a25908.partybuild.Fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.example.a25908.partybuild.Activitys.PartyPayActivity;
import com.example.a25908.partybuild.Activitys.PartyVideoActivity;
import com.example.a25908.partybuild.Activitys.StudyActivity;
import com.example.a25908.partybuild.Activitys.SupportActivity;
import com.example.a25908.partybuild.Adapters.MyGridAdapter;
import com.example.a25908.partybuild.Dialogs.WaitDialog;
import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.example.a25908.partybuild.Utils.URLconstant;
import com.example.a25908.partybuild.Views.ImageCycleView;
import com.example.a25908.partybuild.Views.Toast;
import com.lidroid.xutils.ViewUtils;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.List;

import static com.example.a25908.partybuild.Utils.FileUtils.FileNewsExists;
import static com.example.a25908.partybuild.Utils.URLconstant.DOCUMENTROOM;
import static com.example.a25908.partybuild.Utils.URLconstant.FAQ;
import static com.example.a25908.partybuild.Utils.URLconstant.PARTYCOMM;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;

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

    PartySharePreferences psp;
    public static Handler handler;
    WaitDialog wd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_fragment1, container, false);
        ViewUtils.inject(getActivity());
        wd=new WaitDialog(getActivity());
        psp=PartySharePreferences.getLifeSharedPreferences();
        FileNewsExists();

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
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0://党委通知
                        wd.dismiss();
                        startActivity(new Intent(getActivity(), PartyCommitteeActivity.class));
                        break;
                    case 1://掌上党校
                        startActivity(new Intent(getActivity(), PalmPartySchoolActivity.class));
                        break;
                    case 2:
                        //党费缴纳
                        startActivity(new Intent(getActivity(), PartyPayActivity.class));
                        break;
                    case 3:
                        //党建视频
                        wd.dismiss();
                        startActivity(new Intent(getActivity(), PartyVideoActivity.class));
                        break;
                    case 4://学习园地
                        wd.dismiss();
                        startActivity(new Intent(getActivity(), StudyActivity.class));
                        break;
                    case 5:
                        //在线答疑
                        wd.dismiss();
                        startActivity(new Intent(getActivity(), AnswerActivity.class));
                        break;
                    case 6:
                        //党员扶持
                        wd.dismiss();
                        startActivity(new Intent(getActivity(), SupportActivity.class));
                        break;
                    case 7://文档中心
                        wd.dismiss();
                        startActivity(new Intent(getActivity(), FilesActivity.class));
                        break;
                    case 8:
                        //问卷调查
                        wd.dismiss();
//                        startActivity(new Intent(getActivity(), QuestionSurveyActivity.class));
                        break;
                    case 500:
                        wd.dismiss();
                        Toast.show("暂无数据!");
                        break;
                }
            }
        };
        return v;
    }

    public void gvShow(){
        GridView gridView= (GridView) v.findViewById(R.id.gridview);
        MyGridAdapter adapter=new MyGridAdapter(getActivity(),text1,null,imgs);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GsonRequest Request;
                switch (position){
                    case 0://党委通知
                        wd.show();
                        Request = new GsonRequest(URLINSER +PARTYCOMM, RequestMethod.GET);
                        Request.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                        Request.add("KeyNo", psp.getUSERID());
                        Request.add("deviceId", new Build().MODEL);
                        Request.add("type", 0);
//                        Request.add("PageIndex",);
//                        Request.add("PageSize",);
                        CallServer.getInstance().add(getActivity(), Request, GsonCallBack.getInstance(), 0x004, true, false, true);
                        break;
                    case 1://掌上党校
                        startActivity(new Intent(getActivity(), PalmPartySchoolActivity.class));
                        break;
                    case 2: //党费缴纳
                        startActivity(new Intent(getActivity(), PartyPayActivity.class));
                        break;
                    case 3: //党建视频
                        Toast.show("功能还在开发中。。。");
//                        wd.show();
//                        Request = new GsonRequest(URLINSER +PARTYVIDEO, RequestMethod.POST);
//                        Request.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
//                        Request.add("KeyNo", psp.getUSERID());
//                        Request.add("deviceId", new Build().MODEL);
//                        Request.add("type",1);
////                        Request.add("PageIndex",);
////                        Request.add("PageSize",);
//                        CallServer.getInstance().add(getActivity(), Request, GsonCallBack.getInstance(), 0x009, true, false, true);

                        break;
                    case 4://学习园地
                        wd.show();
                        Request = new GsonRequest(URLINSER +PARTYCOMM, RequestMethod.GET);
                        Request.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                        Request.add("KeyNo", psp.getUSERID());
                        Request.add("deviceId", new Build().MODEL);
                        Request.add("type", 1);
//                        Request.add("PageIndex",);
//                        Request.add("PageSize",);
                        CallServer.getInstance().add(getActivity(), Request, GsonCallBack.getInstance(), 0x005, true, false, true);

                        break;
                    case 5: //在线答疑
                        wd.show();
                        Request= new GsonRequest(URLINSER +FAQ, RequestMethod.GET);
                        Request.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                        Request.add("deviceId", new Build().MODEL);
                        Request.add("KeyNo", psp.getUSERID());
//                        Request.add("PageIndex",);
//                        Request.add("PageSize",);
                        CallServer.getInstance().add(getActivity(), Request, GsonCallBack.getInstance(), 0x0010, true, false, true);
                        break;
                    case 6: //党员扶持
                        wd.show();
                        Request= new GsonRequest(URLINSER +PARTYCOMM, RequestMethod.GET);
                        Request.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                        Request.add("KeyNo", psp.getUSERID());
                        Request.add("deviceId", new Build().MODEL);
                        Request.add("type", 2);
//                        Request.add("PageIndex",);
//                        Request.add("PageSize",);
                        CallServer.getInstance().add(getActivity(), Request, GsonCallBack.getInstance(), 0x006, true, false, true);
                        break;
                    case 7://文档中心
                        wd.show();
                        Request= new GsonRequest(URLINSER +DOCUMENTROOM, RequestMethod.GET);
                        Request.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                        Request.add("deviceId", new Build().MODEL);
                        Request.add("KeyNo", psp.getUSERID());
//                        Request.add("PageIndex",);
//                        Request.add("PageSize",);
                        CallServer.getInstance().add(getActivity(), Request, GsonCallBack.getInstance(), 0x008, true, false, true);

                        break;
                    case 8: //问卷调查
                        Toast.show("此模块开发中...!");
//                        wd.show();
//                        Request= new GsonRequest(URLINSER +SURVEY, RequestMethod.GET);
//                        Request.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
//                        Request.add("KeyNo", psp.getUSERID());
//                        Request.add("deviceId", new Build().MODEL);
//                        CallServer.getInstance().add(getActivity(), Request, GsonCallBack.getInstance(), 0x0011, true, false, true);
                        break;

                }
            }
        });
    }

}
