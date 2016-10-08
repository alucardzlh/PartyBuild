package com.example.a25908.partybuild.Activitys;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;

import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.FileUtils;
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.example.a25908.partybuild.Views.Toast;
import com.example.a25908.partybuild.Views.WheelView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yolanda.nohttp.RequestMethod;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.a25908.partybuild.Utils.URLconstant.PARTYRTNOTESURL;
import static com.example.a25908.partybuild.Utils.URLconstant.UPDATEUSERDATEURL;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;

/**
 *
 * 个人资料
 * @author yusi
 */
public class MydataActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    private ImageView back;
    @ViewInject(R.id.title)
    private TextView title;

    /**
     * textview 参数 控件
     */
    @ViewInject(R.id.md_name)
    private TextView md_name;

    @ViewInject(R.id.md_sex)
    private TextView md_sex;

    @ViewInject(R.id.md_age)
    private TextView md_age;
    @ViewInject(R.id.md_birth)
    private TextView md_birth;

    @ViewInject(R.id.md_jianjie)
    private TextView md_jianjie;

    @ViewInject(R.id.md_dangwie)
    private TextView md_dangwie;

    @ViewInject(R.id.md_bumeng)
    private TextView md_bumeng
            ;
    @ViewInject(R.id.md_zhiweu)
    private TextView md_zhiweu;

    @ViewInject(R.id.md_address)
    private TextView md_address;

    @ViewInject(R.id.md_guxiang)
    private TextView md_guxiang;

    @ViewInject(R.id.md_youxiang)
    private TextView md_youxiang;

    @ViewInject(R.id.md_mphone)
    private TextView md_mphone;

    @ViewInject(R.id.md_sphone)
    private TextView md_sphone;

    /**
     * RelativeLayout 点击 控件
     */
    @ViewInject(R.id.mmdd_name)
    private RelativeLayout mmdd_name;
    @ViewInject(R.id.mmdd_sex)
    private RelativeLayout mmdd_sex;
    @ViewInject(R.id.mmdd_age)
    private RelativeLayout mmdd_age;
    @ViewInject(R.id.mmdd_brith)
    private RelativeLayout mmdd_brith;
    @ViewInject(R.id.mmdd_jianjie)
    private RelativeLayout mmdd_jianjie;
    @ViewInject(R.id.mmdd_dangwei)
    private RelativeLayout mmdd_dangwei;
    @ViewInject(R.id.mmdd_bumen)
    private RelativeLayout mmdd_bumen;
    @ViewInject(R.id.mmdd_zhiwu)
    private RelativeLayout mmdd_zhiwu;
    @ViewInject(R.id.mmdd_xzz)
    private RelativeLayout mmdd_xzz;
    @ViewInject(R.id.mmdd_huji)
    private RelativeLayout mmdd_huji;
    @ViewInject(R.id.mmdd_youxiang)
    private RelativeLayout mmdd_youxiang;
    @ViewInject(R.id.mmdd_dianhua)
    private RelativeLayout mmdd_dianhua;
    @ViewInject(R.id.mmdd_shouji)
    private RelativeLayout mmdd_shouji;

    PartySharePreferences psp;
    public static Handler handler;

    /**
     * 日期选择器
     */
    //获取日期格式器对象
    DateFormat fmtDate = new java.text.SimpleDateFormat("yyyy-MM-dd");
    //    单独获取
    DateFormat fmtDateyyyy = new java.text.SimpleDateFormat("yyyy");
    DateFormat fmtDatemm = new java.text.SimpleDateFormat("MM");
    DateFormat fmtDatedd = new java.text.SimpleDateFormat("dd");

    //定义一个TextView控件对象
    //获取一个日历对象
    Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);

    //当点击DatePickerDialog控件的设置按钮时，调用该方法
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            //修改日历控件的年，月，日
            //这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            //将页面TextView的显示更新为最新时间
            upDateDate();
        }
    };

    String str=null;
    android.support.v7.app.AlertDialog.Builder builder;
    public static android.support.v7.app.AlertDialog dialog;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydata);
        ViewUtils.inject(this);
        psp=PartySharePreferences.getLifeSharedPreferences();
        title.setText("个人资料");
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        init();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
//                        Toast.show("修改成功!");
                        psp.putUSERNAME(md_name.getText().toString());
                        psp.putEMAIL(md_youxiang.getText().toString());
                        break;
                    case 1:
//                        Toast.show("修改失败!");
                        break;
                }
            }
        };

    }

    /**
     * 初始化赋值
     */
    public void init(){
        md_name.setText(DataManager.MyDataList.data.partyMemberlist.USERNAME+"");

        if(DataManager.MyDataList.data.partyMemberlist.SEX == 0){
            md_sex.setText("男");
        }else{
            md_sex.setText("女");
        }
        md_age.setText(DataManager.MyDataList.data.partyMemberlist.AGE+"");

        md_birth.setText(DataManager.MyDataList.data.partyMemberlist.BIRTH+"");

        md_jianjie.setText( DataManager.MyDataList.data.partyMemberlist.INTRODUCTION+"");

        md_dangwie.setText(DataManager.MyDataList.data.partyMemberlist.UNIT_NAME+"");

        md_bumeng.setText( DataManager.MyDataList.data.partyMemberlist.DEPARTMENT_NAME+"");

        md_zhiweu.setText( DataManager.MyDataList.data.partyMemberlist.POSITION+"");

        md_address.setText( DataManager.MyDataList.data.partyMemberlist.HOME_ADDRESS+"");

        md_guxiang.setText( DataManager.MyDataList.data.partyMemberlist.CENSUS_REGISTER+"");

        md_youxiang.setText(DataManager.MyDataList.data.partyMemberlist.EMAIL+"");

        md_mphone.setText(DataManager.MyDataList.data.partyMemberlist.PHONE+"");

        md_sphone.setText(DataManager.MyDataList.data.partyMemberlist.MOBILE+"");

        back.setOnClickListener(listener);
        mmdd_name.setOnClickListener(listener);
        mmdd_sex.setOnClickListener(listener);
        mmdd_age.setOnClickListener(listener);
        mmdd_brith.setOnClickListener(listener);
        mmdd_jianjie.setOnClickListener(listener);
        mmdd_dangwei.setOnClickListener(listener);
        mmdd_bumen.setOnClickListener(listener);
        mmdd_zhiwu.setOnClickListener(listener);
        mmdd_xzz.setOnClickListener(listener);
        mmdd_huji.setOnClickListener(listener);
        mmdd_youxiang.setOnClickListener(listener);
        mmdd_dianhua.setOnClickListener(listener);
        mmdd_shouji.setOnClickListener(listener);
    }
    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.returnT:
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

//                    Request.add("unit_id", md_dangwie.getText().toString());//单位(预留)id
                    Request.add("unit_name", md_dangwie.getText().toString());//单位(预留)
//                    Request.add("department_id", md_bumeng.getText().toString());//部门（预留）id
                    Request.add("department_name", md_bumeng.getText().toString());//部门（预留）
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

                    CallServer.getInstance().add(MydataActivity.this, Request, GsonCallBack.getInstance(), 0x0021, true, false, true);
                    finish();
                    break;
                case R.id.mmdd_name:
                    showinit(md_name.getText().toString(),"姓名",md_name);
                    break;
                case R.id.mmdd_sex:
                    showSelect(md_sex.getText().toString(),"性别",getResources().getStringArray(R.array.sexMar),md_sex);
                    break;
                case R.id.mmdd_brith:
                    String[] str=md_birth.getText().toString().split("-");
                    DatePickerDialog  dateDlg = new DatePickerDialog(MydataActivity.this,
                            d,Integer.parseInt(str[0]),
                            Integer.parseInt(str[1])-1,
                            Integer.parseInt(str[2]));
                    dateDlg.setCancelable(false);
                    dateDlg.show();
                    break;
                case R.id.mmdd_age:
                    break;
                case R.id.mmdd_jianjie:
                    showinit(md_jianjie.getText().toString(),"简介",md_jianjie);
                    break;
                case R.id.mmdd_dangwei:
                    int size = DataManager.MyDataBMList.data.UnitlistPage.size();
                    String[] dwName = new String[size];
                    for(int i=0;i<size;i++){
                        dwName[i]=DataManager.MyDataBMList.data.UnitlistPage.get(i).unit_name;
                    }
                    showSelect(md_dangwie.getText().toString(),"单位",dwName,md_dangwie);
                    break;
                case R.id.mmdd_bumen:
                    String dw=md_dangwie.getText().toString();
                    String[] bmName=null;
                    for(int i=0;i<DataManager.MyDataBMList.data.UnitlistPage.size();i++){
                        if(dw.equals(DataManager.MyDataBMList.data.UnitlistPage.get(i).unit_name)){
                            int sizebm = DataManager.MyDataBMList.data.UnitlistPage.get(i).Departmentlist.size();
                            bmName = new String[sizebm];
                            for(int j=0;j<sizebm;j++){
                                bmName[j]=DataManager.MyDataBMList.data.UnitlistPage.get(i).Departmentlist.get(j).department_name;
                            }
                        }
                    }
                    showSelect(md_bumeng.getText().toString(),"部门",bmName,md_bumeng);
                    break;
                case R.id.mmdd_zhiwu:
                    showinit(md_zhiweu.getText().toString(),"职务",md_zhiweu);
                    break;
                case R.id.mmdd_xzz:
                    showinit(md_address.getText().toString(),"现住址",md_address);
                    break;
                case R.id.mmdd_huji:
                    showinit(md_guxiang.getText().toString(),"户籍",md_guxiang);
                    break;
                case R.id.mmdd_youxiang:
                    showinit(md_youxiang.getText().toString(),"邮箱地址",md_youxiang);
                    break;
                case R.id.mmdd_dianhua:
                    showinit(md_mphone.getText().toString(),"电话号码",md_mphone);
                    break;
                case R.id.mmdd_shouji:
                    showinit(md_sphone.getText().toString(),"手机号码",md_sphone);
                    break;
            }
        }
    };
    private void upDateDate() {
        md_birth.setText(fmtDate.format(dateAndTime.getTime()));
        md_age.setText(getAge(dateAndTime.getTime())+"");
    }

    /**
     * 计算年龄
     * @param dateOfBirth
     * @return
     */
    public static int getAge(Date dateOfBirth) {
        int age = 0;
        Calendar born = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        if (dateOfBirth != null) {
            now.setTime(new Date());
            born.setTime(dateOfBirth);
            if (born.after(now)) {
                throw new IllegalArgumentException(
                        "Can't be born in the future");
            }
            age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
            if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
                age -= 1;
            }
        }
        return age;
    }

    /**
     * 上下滑动选择器
     */
    public void showSelect(String state, final String title, final String[] con, final TextView tv){
        final String cc=state;//默认保存一个值
        View outerView = LayoutInflater.from(MydataActivity.this).inflate(R.layout.wheel_view, null);
        WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
        wv.setOffset(2);
        wv.setItems(Arrays.asList(con));
        for(int i=0;i<Arrays.asList(con).size();i++){
            if(!state.equals("")){
                if(Arrays.asList(con).get(i).equals(state)){
                    wv.setSeletion(i);
                }
            }else{
                wv.setSeletion(0);
            }
        }
        str=state;
        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
//                Toast.show("[Dialog]selectedIndex: " + selectedIndex + ", item: " + item);
                str=item;
            }
        });

        new AlertDialog.Builder(MydataActivity.this)
                .setTitle(title)
                .setView(outerView)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!str.equals("")){
                            tv.setText(str+"");
                        }else{
                            tv.setText(con[0]+"");
                        }

                        if(title.equals("单位")){
                            if(!md_dangwie.getText().toString().equals(cc)){//判断此时的单位是否修改
                                md_bumeng.setText("");
                                md_zhiweu.setText("");
                            }
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .setCancelable(false)
                .show();
    }

    /**
     * 修改文本 dialog
     */
    public void showinit(String state, final String title, final TextView tv) {
        et = new EditText(MydataActivity.this);
        if (!title.equals("简介") && !title.equals("现住址")) {
            et.setGravity(Gravity.CENTER);
            et.setLines(1);
            et.setMaxEms(1);
            et.setMaxLines(1);
            switch (title){
                case "姓名":
                    et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
                    break;
            }
        }
        et.setText(state);
        final TextView ttv = new TextView(MydataActivity.this);
        new AlertDialog.Builder(MydataActivity.this)
                .setTitle(title)
                .setView(et)
                .setMessage(ttv.getText().toString())
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (title) {
                            case "姓名":
                                if (FileUtils.checkNameChese(et.getText().toString()) && et.getText().toString().length() <= 6) {
                                    tv.setText(et.getText().toString());
                                } else {
                                    Toast.show("为了使名字规范,名字只能输入中文 !!!");
                                }
                                break;
                            case "邮箱地址":
                                if (FileUtils.isEmail(et.getText().toString())) {
                                    tv.setText(et.getText().toString());
                                } else {
                                    Toast.show("邮箱格式不正确!!!");
                                }
                                break;
                            case "手机号码":
                                if (!FileUtils.isNumeric(et.getText().toString())) {
                                    Toast.show("手机格式不正确！");
                                } else if (et.getText().length() != 11) {
                                    Toast.show("手机格式不正确,号码位数不等于11位!!");
                                } else {
                                    tv.setText(et.getText().toString());
                                }
                                break;
                            default:
                                tv.setText(et.getText().toString());
                                break;
                        }

                    }
                })
                .setNegativeButton("取消", null)
                .setCancelable(false)
                .show();
    }

}
