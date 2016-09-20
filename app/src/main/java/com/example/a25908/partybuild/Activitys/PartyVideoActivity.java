package com.example.a25908.partybuild.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a25908.partybuild.Adapters.PartyCommitteeAdpter;
import com.example.a25908.partybuild.Adapters.PartyVideoCAdpter;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 党建视频
 * @author weixuan
 */
public class PartyVideoActivity extends BaseActivity {

    @ViewInject(R.id.returnT)
    private ImageView back;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.list_pv)
    private ListView list_pv;
    private PartyVideoCAdpter adpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_video);
        ViewUtils.inject(this);
        title.setText("党建视频");
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adpter = new PartyVideoCAdpter(PartyVideoActivity.this,DataManager.partyvideoList.data.videolistPage);
        list_pv.setAdapter(adpter);
        list_pv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(PartyVideoActivity.this,DetailsPageActivity.class).putExtra("flag",1));
            }
        });
    }


}
