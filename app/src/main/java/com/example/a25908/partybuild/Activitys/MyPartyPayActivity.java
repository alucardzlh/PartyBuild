package com.example.a25908.partybuild.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a25908.partybuild.Adapters.MyPartyPayListAdapter;
import com.example.a25908.partybuild.Adapters.StudyListAdapter;
import com.example.a25908.partybuild.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_party_pay);
        ViewUtils.inject(this);
        title.setText("我的党费");
        returnT.setVisibility(View.VISIBLE);
        returnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
    }
    public void init(){//模拟数据
        List<String> list1=new ArrayList<>();
        List<String> list2=new ArrayList<>();
        List<String> list3=new ArrayList<>();
        for(int i=0;i<10;i++){
            list1.add("今天  16:44");
            list2.add("-50 RMB");
            list3.add("党费");
        }
        MyPartyPayListAdapter adapter=new MyPartyPayListAdapter(MyPartyPayActivity.this,list1,list2,list3);
        list_pay.setAdapter(adapter);
    }

}
