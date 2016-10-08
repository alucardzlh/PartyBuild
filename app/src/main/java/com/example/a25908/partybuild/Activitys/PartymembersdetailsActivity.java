package com.example.a25908.partybuild.Activitys;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.FileUtils;
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.example.a25908.partybuild.Views.RoundImageView;
import com.example.a25908.partybuild.Views.Toast;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;

import static com.example.a25908.partybuild.Utils.URLconstant.PARTYRTNOTESURL;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;

/**
 * @author yusi
 * 党员详情
 */
public class PartymembersdetailsActivity extends BaseActivity {
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 0;
    public static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    @ViewInject(R.id.returnT)
    private ImageView back;
    @ViewInject(R.id.title)
    private TextView title;

    @ViewInject(R.id.pbd_name)
    private TextView pbd_name;
    @ViewInject(R.id.pbd_age)
    private TextView pbd_age;
    @ViewInject(R.id.pbd_sex)
    private TextView pbd_sex;
    @ViewInject(R.id.pbd_phone)
    private TextView pbd_phone;
    @ViewInject(R.id.pbd_dangwei)
    private TextView pbd_dangwei;
    @ViewInject(R.id.pbd_zhiwu)
    private TextView pbd_zhiwu;
    @ViewInject(R.id.pbd_setNotes)
    private LinearLayout pbd_setNotes;//设置备注
    @ViewInject(R.id.pbd_Notes)
    private TextView pbd_Notes;//备注
    @ViewInject(R.id.pmd_img)
    private RoundImageView pmd_img;//头像

    @ViewInject(R.id.btn_phone)
    private Button btn_phone;//拨打电话
    @ViewInject(R.id.btn_send)
    private Button btn_send;//发送短信
    @ViewInject(R.id.btn_Addt)
    private Button btn_Addt;//添加到本地通通讯录

    //修改备注
    AlertDialog.Builder builder;
    public static AlertDialog dialog;
    EditText et;
    //拨打电话
    AlertDialog.Builder builder1;
    public static AlertDialog dialog1;
    //发送短信
    AlertDialog.Builder builder2;
    public static AlertDialog dialog2;
    EditText et1;
    //添加到本地通讯录
    AlertDialog.Builder builder3;
    public static AlertDialog dialog3;
    String name;

    PartySharePreferences psp;

    String pid,phone;

    public static Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partymembersdetails);
        ViewUtils.inject(this);
        psp=PartySharePreferences.getLifeSharedPreferences();
        title.setText(getResources().getString(R.string.pbd_title));
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent i=getIntent();
        name=i.getStringExtra("name");
        init();
        showdio();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        pbd_Notes.setText("【备注： "+et.getText().toString()+" 】");
                        DataManager.PartyerList.data.UserlistPage.clear();//清空数据集合，以便某些判断执行
                        break;
                    case 1:
                        Toast.show("备注修改失败!");
                        break;
                }
            }
        };

    }
    public void init(){
        pbd_name.setText(name);
        for (int i=0;i<DataManager.PartyerList.data.UserlistPage.size();i++){
            if(DataManager.PartyerList.data.UserlistPage.get(i).USERNAME.equals(name)){
                try{
                    if(!DataManager.PartyerList.data.UserlistPage.get(i).HEAD_IMG.equals("")){
                        pmd_img.setImageBitmap(FileUtils.stringtoBitmap(DataManager.PartyerList.data.UserlistPage.get(i).HEAD_IMG));
                    }else{
                        pmd_img.setImageResource(R.mipmap.appicon);
                    }
                }catch (NullPointerException e){
                    pmd_img.setImageResource(R.mipmap.appicon);
                }


                pbd_age.setText(DataManager.PartyerList.data.UserlistPage.get(i).AGE+" 岁");
                if(DataManager.PartyerList.data.UserlistPage.get(i).SEX.equals("0")){
                    pbd_sex.setText("男");
                }else{
                    pbd_sex.setText("女");
                }
                pid=DataManager.PartyerList.data.UserlistPage.get(i).USERID;
                phone=DataManager.PartyerList.data.UserlistPage.get(i).MOBILE;
                try{
                    if(!DataManager.PartyerList.data.UserlistPage.get(i).note.equals("")){
                        pbd_Notes.setText("【备注： "+DataManager.PartyerList.data.UserlistPage.get(i).note+" 】");
                    }
                }catch (NullPointerException e){

                }
                pbd_phone.setText("电话："+phone);
                pbd_dangwei.setText("单位："+DataManager.PartyerList.data.UserlistPage.get(i).UNIT_NAME);
                pbd_zhiwu.setText("职务："+DataManager.PartyerList.data.UserlistPage.get(i).POSITION);
            }
        }

        pbd_setNotes.setOnClickListener(listener);
        btn_phone.setOnClickListener(listener);
        btn_send.setOnClickListener(listener);
        btn_Addt.setOnClickListener(listener);
    }

    View.OnClickListener listener=new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.pbd_setNotes:
                    dialog.show();
                    break;
                case R.id.btn_phone:
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (ContextCompat.checkSelfPermission(PartymembersdetailsActivity.this, Manifest.permission.CALL_PHONE)
                                != PackageManager.PERMISSION_GRANTED) {
                            //READ_EXTERNAL_STORAGE
                            ActivityCompat.requestPermissions(PartymembersdetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE},
                                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
                        }else {
                            dialog1.show();
                        }
                        return;
                    }
                    dialog1.show();
                    break;
                case R.id.btn_send:
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (ContextCompat.checkSelfPermission(PartymembersdetailsActivity.this, Manifest.permission.SEND_SMS)
                                != PackageManager.PERMISSION_GRANTED) {
                            //READ_EXTERNAL_STORAGE
                            ActivityCompat.requestPermissions(PartymembersdetailsActivity.this, new String[]{Manifest.permission.SEND_SMS},
                                    MY_PERMISSIONS_REQUEST_SEND_SMS);
                        }else {
                            dialog2.show();
                        }
                        return;
                    }
                    dialog2.show();
                    break;
                case R.id.btn_Addt:
                    dialog3.show();
                    break;
            }
        }
    };
    public void showdio(){
        /**
         * 修改备注 dialog
         */
        et=new EditText(PartymembersdetailsActivity.this);
        et.setLines(1);
        et.setGravity(Gravity.CENTER);
        builder = new AlertDialog.Builder(this);
        builder.setTitle("修改备注");
        builder.setView(et);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                GsonRequest Request = new GsonRequest(URLINSER +PARTYRTNOTESURL, RequestMethod.GET);
                Request.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                Request.add("KeyNo", psp.getUSERID());
                Request.add("udid", pid);
                Request.add("note", et.getText().toString());
                Request.add("deviceId", new Build().MODEL);
                CallServer.getInstance().add(PartymembersdetailsActivity.this, Request, GsonCallBack.getInstance(), 0x0031, true, false, true);
            }
        });
        builder.setNegativeButton("取消", null);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        /**
         * 拨打电话 dialog
         */
        builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("提示");
        builder1.setMessage("是否拨打电话到"+name+"("+phone+")");
        builder1.setPositiveButton("拨打", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                //启动
                startActivity(new Intent("android.intent.action.CALL",Uri.parse("tel:"+phone)));
//                Toast.show("此功能正在施工中...");
            }
        });
        builder1.setNegativeButton("取消", null);
        dialog1 = builder1.create();
        dialog1.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        /**
         * 发送短信 dialog
         */
        et1=new EditText(PartymembersdetailsActivity.this);
        builder2 = new AlertDialog.Builder(this);
        builder2.setTitle("提示-发送短信至"+name+"("+phone+")");
        builder2.setView(et1);
        builder2.setPositiveButton("发送", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SmsManager manager = SmsManager.getDefault();
                ArrayList<String> list = manager.divideMessage(et1.getText().toString());  //因为一条短信有字数限制，因此要将长短信拆分
                for(String text:list){
                    manager.sendTextMessage(phone, null, text, null, null);
                }
                Toast.show("发送完毕");
//                Toast.show("此功能正在施工中...");
            }
        });
        builder2.setNegativeButton("取消", null);
        dialog2 = builder2.create();
        dialog2.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        /**
         * 添加到本地通讯录 dialog
         */
        builder3 = new AlertDialog.Builder(this);
        builder3.setTitle("提示");
        builder3.setMessage("是否"+name+"添加到手机本地通讯录中");
        builder3.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent it = new Intent(Intent.ACTION_INSERT, Uri.withAppendedPath(
                        Uri.parse("content://com.android.contacts"), "contacts"));
                it.setType("vnd.android.cursor.dir/person");
                // it.setType("vnd.android.cursor.dir/contact");
                // it.setType("vnd.android.cursor.dir/raw_contact");
                // 联系人姓名
                for (int i=0;i<DataManager.PartyerList.data.UserlistPage.size();i++){
                    if(DataManager.PartyerList.data.UserlistPage.get(i).USERNAME.equals(name)) {
                        // 名字
                        it.putExtra(android.provider.ContactsContract.Intents.Insert.NAME, name);
                        // 公司
                        it.putExtra(android.provider.ContactsContract.Intents.Insert.COMPANY, DataManager.PartyerList.data.UserlistPage.get(i).UNIT_NAME);
                        // 职务
                        it.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, DataManager.PartyerList.data.UserlistPage.get(i).POSITION);
//                        // email
//                        it.putExtra(android.provider.ContactsContract.Intents.Insert.EMAIL, "123456@qq.com");
                        // 手机号码
                        it.putExtra(android.provider.ContactsContract.Intents.Insert.PHONE, phone);
//                        // 单位电话
//                        it.putExtra(android.provider.ContactsContract.Intents.Insert.SECONDARY_PHONE, "18600001111");
//                        // 住宅电话
//                        it.putExtra(android.provider.ContactsContract.Intents.Insert.TERTIARY_PHONE, "010-7654321");
                        if (!DataManager.PartyerList.data.UserlistPage.get(i).note.equals("")) {
                            // 备注信息
                            it.putExtra(android.provider.ContactsContract.Intents.Insert.JOB_TITLE, DataManager.PartyerList.data.UserlistPage.get(i).note);
                        }
                    }
                }
                startActivity(it);
//                Toast.show("此功能正在施工中...");
            }
        });
        builder3.setNegativeButton("取消", null);
        dialog3 = builder3.create();
        dialog3.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==MY_PERMISSIONS_REQUEST_CALL_PHONE){//拨打电话权限回调
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                dialog1.show();
            }
            else {
                Toast.show("权限获取失败，无法拨打电话，请到设置中开放电话权限");
            }

        }
        else if (requestCode==MY_PERMISSIONS_REQUEST_SEND_SMS){//发送短信权限回调
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                dialog2.show();
            }
            else {
                Toast.show("权限获取失败，无法发送短信，请到设置中开放短信权限");
            }
        }
    }
}
