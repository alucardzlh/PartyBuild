package com.example.a25908.partybuild.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a25908.partybuild.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author  yusi
 * 掌上党校
 */
public class PalmPartySchoolActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    private ImageView back;
    @ViewInject(R.id.title)
    private TextView title;

    @ViewInject(R.id.pps1)
    private RelativeLayout pps1;
    @ViewInject(R.id.pps2)
    private RelativeLayout pps2;
    @ViewInject(R.id.pps3)
    private RelativeLayout pps3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palm_party_school);
        ViewUtils.inject(this);
        title.setText("掌上党校");
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        pps1.setOnClickListener(listener);
        pps2.setOnClickListener(listener);
        pps3.setOnClickListener(listener);
    }
    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.pps1:
                    startActivity(new Intent(PalmPartySchoolActivity.this, PeopleGalleryActivity.class));
                    break;
                case R.id.pps2:
                    startActivity(new Intent(PalmPartySchoolActivity.this, TheConstitutionOfThePartyActivity.class));
                    break;
                case R.id.pps3:
                    startActivity(new Intent(PalmPartySchoolActivity.this, PartydisciplineActivity.class));
                    break;
            }
        }
    };
}
