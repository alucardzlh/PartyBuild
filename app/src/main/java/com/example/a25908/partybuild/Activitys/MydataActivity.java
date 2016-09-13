package com.example.a25908.partybuild.Activitys;

import android.app.Activity;
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
 * 个人资料
 */
public class MydataActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    private ImageView back;
    @ViewInject(R.id.title)
    private TextView title;

    @ViewInject(R.id.md_name)
    private TextView md_name;

    @ViewInject(R.id.md_sex)
    private TextView md_sex;

    @ViewInject(R.id.md_age)
    private TextView md_age;

    @ViewInject(R.id.md_jianjie)
    private TextView md_jianjie;

    @ViewInject(R.id.md_dangwie)
    private TextView md_dangwie;

    @ViewInject(R.id.md_bumeng)
    private TextView md_bumeng
            ;
    @ViewInject(R.id.md_zhiweu)
    private TextView md_zhiweu;

    @ViewInject(R.id.md_guxiang)
    private TextView md_guxiang;

    @ViewInject(R.id.md_youxiang)
    private TextView md_youxiang;

    @ViewInject(R.id.md_sphone)
    private TextView md_sphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydata);
        ViewUtils.inject(this);
        title.setText("个人资料");
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        init();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void init(){
        md_name.setText("姓名："+ DataManager.MyDataList.data.partyMemberlist.username);

        md_sex.setText("性别："+ DataManager.MyDataList.data.partyMemberlist.sex);

        md_age.setText("年龄："+ DataManager.MyDataList.data.partyMemberlist.age);

        md_jianjie.setText("简介："+ DataManager.MyDataList.data.partyMemberlist.introduction);

        md_dangwie.setText("单位："+ DataManager.MyDataList.data.partyMemberlist.unit);

        md_bumeng.setText("部门："+ DataManager.MyDataList.data.partyMemberlist.department);

        md_zhiweu.setText("职务："+ DataManager.MyDataList.data.partyMemberlist.position);

        md_guxiang.setText("故乡："+ DataManager.MyDataList.data.partyMemberlist.home_address);

        md_youxiang.setText("邮箱："+ DataManager.MyDataList.data.partyMemberlist.email);

        md_sphone.setText("手机号："+ DataManager.MyDataList.data.partyMemberlist.mobile);

    }
}
