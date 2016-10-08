package com.example.a25908.partybuild.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a25908.partybuild.Adapters.MyPartyPayListAdapter;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的党费
 */
public class MyPartyPayActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    ImageView returnT;
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.list_pay)
    ListView list_pay;
    @ViewInject(R.id.nullshuju)
    TextView nullshuju;
    @ViewInject(R.id.pay_bar)
    RelativeLayout pay_bar;
    @ViewInject(R.id.pay_pay)
    Button paypay;
    @ViewInject(R.id.qiam_pay)
    TextView qiam_pay;
    MyPartyPayListAdapter adapter;
    List<DataManager.MyPartyPay.DataBean.PartyMemberlistBean> list1;
    public static android.os.Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_party_pay);
        ViewUtils.inject(this);
        Intent intent = getIntent();
        if (intent.getIntExtra("falg",0)==1){
            title.setText("查询缴费");
            returnT.setVisibility(View.VISIBLE);
            returnT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        else {
            title.setText("我的党费");
            returnT.setVisibility(View.VISIBLE);
            returnT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
//                        Toast.show(adapter.getAllmoney()+"");
                        qiam_pay.setText(adapter.getAllmoney()+" ");
                        if (adapter.getAllmoney()==0){
                            pay_bar.setVisibility(View.GONE);
                        }else {
                            pay_bar.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            }
        };
        init();
    }
    public void init(){
        list1= new ArrayList<>();
        list1 = DataManager.myPartyPaylist.data.partyMemberlist;
        if (!list1.isEmpty()) {
            nullshuju.setVisibility(View.GONE);
            list_pay.setVisibility(View.VISIBLE);
            adapter = new MyPartyPayListAdapter(MyPartyPayActivity.this, list1);
            list_pay.setAdapter(adapter);

        }
        else {
            nullshuju.setVisibility(View.VISIBLE);
            list_pay.setVisibility(View.GONE);
        }

        paypay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyPartyPayActivity.this,PayDetailsActivity.class).putExtra("pay",adapter.getAllmoney()));
            }
        });


    }
}
