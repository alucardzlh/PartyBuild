package com.example.a25908.partybuild.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a25908.partybuild.Adapters.TheConstitutionOfThePartyAdapter;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yusi
 *党的章程一级界面
 */
public class TheConstitutionOfThePartyActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    private ImageView back;
    @ViewInject(R.id.title)
    private TextView title;

    @ViewInject(R.id.tcop_list)
    private ListView tcop_list;
    Intent intent;
    int b;

    TheConstitutionOfThePartyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_constitution_of_the_party);
        ViewUtils.inject(this);
        intent = getIntent();
        b = intent.getIntExtra("falg",0);
        if (b==1) {
        title.setText("党规党纪");
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        }
        else {
            title.setText("党的章程");
            title.setVisibility(View.VISIBLE);
            back.setVisibility(View.VISIBLE);
            back.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }

        List<DataManager.TCTPdanggui.DataBean.CommentListBean> list1=new ArrayList<>();
        list1 = DataManager.myTCTPdanggui.data.commentList;
        adapter=new TheConstitutionOfThePartyAdapter(TheConstitutionOfThePartyActivity.this,list1);
        tcop_list.setAdapter(adapter);
        tcop_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(TheConstitutionOfThePartyActivity.this,PartydisciplineActivity.class);
                intent1.putExtra("i",i);
                intent1.putExtra("b",b);
                startActivity(intent1);

            }
        });
    }
}
