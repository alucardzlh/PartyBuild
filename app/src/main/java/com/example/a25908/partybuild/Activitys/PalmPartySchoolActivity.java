package com.example.a25908.partybuild.Activitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yolanda.nohttp.RequestMethod;

import static com.example.a25908.partybuild.Utils.URLconstant.PALMPARTY;
import static com.example.a25908.partybuild.Utils.URLconstant.TCOP;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;

/**
 * @author  yusi
 * 掌上党校
 */
public class PalmPartySchoolActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    private ImageView back;
    @ViewInject(R.id.title)
    private TextView title;

    @ViewInject(R.id.pps1)
    private RelativeLayout pps1;
    @ViewInject(R.id.pps2)
    private RelativeLayout pps2;
    @ViewInject(R.id.pps3)
    private RelativeLayout pps3;
    public static Handler handler;
    PartySharePreferences psp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palm_party_school);
        ViewUtils.inject(this);
        title.setText("掌上党校");
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        pps1.setOnClickListener(listener);
        pps2.setOnClickListener(listener);
        pps3.setOnClickListener(listener);
        psp = PartySharePreferences.getLifeSharedPreferences();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        startActivity(new Intent(PalmPartySchoolActivity.this, PeopleGalleryActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(PalmPartySchoolActivity.this, TheConstitutionOfThePartyActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(PalmPartySchoolActivity.this, PartydisciplineActivity.class));
                        break;
                }
            }
        };
    }
    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.pps1://人物长廊
                    GsonRequest ppsRequest = new GsonRequest(URLINSER + PALMPARTY, RequestMethod.GET);
                    ppsRequest.add("KeyNo",psp.getUSERID());//psp.getUSERID()
                    ppsRequest.add("type",3);
                    ppsRequest.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));//MD5.MD5s(psp.getUSERID() + new Build().MODEL)
                    ppsRequest.add("deviceId",new Build().MODEL);//new Build().MODEL
                    CallServer.getInstance().add(PalmPartySchoolActivity.this,ppsRequest, GsonCallBack.getInstance(),0x201,true,false,true);

                    break;
                case R.id.pps2://党的章程
                    GsonRequest TCTPRequest = new GsonRequest(URLINSER + TCOP, RequestMethod.GET);
                    TCTPRequest.add("KeyNo",0);
                    TCTPRequest.add("deviceId",new Build().MODEL);
                    TCTPRequest.add("token",MD5.MD5s(0+new  Build().MODEL));
                    CallServer.getInstance().add(PalmPartySchoolActivity.this,TCTPRequest, GsonCallBack.getInstance(),0x202,true,false,true);
                    break;
                case R.id.pps3:
                    GsonRequest TCTPRequest2 = new GsonRequest(URLINSER + TCOP, RequestMethod.GET);
                    TCTPRequest2.add("KeyNo",1);
                    TCTPRequest2.add("deviceId",new Build().MODEL);
                    TCTPRequest2.add("token",MD5.MD5s(1+new  Build().MODEL));
                    CallServer.getInstance().add(PalmPartySchoolActivity.this,TCTPRequest2, GsonCallBack.getInstance(),0x203,true,false,true);
//                    startActivity(new Intent(PalmPartySchoolActivity.this, PartydisciplineActivity.class));
                    break;
            }
        }
    };
}
