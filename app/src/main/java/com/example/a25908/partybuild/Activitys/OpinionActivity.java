package com.example.a25908.partybuild.Activitys;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.example.a25908.partybuild.Views.Toast;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yolanda.nohttp.RequestMethod;

import static com.example.a25908.partybuild.Utils.URLconstant.OPINION;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;

/**
 * @author yusi
 * 意见反馈
 */
public class OpinionActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    private ImageView back;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.op_et)
    private EditText op_et;
    @ViewInject(R.id.op_phone)
    private EditText op_phone;
    @ViewInject(R.id.oksend)
    private Button oksend;
    PartySharePreferences psp;
    public static Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion);
        ViewUtils.inject(this);
        psp = PartySharePreferences.getLifeSharedPreferences();
        title.setText("意见反馈");
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String content = op_et.getText().toString();
        //提交
        oksend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new WaitDialog(OpinionActivity.this).show();
                GsonRequest OpinionRequest = new GsonRequest(URLINSER + OPINION, RequestMethod.GET);
                OpinionRequest.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                OpinionRequest.add("deviceId",new Build().MODEL);
                OpinionRequest.add("username",psp.getUSERNAME());
                OpinionRequest.add("content",op_et.getText().toString());
                OpinionRequest.add("mobile",op_phone.getText().toString());
                OpinionRequest.add("KeyNo",psp.getUSERID());
                CallServer.getInstance().add(OpinionActivity.this,OpinionRequest, GsonCallBack.getInstance(),0x102,true,false,true);
            }
        });

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what==0) {
                    Toast.show("反馈成功");
                    finish();
                }
                else if (msg.what==1){
                    Toast.show("服务器出错");
                }
            }
        };
    }

}
