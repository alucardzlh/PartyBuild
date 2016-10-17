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

import com.example.a25908.partybuild.Adapters.StudyListAdapter;
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
 * 学习园地
 * @author yusi
 *
 */
public class StudyActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    ImageView returnT;
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.listStudy)
    ListView listStudy;
    public static Handler handler;
    PartySharePreferences psp;
    WaitDialog waitDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        ViewUtils.inject(this);
        psp=PartySharePreferences.getLifeSharedPreferences();
        title.setText("学习园地");
        waitDialog = new WaitDialog(this);
        returnT.setVisibility(View.VISIBLE);
        returnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        waitDialog.dismiss();
                        startActivity(new Intent(StudyActivity.this,DetailsPageActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        break;
                }
            }
        };
    }
    public void init(){//模拟数据
        StudyListAdapter adapter=new StudyListAdapter(StudyActivity.this, DataManager.paertyCommList.data.commentList);
        listStudy.setAdapter(adapter);
        listStudy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                waitDialog.show();
                GsonRequest Request = new GsonRequest(URLINSER +PARTYDETAILS, RequestMethod.GET);
                Request.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                Request.add("KeyNo", psp.getUSERID());
                Request.add("deviceId", new Build().MODEL);
                Request.add("username", psp.getUSERNAME());
                Request.add("newsid", DataManager.paertyCommList.data.commentList.get(i).newsid);
                Request.add("type", 1);
                CallServer.getInstance().add(StudyActivity.this, Request, GsonCallBack.getInstance(), 0x0051, true, false, true);

            }
        });

    }
}
