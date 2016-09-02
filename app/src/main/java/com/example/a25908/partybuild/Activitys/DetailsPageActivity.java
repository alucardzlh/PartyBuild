package com.example.a25908.partybuild.Activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a25908.partybuild.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 党委通知，学习园地，党员扶持的详情页
 */
public class DetailsPageActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    private ImageView returnT;
    @ViewInject(R.id.title)
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);
        ViewUtils.inject(this);
        title.setText("详情");
        returnT.setVisibility(View.VISIBLE);
        returnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
