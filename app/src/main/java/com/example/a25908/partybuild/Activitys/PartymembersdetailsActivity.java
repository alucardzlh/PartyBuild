package com.example.a25908.partybuild.Activitys;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
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
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.example.a25908.partybuild.Views.Toast;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;

import static com.example.a25908.partybuild.Utils.URLconstant.PARTYRTLISTURL;
import static com.example.a25908.partybuild.Utils.URLconstant.PARTYRTNOTESURL;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;

/**
 * @author yusi
 * 党员详情
 */
public class PartymembersdetailsActivity extends BaseActivity {
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
    @ViewInject(R.id.pbd_dangwei)
    private TextView pbd_dangwei;
    @ViewInject(R.id.pbd_zhiwu)
    private TextView pbd_zhiwu;
    @ViewInject(R.id.pbd_setNotes)
    private LinearLayout pbd_setNotes;//设置备注
    @ViewInject(R.id.pbd_Notes)
    private TextView pbd_Notes;//备注

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

    String pid;

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
                pbd_age.setText(DataManager.PartyerList.data.UserlistPage.get(i).AGE+" 岁");
                if(DataManager.PartyerList.data.UserlistPage.get(i).SEX.equals("0")){
                    pbd_sex.setText("男");
                }else{
                    pbd_sex.setText("女");
                }
                pid=DataManager.PartyerList.data.UserlistPage.get(i).USERID;
                try{
                    if(!DataManager.PartyerList.data.UserlistPage.get(i).note.equals("")){
                        pbd_Notes.setText("【备注： "+DataManager.PartyerList.data.UserlistPage.get(i).note+" 】");
                    }
                }catch (NullPointerException e){

                }
                pbd_dangwei.setText("单位："+DataManager.PartyerList.data.UserlistPage.get(i).UNIT);
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
                    dialog1.show();
                    break;
                case R.id.btn_send:
                    dialog2.show();
                    break;
                case R.id.btn_Addt:
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
        builder1.setMessage("是否拨打电话到10086");
        builder1.setPositiveButton("拨打", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                //启动
//                startActivity(new Intent("android.intent.action.CALL",Uri.parse("tel:10086")));
                Toast.show("此功能正在施工中...");
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
        builder2.setTitle("提示-发送短信至10086");
        builder2.setView(et1);
        builder2.setPositiveButton("发送", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                SmsManager manager = SmsManager.getDefault();
//                ArrayList<String> list = manager.divideMessage(et1.getText().toString());  //因为一条短信有字数限制，因此要将长短信拆分
//                for(String text:list){
//                    manager.sendTextMessage("10086", null, text, null, null);
//                }
//                Toast.show("发送完毕");
                Toast.show("此功能正在施工中...");
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
        builder3.setMessage("是否拨打电话到10086");
        builder3.setPositiveButton("拨打", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Intent it = new Intent(Intent.ACTION_INSERT, Uri.withAppendedPath(
//                        Uri.parse("content://com.android.contacts"), "contacts"));
//                it.setType("vnd.android.cursor.dir/person");
//                // it.setType("vnd.android.cursor.dir/contact");
//                // it.setType("vnd.android.cursor.dir/raw_contact");
//                // 联系人姓名
//                for (int i=0;i<DataManager.PartyerList.data.UserlistPage.size();i++){
//                    if(DataManager.PartyerList.data.UserlistPage.get(i).USERNAME.equals(name)) {
//                        pbd_age.setText(DataManager.PartyerList.data.UserlistPage.get(i).AGE + " 岁");
//                        if (DataManager.PartyerList.data.UserlistPage.get(i).SEX.equals("0")) {
//                            pbd_sex.setText("男");
//                        } else {
//                            pbd_sex.setText("女");
//                        }
//                        pid = DataManager.PartyerList.data.UserlistPage.get(i).USER_DATA_ID;
//                        if (!DataManager.PartyerList.data.UserlistPage.get(i).note.equals("")) {
//                            pbd_Notes.setText("【备注： " + DataManager.PartyerList.data.UserlistPage.get(i).note + " 】");
//                        }
//                        pbd_dangwei.setText("单位：" + DataManager.PartyerList.data.UserlistPage.get(i).UNIT);
//                        pbd_zhiwu.setText("职务：" + DataManager.PartyerList.data.UserlistPage.get(i).POSITION);
//                        // 名字
//                        it.putExtra(android.provider.ContactsContract.Intents.Insert.NAME, name);
//                        // 公司
//                        it.putExtra(android.provider.ContactsContract.Intents.Insert.COMPANY, "北京XXXXXX公司");
//                        // 职务
//                        it.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, "");
//                        // email
//                        it.putExtra(android.provider.ContactsContract.Intents.Insert.EMAIL, "123456@qq.com");
//                        // 手机号码
//                        it.putExtra(android.provider.ContactsContract.Intents.Insert.PHONE, "010-1234567");
//                        // 单位电话
//                        it.putExtra(android.provider.ContactsContract.Intents.Insert.SECONDARY_PHONE, "18600001111");
//                        // 住宅电话
//                        it.putExtra(android.provider.ContactsContract.Intents.Insert.TERTIARY_PHONE, "010-7654321");
//                        // 备注信息
//                        it.putExtra(android.provider.ContactsContract.Intents.Insert.JOB_TITLE, "名片");
//                }
//            }
//            startActivity(it);
                Toast.show("此功能正在施工中...");
        }
    });
    builder3.setNegativeButton("取消", null);
    dialog3 = builder3.create();
    dialog3.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
}
}
