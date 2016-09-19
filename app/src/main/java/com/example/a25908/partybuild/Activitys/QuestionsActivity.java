package com.example.a25908.partybuild.Activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a25908.partybuild.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 在线答疑提交问题
 * @author weixuan
 */
public class QuestionsActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    private ImageView returnT;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.tijiao)
    public Button tijiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        ViewUtils.inject(this);
        title.setText("在线答疑");
        returnT.setVisibility(View.VISIBLE);
        returnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tijiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(QuestionsActivity.this,"功能还在开发中",Toast.LENGTH_SHORT).show();;
            }
        });


    }
}
