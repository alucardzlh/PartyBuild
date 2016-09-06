package com.example.a25908.partybuild.Activitys;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import  com.lidroid.xutils.ViewUtils;
import com.example.a25908.partybuild.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author yusi
 * 用户登录界面
 */
public class LoginActivity extends BaseActivity {
    AlertDialog.Builder builder;
    public static AlertDialog dialog;

    @ViewInject(R.id.regiest)
    LinearLayout regiest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewUtils.inject(this);
        init();
        regiest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }
    public  void  init(){
        builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("APP暂不支持注册,党员请联系党支部或后台管理员!");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
    }
}
