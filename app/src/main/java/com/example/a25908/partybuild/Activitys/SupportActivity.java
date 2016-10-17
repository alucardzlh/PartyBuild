package com.example.a25908.partybuild.Activitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a25908.partybuild.Adapters.SupportAdapter;
import com.example.a25908.partybuild.Dialogs.WaitDialog;
import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yolanda.nohttp.RequestMethod;

import static com.example.a25908.partybuild.Utils.URLconstant.PARTYDETAILS;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;

/**
 * 党员扶持
 * @author weixuan
 */
public class SupportActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    private ImageView back;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.list_sa)
    private ListView list_sa;
    private SupportAdapter adapter;
    public static Handler handler;
    PartySharePreferences psp;
    WaitDialog wd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        ViewUtils.inject(this);
        psp=PartySharePreferences.getLifeSharedPreferences();
        title.setText("党员扶持");
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        wd = new WaitDialog(this);

        init();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        wd.dismiss();
                        startActivity(new Intent(SupportActivity.this,DetailsPageActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        break;
                }
            }
        };
    }

    private void init() {
        adapter = new SupportAdapter(this, DataManager.paertyCommList.data.commentList);
        list_sa.setAdapter(adapter);
        list_sa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                wd.show();
                GsonRequest Request = new GsonRequest(URLINSER +PARTYDETAILS, RequestMethod.GET);
                Request.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                Request.add("KeyNo", psp.getUSERID());
                Request.add("deviceId", new Build().MODEL);
                Request.add("username", psp.getUSERNAME());
                Request.add("newsid", DataManager.paertyCommList.data.commentList.get(i).newsid);
                Request.add("type", 2);
                CallServer.getInstance().add(SupportActivity.this, Request, GsonCallBack.getInstance(), 0x0061, true, false, true);
            }
        });
    }
}
