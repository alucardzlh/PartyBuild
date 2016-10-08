package com.example.a25908.partybuild.Activitys;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a25908.partybuild.Adapters.QuestionSurveyAdapter;
import com.example.a25908.partybuild.Dialogs.WaitDialog;
import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.a25908.partybuild.Utils.URLconstant.UPDATEUSERDATEURL;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;

/**
 * 问卷调查(废弃)
 * @author weixuan
 */
public class QuestionSurveyActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    private ImageView returnT;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.fileclear)
    private TextView tijiao2;
    @ViewInject(R.id.list_qs)
    private ListView list_qs;
    private List<Map<String,List<Map<String,Object>>>> mapList;
    public static Handler handler;
    QuestionSurveyAdapter adapter;
    WaitDialog wd;
    PartySharePreferences psp;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_survey);
        ViewUtils.inject(this);
        wd = new WaitDialog(this);
        psp = new PartySharePreferences();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0://单选
                        Map map = new HashMap();
                        map.put("topic_id",adapter.topic_id);
                        map.put("content",adapter.content);
                        Map omap = new HashMap();
                        List<Map<String,Object>> olist = new ArrayList<>();
                        omap.put("alist",olist);
                        omap.put("answer_id",adapter.answer_id);
                        mapList.add(omap);
                        break;
                    case 1:

                        break;
                }
            }
        };
        tijiao2.setVisibility(View.VISIBLE);
        tijiao2.setText("提交");
        title.setText("问卷调查");
        returnT.setVisibility(View.VISIBLE);
        returnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter=new QuestionSurveyAdapter(this,DataManager.myQuestions.data.QuestionlistPage);
        list_qs.setAdapter(adapter);

        tijiao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(QuestionSurveyActivity.this,"开发中",Toast.LENGTH_SHORT).show();
//                wd.show();
                String resultlist = gson.toJson(mapList);
                GsonRequest Request = new GsonRequest(URLINSER + UPDATEUSERDATEURL, RequestMethod.POST);
                Request.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                Request.add("KeyNo", psp.getUSERID());
                Request.add("username",psp.getUSERNAME());
                Request.add("deviceId", new Build().MODEL);
                Request.add("resultlist", resultlist);
                CallServer.getInstance().add(QuestionSurveyActivity.this, Request, GsonCallBack.getInstance(), 0x0012, true, false, true);
            }
        });
    }
}
