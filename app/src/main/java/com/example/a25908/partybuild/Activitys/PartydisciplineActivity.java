package com.example.a25908.partybuild.Activitys;

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
 *党规党纪界面
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
        title.setText("党规党纪");
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });
        init();
    }

    /**
     * 初始化
     */
    private void init(){
        pdp_title.setText(DataManager.myTCTPdanggui.data.commentList.get(0).TITLE+"");
        pdp_time.setText("("+DataManager.myTCTPdanggui.data.commentList.get(0).DESCRIBES+")");
        pdp_con.setText(DataManager.myTCTPdanggui.data.commentList.get(0).CONTENT);
    }
}
