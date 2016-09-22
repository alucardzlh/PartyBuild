package com.example.a25908.partybuild.Activitys;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.example.a25908.partybuild.Views.Toast;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yolanda.nohttp.RequestMethod;

import static com.example.a25908.partybuild.Utils.FileUtils.checkNameChese;
import static com.example.a25908.partybuild.Utils.URLconstant.UPDATEUSERDATEURL;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;

/**
 * @author yusi
 * 个人资料
 */
public class MydataUpdataActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    private ImageView back;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.fileclear)
    private TextView fileclear;

    @ViewInject(R.id.md_name)
    private EditText md_name;

    @ViewInject(R.id.md_sex)
    private EditText md_sex;

    @ViewInject(R.id.md_age)
    private EditText md_age;
    @ViewInject(R.id.md_birth)
    private EditText md_birth;

    @ViewInject(R.id.md_jianjie)
    private EditText md_jianjie;

    @ViewInject(R.id.md_dangwie)
    private EditText md_dangwie;

    @ViewInject(R.id.md_bumeng)
    private EditText md_bumeng
            ;
    @ViewInject(R.id.md_zhiweu)
    private EditText md_zhiweu;

    @ViewInject(R.id.md_address)
    private EditText md_address;

    @ViewInject(R.id.md_guxiang)
    private EditText md_guxiang;

    @ViewInject(R.id.md_youxiang)
    private EditText md_youxiang;

    @ViewInject(R.id.md_mphone)
    private EditText md_mphone;

    @ViewInject(R.id.md_sphone)
    private EditText md_sphone;

    PartySharePreferences psp;
    public static Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydata);
        ViewUtils.inject(this);
        psp=PartySharePreferences.getLifeSharedPreferences();
        title.setText("个人资料");
        fileclear.setText("编辑");
        fileclear.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        init();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fileclear.setOnClickListener(listener);
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        Toast.show("修改成功!");
                        psp.putUSERNAME(md_name.getText().toString());
                        psp.putEMAIL(md_youxiang.getText().toString());
                        break;
                    case 1:
                        Toast.show("修改失败!");
                        break;
                }
            }
        };
    }

    /**
     * 初始化赋值
     */
    public void init(){
        showEdit();

        md_name.setText(DataManager.MyDataList.data.partyMemberlist.USERNAME+"");

        if(DataManager.MyDataList.data.partyMemberlist.SEX == 0){
            md_sex.setText("男");
        }else{
            md_sex.setText("女");
        }

        md_age.setText(DataManager.MyDataList.data.partyMemberlist.AGE+"");

        md_birth.setText(DataManager.MyDataList.data.partyMemberlist.BIRTH+"");

        md_jianjie.setText( DataManager.MyDataList.data.partyMemberlist.INTRODUCTION+"");

        md_dangwie.setText(DataManager.MyDataList.data.partyMemberlist.UNIT+"");

        md_bumeng.setText( DataManager.MyDataList.data.partyMemberlist.DEPARTMENT+"");

        md_zhiweu.setText( DataManager.MyDataList.data.partyMemberlist.POSITION+"");

        md_address.setText( DataManager.MyDataList.data.partyMemberlist.HOME_ADDRESS+"");

        md_guxiang.setText( DataManager.MyDataList.data.partyMemberlist.CENSUS_REGISTER+"");

        md_youxiang.setText(DataManager.MyDataList.data.partyMemberlist.EMAIL+"");

        md_mphone.setText(DataManager.MyDataList.data.partyMemberlist.PHONE+"");

        md_sphone.setText(DataManager.MyDataList.data.partyMemberlist.MOBILE+"");
    }
    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.fileclear:
                    if(fileclear.getText().toString().equals("编辑")){
                        fileclear.setText("保存");
                        showEdit();
                    }else{

                        if(md_name.getText().toString().equals("")){
                            Toast.show("姓名不能为空!");
                        }else if(!checkNameChese(md_name.getText().toString())){
                            Toast.show("姓名只能填中文!");
                        }else if(md_sex.getText().toString().equals("")){
                            Toast.show("性别不能为空!");
                        }else if(!md_sex.getText().toString().equals("男") && !md_sex.getText().toString().equals("女")){
                            Toast.show("性别只能填'男'或者'女'!");
                        }else if(md_age.getText().toString().equals("")){
                            Toast.show("年龄不能为空!");
                        }
                        else{
                            fileclear.setText("编辑");
                            showEdit();
                            GsonRequest Request = new GsonRequest(URLINSER +UPDATEUSERDATEURL, RequestMethod.GET);
                            Request.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                            Request.add("KeyNo", psp.getUSERID());
                            Request.add("deviceId", new Build().MODEL);

                            Request.add("username", md_name.getText().toString());
                            if(md_sex.getText().toString().equals("男")){
                                Request.add("sex",0);
                            }else{
                                Request.add("sex",1);
                            }
                            Request.add("age", md_age.getText().toString());
                            Request.add("birth",md_birth.getText().toString());//出生年月
                            Request.add("introduction", md_jianjie.getText().toString());

                            Request.add("unit", md_dangwie.getText().toString());//单位(预留)
                            Request.add("department", md_bumeng.getText().toString());//部门（预留）
                            Request.add("position",md_zhiweu.getText().toString());//职务（预留）
                            Request.add("census_register", md_guxiang.getText().toString());//户籍
                            Request.add("email", md_youxiang.getText().toString());//邮箱
                            Request.add("phone", md_mphone.getText().toString());//电话
                            Request.add("mobile", md_sphone.getText().toString());//手机号
                            Request.add("home_address",md_address.getText().toString());//现家庭住址

//                           ================ 可不传】=====================
//                            Request.add("head_img", md_name.getText().toString());//头像(base64)
//                            Request.add("id_number", md_name.getText().toString());//身份证号
//                            Request.add("join_party",md_name.getText().toString());//入党时间
//                            Request.add("state", md_name.getText().toString());//状态（0正常 1退党 2开除 默认为0）

                            CallServer.getInstance().add(MydataUpdataActivity.this, Request, GsonCallBack.getInstance(), 0x0021, true, false, true);
                        }
                    }
                    break;
            }
        }
    };

    /**
     * 改变EditText的编辑状态
     */
    public void showEdit(){
        if(fileclear.getText().toString().equals("编辑")){
            for(int i=R.id.md_name;i<=R.id.md_sphone;i++){
                ((EditText) findViewById(i)).setFocusable(false);
                ((EditText) findViewById(i)).setFocusableInTouchMode(false);
            }
        }else{
            for(int i=R.id.md_sphone;i>=R.id.md_name;i--){
                ((EditText) findViewById(i)).setFocusable(true);
                ((EditText) findViewById(i)).setFocusableInTouchMode(true);
                ((EditText) findViewById(i)).requestFocus();
            }
        }

    }
}
