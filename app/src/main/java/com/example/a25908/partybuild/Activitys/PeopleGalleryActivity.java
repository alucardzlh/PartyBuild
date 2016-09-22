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

import com.example.a25908.partybuild.Adapters.PeopleGalleryAdapter;
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

import java.util.ArrayList;
import java.util.List;

import static com.example.a25908.partybuild.Utils.URLconstant.PARTYDETAILS;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;

/**
 * @author yusi
 *人物长廊
 */
public class PeopleGalleryActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    private ImageView back;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.PeopleGallery_list)
    private ListView PeopleGallery_list;
    PartySharePreferences psp;
    private WaitDialog waitDialog;
    public static Handler handler;

    PeopleGalleryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_gallery);
        ViewUtils.inject(this);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what==1)
                startActivity(new Intent(PeopleGalleryActivity.this,DetailsPageActivity.class));
            }
        };
        waitDialog = new WaitDialog(PeopleGalleryActivity.this);
        waitDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        psp = new PartySharePreferences();
        title.setText("人物长廊");
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              finish();
            }
        });
        List<DataManager.MyPALMPARTY.DataBean.CommentListBean> list=new ArrayList<>();
        list = DataManager.palmparty.data.commentList;
        adapter=new PeopleGalleryAdapter(PeopleGalleryActivity.this,list);
        PeopleGallery_list.setAdapter(adapter);
        PeopleGallery_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GsonRequest ppsRequest = new GsonRequest(URLINSER + PARTYDETAILS, RequestMethod.GET);
                ppsRequest.add("KeyNo",psp.getUSERID());//psp.getUSERID()
                ppsRequest.add("type",3);
                ppsRequest.add("newsid",DataManager.palmparty.data.commentList.get(i).newsid);
                ppsRequest.add("username",psp.getUSERNAME());
                ppsRequest.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));//MD5.MD5s(psp.getUSERID() + new Build().MODEL)
                ppsRequest.add("deviceId",new Build().MODEL);//new Build().MODEL
                CallServer.getInstance().add(PeopleGalleryActivity.this,ppsRequest, GsonCallBack.getInstance(),0x2023,true,false,true);
            }
        });
    }
}
