package com.example.a25908.partybuild.Activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a25908.partybuild.Adapters.AnswerAdapter;
import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.example.a25908.partybuild.Views.Toast;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yolanda.nohttp.RequestMethod;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.a25908.partybuild.Utils.URLconstant.FAQ;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;

/**
 * 在线答疑
 * @author weixuan
 */
public class AnswerActivity extends BaseActivity {
    @ViewInject(R.id.list_an)
    private ListView listView;
    @ViewInject(R.id.questions_aa)
    private LinearLayout questions;
    @ViewInject(R.id.returnT)
    private ImageView back;
    @ViewInject(R.id.title)
    private TextView title;
    private AnswerAdapter answerAdapter;
    AlertDialog.Builder builder;
    public static AlertDialog dialog;
    PartySharePreferences psp;
    public static  Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        ViewUtils.inject(this);
        psp=PartySharePreferences.getLifeSharedPreferences();
        title.setText("在线答疑");
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        init();

        questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AnswerActivity.this,QuestionsActivity.class));
            }
        });
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        answerAdapter = new AnswerAdapter(AnswerActivity.this, DataManager.FAQmarList.data.onlineAnswerlistPage);
                        listView.setAdapter(answerAdapter);
                        answerAdapter.notifyDataSetChanged();
                        break;
                    case 1:
                        break;
                }
            }
        };
    }

    private void init() {
        answerAdapter = new AnswerAdapter(this, DataManager.FAQmarList.data.onlineAnswerlistPage);
        listView.setAdapter(answerAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /**
                 * //回答状态（0为审核中  1为待回答  2为已答复  3拒审）默认为0
                 */
                if (DataManager.FAQmarList.data.onlineAnswerlistPage.get(i).answer_state.equals("2")){
                    startActivity(new Intent(AnswerActivity.this,AnswerDetailsActivity.class).putExtra("position",i));
                }else {
                    String sate=null;
                    if (DataManager.FAQmarList.data.onlineAnswerlistPage.get(i).answer_state.equals("0")){
                        sate="您的提问正在审核中...,请稍后!";
                    } else if (DataManager.FAQmarList.data.onlineAnswerlistPage.get(i).answer_state.equals("1")){
                        sate="您的提问正等待回答...,请稍后!";
                    } else if (DataManager.FAQmarList.data.onlineAnswerlistPage.get(i).answer_state.equals("3")){
                        sate="您的提问内容中含敏感词汇,已被拒审!";
                    }else {
                    }
                    builder = new AlertDialog.Builder(AnswerActivity.this);
                    builder.setTitle("提示");
                    builder.setMessage(sate);
                    dialog = builder.create();
                    dialog.show();
                }

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(QuestionsActivity.QAflag==true){
            QuestionsActivity.QAflag=false;
            GsonRequest Request= new GsonRequest(URLINSER +FAQ, RequestMethod.GET);
            Request.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
            Request.add("deviceId", new Build().MODEL);
            Request.add("KeyNo", psp.getUSERID());
//                        Request.add("PageIndex",);
//                        Request.add("PageSize",);
            CallServer.getInstance().add(AnswerActivity.this, Request, GsonCallBack.getInstance(), 0x001011, true, false, true);
        }
    }


}
