package com.example.a25908.partybuild.Activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a25908.partybuild.Adapters.PeopleGalleryAdapter;
import com.example.a25908.partybuild.Adapters.TheConstitutionOfThePartyAdapter;
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

    TheConstitutionOfThePartyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_constitution_of_the_party);
        ViewUtils.inject(this);
        title.setText("党的章程");
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        List<String> list1=new ArrayList<>();
        for(int i=0;i<20;i++){
            list1.add(getResources().getString(R.string.tcop_tit));
        }
        List<String> list2=new ArrayList<>();
        for(int i=0;i<20;i++){
            list2.add(getResources().getString(R.string.tcop_con));
        }
        adapter=new TheConstitutionOfThePartyAdapter(TheConstitutionOfThePartyActivity.this,list1,list2);
        tcop_list.setAdapter(adapter);
    }
}
