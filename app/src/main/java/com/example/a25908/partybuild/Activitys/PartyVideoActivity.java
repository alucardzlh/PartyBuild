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

import com.example.a25908.partybuild.Adapters.PartyVideoCAdpter;
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

import static com.example.a25908.partybuild.Utils.URLconstant.PARTYVIDEOCOMN;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;

/**
 * 党建视频
 * @author weixuan
 */
public class PartyVideoActivity extends BaseActivity {

    int item = 0;
    @ViewInject(R.id.returnT)
    private ImageView back;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.list_pv)
    private ListView list_pv;
    private PartyVideoCAdpter adpter;
    PartySharePreferences psp;
    public static Handler handler;
    WaitDialog waitDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_video);
        ViewUtils.inject(this);
        title.setText("党建视频");
        waitDialog = new WaitDialog(this);
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
                if (msg.what==0){
                    waitDialog.dismiss();
                    startActivity(new Intent(PartyVideoActivity.this,DetailsPageActivity2.class).putExtra("flag",1).putExtra("i",item).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
            }
        };

        adpter = new PartyVideoCAdpter(PartyVideoActivity.this,DataManager.partyvideoList.data.videolistPage);
        list_pv.setAdapter(adpter);
        psp = new PartySharePreferences();
        list_pv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(PartyVideoActivity.this,DetailsPageActivity.class);
//
//
//                intent.putExtra("flag",1);
//                intent.putExtra("title",DataManager.partyvideoList.data.videolistPage.get(i).title);
//                intent.putExtra("time",DataManager.partyvideoList.data.videolistPage.get(i).describes);
////                intent.putExtra("img", FileUtils.stringtoBitmap(DataManager.partyvideoList.data.videolistPage.get(i).img));
//                intent.putExtra("i",i);
//                intent.putExtra("path",DataManager.partyvideoList.data.videolistPage.get(i).path);
//                intent.putExtra("content",DataManager.partyvideoList.data.videolistPage.get(i).content);
//                startActivity(intent);
                waitDialog.show();
                item = i;
                GsonRequest Request3 = new GsonRequest(URLINSER +PARTYVIDEOCOMN, RequestMethod.GET);
                Request3.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                Request3.add("KeyNo", psp.getUSERID());
                Request3.add("deviceId", new Build().MODEL);
                Request3.add("username", psp.getUSERNAME());
                Request3.add("videoid", DataManager.partyvideoList.data.videolistPage.get(i).videoid);
                CallServer.getInstance().add(PartyVideoActivity.this, Request3, GsonCallBack.getInstance(), 0x0091, true, false, true);

            }
        });
    }


}
