package com.example.a25908.partybuild.Activitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a25908.partybuild.Dialogs.WaitDialog;
import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.example.a25908.partybuild.Views.Toast;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yolanda.nohttp.RequestMethod;

import static com.example.a25908.partybuild.Utils.URLconstant.LOGINURL;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;

/**
 * 用户登录界面
 * @author yusi
 *
 */
public class LoginActivity extends BaseActivity {
    AlertDialog.Builder builder;
    public static AlertDialog dialog;
    @ViewInject(R.id.login_user)
    EditText login_user;
    @ViewInject(R.id.login_pwd)
    EditText login_pwd;
    @ViewInject(R.id.user_c)
    ImageView user_c;
    @ViewInject(R.id.pwd_c)
    ImageView pwd_c;

    @ViewInject(R.id.close)
    TextView close;
    @ViewInject(R.id.regiest)
    LinearLayout regiest;
    @ViewInject(R.id.login)
    LinearLayout login;
    public static Handler handler;
    public static WaitDialog wd;
    public static boolean flag;
    PartySharePreferences psp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewUtils.inject(this);
        psp=PartySharePreferences.getLifeSharedPreferences();
        wd=new WaitDialog(this);
        init();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        regiest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login_user.getText().length() == 0 || login_pwd.getText().length() == 0) {
                    Toast.show("账号密码不能为空...");
                } else if (login_pwd.getText().length() < 6) {
                    Toast.show("密码长度至少6位...");
                } else {
                    wd.show();
                    flag=true;
                    GsonRequest LoginRequest = new GsonRequest(URLINSER +LOGINURL, RequestMethod.POST);
//                    LoginRequest.setConnectTimeout(100000);
                    LoginRequest.add("token", MD5.MD5s(login_user.getText().toString() + new Build().MODEL));
                    LoginRequest.add("KeyNo", login_user.getText().toString());
                    LoginRequest.add("deviceId", new Build().MODEL);
                    LoginRequest.add("password", MD5.MD5s(login_pwd.getText().toString()));
                    LoginRequest.add("cid", psp.getCid());
                    CallServer.getInstance().add(LoginActivity.this, LoginRequest, GsonCallBack.getInstance(), 0x001, true, false, true);
                }
            }
        });
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                wd.dismiss();
                flag=false;
                Intent i;
                switch (msg.what){
                    case 0:
                        i=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(i);
                        finish();
                        break;
                    case 1:
                        Toast.show("登陆失败!");
                        break;
                    case 2:
                        Toast.show("账号或密码错误");
                        break;
                    case 3:
                        Toast.show("服务器已关闭,APP无法使用!");
                        break;
                }
            }
        };
    }
    public  void  init(){
        builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("APP暂不支持注册,党员请联系党支部或后台管理员!");
        builder.setPositiveButton("确认", null);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失

        user_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_user.setText("");
            }
        });
        pwd_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_pwd.setText("");
            }
        });
        login_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start > 0) {
                    user_c.setVisibility(View.VISIBLE);
                } else {
                    user_c.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    user_c.setVisibility(View.VISIBLE);
                } else {
                    user_c.setVisibility(View.GONE);
                }
            }
        });
        login_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start > 0) {
                    pwd_c.setVisibility(View.VISIBLE);
                } else {
                    pwd_c.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    pwd_c.setVisibility(View.VISIBLE);
                } else {
                    pwd_c.setVisibility(View.GONE);
                }
            }
        });

    }
}
