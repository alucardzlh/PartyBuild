package com.example.a25908.partybuild.Activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a25908.partybuild.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 问卷调查
 * @author weixuan
 */
public class QuestionSurveyActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    private ImageView returnT;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.frist_ti)
    private RadioGroup rg1;
    @ViewInject(R.id.rg1_5)
    private RadioButton rg1_5;
    @ViewInject(R.id.rg2_5)
    private RadioButton rg2_5;
    @ViewInject(R.id.rg_qita)
    private LinearLayout rg_qita;
    @ViewInject(R.id.frist2_ti)
    private RadioGroup rg2;
    @ViewInject(R.id.rg_qita2)
    private LinearLayout rg_qita2;
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

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rg1_5.getId() == i){
                    rg_qita.setVisibility(View.VISIBLE);

                }
                else {
                    rg_qita.setVisibility(View.GONE);
                }



            }
        });

        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rg2_5.getId() == i){
                    rg_qita2.setVisibility(View.VISIBLE);
                }
                else {
                    rg_qita2.setVisibility(View.GONE);
                }



            }
        });
        tijiao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(QuestionSurveyActivity.this,"开发中",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
