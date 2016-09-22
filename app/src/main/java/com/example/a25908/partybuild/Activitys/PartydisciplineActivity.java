package com.example.a25908.partybuild.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author yusi
 *党规党纪界面、与党的章程详情
 */
public class PartydisciplineActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    private ImageView back;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.pdp_title)
    private TextView pdp_title;
    @ViewInject(R.id.pdp_time)
    private TextView pdp_time;
    @ViewInject(R.id.pdp_con)
    private TextView pdp_con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partydiscipline);
        ViewUtils.inject(this);
        Intent intent = getIntent();
        int i = intent.getIntExtra("i",0);
        int b = intent.getIntExtra("b",0);
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
            init(i);
    }

    /**
     * 初始化
     */
    private void init(int i){

        pdp_title.setText(DataManager.myTCTPdanggui.data.commentList.get(i).TITLE+"");
        pdp_time.setText("("+DataManager.myTCTPdanggui.data.commentList.get(i).DESCRIBES+")");
        pdp_con.setText(DataManager.myTCTPdanggui.data.commentList.get(i).CONTENT);
    }
}
