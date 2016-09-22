package com.example.a25908.partybuild.Activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a25908.partybuild.Adapters.QuestionSurveyAdapter;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 问卷调查
 * @author weixuan
 */
public class QuestionSurveyActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    private ImageView returnT;
    @ViewInject(R.id.title)
    private TextView title;

    @ViewInject(R.id.list_qs)
    private ListView list_qs;

    @ViewInject(R.id.tijiao2)
    private Button tijiao2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_survey);
        ViewUtils.inject(this);
        title.setText("问卷调查");
        returnT.setVisibility(View.VISIBLE);
        returnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        QuestionSurveyAdapter adapter=new QuestionSurveyAdapter(this,DataManager.surveyList.data.DynamiclistPage);
        list_qs.setAdapter(adapter);

        tijiao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(QuestionSurveyActivity.this,"开发中",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
