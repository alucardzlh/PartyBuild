package com.example.a25908.partybuild.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Utils.FileUtils;
import com.example.a25908.partybuild.Views.RoundImageView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 在线答疑的详情页
 * @author weixuan
 */
public class AnswerDetailsActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    private ImageView back;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.item_an_wen12w22)
    private TextView item_an_wen12w22;
    @ViewInject(R.id.item_ad_time22TW)
    private TextView item_ad_time22TW;//提问时间


    @ViewInject(R.id.item_an_da)
    private TextView item_an_da;
    @ViewInject(R.id.item_ad_timeHD)
    private TextView item_ad_timeHD;//回答时间

    @ViewInject(R.id.item_an_tx1)
    private RoundImageView item_an_tx1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_details);
        ViewUtils.inject(this);
        Intent i=getIntent();
        int position=i.getIntExtra("position",0);
        title.setText("在线答疑");
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        item_an_tx1.setImageBitmap(FileUtils.stringtoBitmap(DataManager.FAQmarList.data.onlineAnswerlistPage.get(position).head_img));
        item_an_wen12w22.setText(DataManager.FAQmarList.data.onlineAnswerlistPage.get(position).problem);
        item_ad_time22TW.setText(DataManager.FAQmarList.data.onlineAnswerlistPage.get(position).add_time);
        item_an_da.setText(DataManager.FAQmarList.data.onlineAnswerlistPage.get(position).answer);
        item_ad_timeHD.setText(DataManager.FAQmarList.data.onlineAnswerlistPage.get(position).response_time);
    }
}
