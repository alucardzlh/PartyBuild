package com.example.a25908.partybuild.Activitys;

import android.content.Intent;
import android.os.Bundle;

import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.yolanda.nohttp.RequestMethod;

import static com.example.a25908.partybuild.Utils.URLconstant.NewVer;
import static com.example.a25908.partybuild.Utils.URLconstant.RSA;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;

/**
 * loading界面
 */
public class WelcomeActivity extends BaseActivity {
    PartySharePreferences psp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        PartySharePreferences.init(this);
        psp=PartySharePreferences.getLifeSharedPreferences();
        GsonRequest Request = new GsonRequest(URLINSER +RSA, RequestMethod.POST);
        Request.add("token", MD5.MD5s("ls@qq.com" + "123123"));
        Request.add("KeyNo", "ls@qq.com");
        Request.add("deviceId", "123123");
        CallServer.getInstance().add(WelcomeActivity.this, Request, GsonCallBack.getInstance(), 0x250, true, false, true);
        GsonRequest Request2 = new GsonRequest(URLINSER +NewVer, RequestMethod.POST);
        Request2.add("token", MD5.MD5s("1" + "123"));
        Request2.add("KeyNo", "1");
        Request2.add("deviceId", "123");
        CallServer.getInstance().add(WelcomeActivity.this, Request2, GsonCallBack.getInstance(), 0x251, true, false, true);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);
                    finish();
//                    if(psp.getLoginStatus()==true){
//                        startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
//                    }
//                    else
                    if (psp.getAppStatus()==true){
                        startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                    }else{
                        startActivity(new Intent(WelcomeActivity.this,viewPagerActivity.class));
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}
