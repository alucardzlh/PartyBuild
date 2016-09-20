package com.example.a25908.partybuild.Activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
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
    List<DataManager.MyPartyPay.DataBean.PartyMemberlistBean> list1;
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
        list1= new ArrayList<>();
        list1 = DataManager.myPartyPaylist.data.partyMemberlist;
        if (list1 !=null) {
            nullshuju.setText(View.GONE);
            list_pay.setVisibility(View.VISIBLE);
            MyPartyPayListAdapter adapter = new MyPartyPayListAdapter(MyPartyPayActivity.this, list1);
            list_pay.setAdapter(adapter);
        }
        else {
            nullshuju.setVisibility(View.VISIBLE);
            list_pay.setVisibility(View.GONE);
        }
    }

}
