package com.example.a25908.partybuild.Activitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a25908.partybuild.Dialogs.WaitDialog;
import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yolanda.nohttp.RequestMethod;

import static com.example.a25908.partybuild.Utils.URLconstant.FAQADD;
import static com.example.a25908.partybuild.Utils.URLconstant.PARTYCOMM;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;

/**
 * 在线答疑提交问题
 * @author weixuan
 */
public class QuestionsActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    private ImageView returnT;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.tijiao)
    public Button tijiao;
    @ViewInject(R.id.wentifaq233)
    public EditText wentifaq233;
    @ViewInject(R.id.tv_size)
    public EditText tv_size;
    PartySharePreferences psp;
    public static Handler handler;
    public static boolean QAflag=false;
    WaitDialog wd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        ViewUtils.inject(this);
        wd=new WaitDialog(this);
        psp=PartySharePreferences.getLifeSharedPreferences();
        title.setText("在线答疑");
        returnT.setVisibility(View.VISIBLE);
        returnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wentifaq233.getText().toString().equals("")){
                    com.example.a25908.partybuild.Views.Toast.show("");
                }else{
                    wd.show();
                    GsonRequest Request = new GsonRequest(URLINSER +FAQADD, RequestMethod.GET);
                    Request.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                    Request.add("KeyNo", psp.getUSERID());
                    Request.add("deviceId", new Build().MODEL);
                    Request.add("username", psp.getUSERNAME());
                    Request.add("problem",wentifaq233.getText().toString());
                    CallServer.getInstance().add(QuestionsActivity.this, Request, GsonCallBack.getInstance(), 0x00101, true, false, true);

                }
            }
        });
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        com.example.a25908.partybuild.Views.Toast.show("提交成功!");
                        QAflag=true;
                        wd.dismiss();
                        finish();
                        break;
                    case 1:
                        com.example.a25908.partybuild.Views.Toast.show("提交失败!");
                        break;
                }
            }
        };
    }
}
