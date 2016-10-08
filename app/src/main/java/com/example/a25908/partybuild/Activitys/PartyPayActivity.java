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

import static com.example.a25908.partybuild.Utils.URLconstant.PARTYPAY;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;

/**
 * 党费缴纳
 * @author weixuan
 */
public class PartyPayActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    private ImageView back;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.chaxun)
    private Button chaxun;
    @ViewInject(R.id.pay_name)
    private EditText pay_name;
    @ViewInject(R.id.pay_num)
    private EditText pay_num;
    private PartySharePreferences psp;
    private WaitDialog wd;
    public static Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_pay);
        ViewUtils.inject(this);
        psp = new PartySharePreferences();
        wd = new WaitDialog(PartyPayActivity.this);
        title.setText("党费缴纳");
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        wd.dismiss();
                        startActivity(new Intent(PartyPayActivity.this,MyPartyPayActivity.class).putExtra("falg",1));
                        break;
                }
            }
        };

        chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wd.show();
                GsonRequest PartyPayRequest = new GsonRequest(URLINSER + PARTYPAY, RequestMethod.GET);
                PartyPayRequest.add("token", MD5.MD5s(1 + new Build().MODEL));
                PartyPayRequest.add("deviceId", new Build().MODEL);
                PartyPayRequest.add("mobile", pay_num.getText().toString());
                PartyPayRequest.add("KeyNo", pay_name.getText().toString());
                PartyPayRequest.add("KeyNo", 1);
                CallServer.getInstance().add(PartyPayActivity.this, PartyPayRequest, GsonCallBack.getInstance(), 0x104, true, false, true);
            }
        });
    }
}
