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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a25908.partybuild.Activitys.AboutsActivity;
import com.example.a25908.partybuild.Activitys.LoginActivity;
import com.example.a25908.partybuild.Activitys.MyFilesActivity;
import com.example.a25908.partybuild.Activitys.MyPartyPayActivity;
import com.example.a25908.partybuild.Activitys.MydataActivity;
import com.example.a25908.partybuild.Activitys.OpinionActivity;
import com.example.a25908.partybuild.Activitys.TestWebActivity;
import com.example.a25908.partybuild.Activitys.tsteActivity;
import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.example.a25908.partybuild.Views.RoundImageView;
import com.yolanda.nohttp.RequestMethod;

import static com.example.a25908.partybuild.Utils.URLconstant.PARTYPAY;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;
import static com.example.a25908.partybuild.Utils.URLconstant.USERDATEURL;

/**
 * @author yusi
 * 个人中心
 */
public class Fragment4 extends Fragment {
    View v;
    PartySharePreferences psp;
    public static Handler handler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_fragment4, container, false);
        psp = PartySharePreferences.getLifeSharedPreferences();
        show();
         handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0://我的资料
                        startActivity(new Intent(getActivity(), MydataActivity.class));
                        break;
                    case 1://我的党费
                        startActivity(new Intent(getActivity(), MyPartyPayActivity.class));
                        break;
                }
            }
        };
        return v;
    }
    public void show(){

        RoundImageView userimg= (RoundImageView) v.findViewById(R.id.userimg);//头像
        TextView username= (TextView) v.findViewById(R.id.username);//名字
        TextView mtime= (TextView) v.findViewById(R.id.mtime);//入党时间
        username.setText(psp.getUSERNAME());
        mtime.setText(psp.getPARTYTIME());
        RelativeLayout my1= (RelativeLayout) v.findViewById(R.id.my1);
        RelativeLayout my2= (RelativeLayout) v.findViewById(R.id.my2);
        RelativeLayout my3= (RelativeLayout) v.findViewById(R.id.my3);
        RelativeLayout my4= (RelativeLayout) v.findViewById(R.id.my4);
        RelativeLayout my5= (RelativeLayout) v.findViewById(R.id.my5);
        userimg.setOnClickListener(listener);
        username.setOnClickListener(listener);
        mtime.setOnClickListener(listener);
        my1.setOnClickListener(listener);
        my2.setOnClickListener(listener);
        my3.setOnClickListener(listener);
        my4.setOnClickListener(listener);
        my5.setOnClickListener(listener);
    }
    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.userimg:
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    break;
                case R.id.username:
                    startActivity(new Intent(getActivity(), TestWebActivity.class));
                    break;
                case R.id.mtime:
                    startActivity(new Intent(getActivity(), tsteActivity.class));
                    break;
                case R.id.my1:
//                    ?KeyNo=1&deviceId=123&token=2cfd4560539f887a5e420412b370b361
                    GsonRequest LoginRequest = new GsonRequest(URLINSER +USERDATEURL, RequestMethod.GET);
                    LoginRequest.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                    LoginRequest.add("KeyNo", psp.getUSERID());
                    LoginRequest.add("deviceId", new Build().MODEL);
                    CallServer.getInstance().add(getActivity(), LoginRequest, GsonCallBack.getInstance(), 0x002, true, false, true);
                    break;
                case R.id.my2:
                    startActivity(new Intent(getActivity(), MyFilesActivity.class));
                    break;
                case R.id.my3:
                    GsonRequest PartyPayRequest = new GsonRequest(URLINSER + PARTYPAY , RequestMethod.GET);
                    PartyPayRequest.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                    PartyPayRequest.add("deviceId",new Build().MODEL);
                    PartyPayRequest.add("KeyNo",psp.getUSERID());
                    CallServer.getInstance().add(getActivity(),PartyPayRequest, GsonCallBack.getInstance(),0x103,true,false,true);
                    break;
                case R.id.my4:
                    startActivity(new Intent(getActivity(), AboutsActivity.class));
                    break;
                case R.id.my5:
                    startActivity(new Intent(getActivity(), OpinionActivity.class));
                    break;
            }
        }
    };
}
