package com.example.a25908.partybuild.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.a25908.partybuild.Dialogs.WaitDialog;
import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.example.a25908.partybuild.Views.Toast;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.a25908.partybuild.Utils.URLconstant.SURVEYTIJIAO;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;

/**
 *
 * 问卷调查
 * @author weixuan
 */
public class QuestionSurvey2Activity extends BaseActivity {

    @ViewInject(R.id.returnT)
    private ImageView returnT;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.tijiao2)
    private Button tijiao2;
    @ViewInject(R.id.big_lin)
    private LinearLayout big_lin;
    @ViewInject(R.id.qs_nm)
    private CheckBox qs_nm;//匿名

    WaitDialog wd;
    PartySharePreferences psp;
    public static Handler handler;
    Intent intent;
    Gson gson;
    int isiza=0;
    int ischeck=0;
    int ischeck2=0;
    int type = 1;
    String content = "";
    private List<RadioButton> radioButtonList;// 装载RadioButton的集合
    private List<CheckBox> checkBoxList;// 装载CheckBox的集合
    private HashMap<Integer, Integer> surveyIDMap = new HashMap<Integer, Integer>();
    private HashMap<Integer, String> surveyNAMEMap = new HashMap<Integer, String>();
    private HashMap<Integer, String> surveyCONMap = new HashMap<Integer, String>();
    private HashMap<Integer, String> surveyTOPMap = new HashMap<Integer, String>();
//    private List<Map<String,List<Map<String,Object>>>> mapList;
    private List<Map<String,Object>> mapList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_survey2);
        ViewUtils.inject(this);
        gson = new Gson();

        intent = getIntent();
        radioButtonList = new ArrayList<RadioButton>();
        checkBoxList = new ArrayList<CheckBox>();
        wd = new WaitDialog(this);
        psp = new PartySharePreferences();
        title.setText("问卷调查");
        returnT.setVisibility(View.VISIBLE);
        returnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intent.getIntExtra("fl",0)==110){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else {
                    finish();
                }
            }
        });
        iniv();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        Toast.show("提交成功");
                        finish();
                        break;
                }
            }
        };
        tijiao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkOptions();
                if (isiza == 0&mapList.isEmpty()){
                    Toast.show("请选择答案");
                }
                else if (isiza != mapList.size()){
                    Toast.show("还有问题没答完");
                }
                else {
                    String resultlist = gson.toJson(mapList);
                    Toast.show("ok");
                    wd.show();
                    GsonRequest Request = new GsonRequest(URLINSER + SURVEYTIJIAO, RequestMethod.GET);
                    Request.add("KeyNo", psp.getUSERID());
                    Request.add("deviceId", new Build().MODEL);
                    Request.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                    Request.add("resultlist", resultlist);
                    CallServer.getInstance().add(QuestionSurvey2Activity.this, Request, GsonCallBack.getInstance(), 0x0012, true, false, true);
                }
            }
        });
        qs_nm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    type = 0;
                    Toast.show("匿名 type==>"+type);
                }
                else {
                    type = 1;
                    Toast.show("不匿名 type==>"+type);
                }
            }
        });
    }

    /**
     * 加载数据
     */
    private void iniv(){
        for (int i = 0; i< DataManager.myQuestions.data.QuestionlistPage.size(); i++){
            final String title = DataManager.myQuestions.data.QuestionlistPage.get(i).topic_name;//题目
            int titleid = DataManager.myQuestions.data.QuestionlistPage.get(i).topic_id;//题目id
            int type = DataManager.myQuestions.data.QuestionlistPage.get(i).type;//0为单选，1为多选
            // 调查问卷问题
            TextView surveyTitle = new TextView(this);
            surveyTitle.setText(title);
            big_lin.addView(surveyTitle);
            //0为单选，1为多选
            if (type==0){
                RadioGroup radioGroup = getRadioGroup(DataManager.myQuestions.data.QuestionlistPage.get(i).alist,title);
                big_lin.addView(radioGroup);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, final int i) {
                        if (surveyNAMEMap.get(i).equals("其他")){
                            final EditText ed = new EditText(QuestionSurvey2Activity.this);
                            ed.setHint("请输入您的意见...");
                            ed.setLines(3);
                            new AlertDialog.Builder(QuestionSurvey2Activity.this)
                                    .setTitle("其他意见")
                                    .setView(ed)
                                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i2) {
                                            content = ed.getText().toString();
                                            surveyCONMap.put(i,content);


                                        }
                                    })
                                    .setNegativeButton("取消",null)
                                    .setCancelable(false)
                                    .show();
                        }
                    }
                });
            }
            else{
                big_lin.addView(getLinearLayout(DataManager.myQuestions.data.QuestionlistPage.get(i).alist,title));
                ischeck++;
            }



        }
    }

    /**
     * 生成一个用于单选的RadioGroup
     * @param list
     * @return
     */
    private RadioGroup getRadioGroup(List<DataManager.MyQuestions.DataBean.QuestionlistPageBean.AlistBean> list,String s){
        RadioGroup radioGroup = new RadioGroup(this);
        // 设置RadioGroup为垂直
        radioGroup.setOrientation(RadioGroup.VERTICAL);
        radioGroup.setBackgroundResource(R.drawable.maintop_shape3);
        for(int i=0; i<list.size(); i++){
            RadioButton radio = new RadioButton(this);
            radio.setText(list.get(i).answer_name);
//            radioGroup.setTag(list.get(i).topic_id);
            // 设置id，我是将封装数据中选项有一个唯一的id当做该控件id了
            radio.setId(list.get(i).answer_id);
            surveyTOPMap.put(list.get(i).answer_id, s);
            surveyNAMEMap.put(list.get(i).answer_id, list.get(i).answer_name);
            surveyIDMap.put(list.get(i).answer_id, list.get(i).topic_id);
            radioButtonList.add(radio);
            radioGroup.addView(radio);
        }
        return radioGroup;
    }

    /**
     * 生成一个用于多选的LinearLayout
     * @param list
     * @return
     */
    private LinearLayout getLinearLayout(List<DataManager.MyQuestions.DataBean.QuestionlistPageBean.AlistBean> list,String s){
        LinearLayout layout = new LinearLayout(this);
        // 设置LinearLayout垂直
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundResource(R.drawable.maintop_shape3);
        for(int i=0; i<list.size(); i++){
            CheckBox cb = new CheckBox(this);
            cb.setText(list.get(i).answer_name);
//             cb.setTag(list.get(i).topic_id);
            // 设置id，与RadioButton一样
            cb.setId(list.get(i).answer_id);
            surveyTOPMap.put(list.get(i).answer_id, s);
            surveyNAMEMap.put(list.get(i).answer_id, list.get(i).answer_name);
            surveyIDMap.put(list.get(i).answer_id, list.get(i).topic_id);
            checkBoxList.add(cb);
            layout.addView(cb);
        }
        return layout;
    }

    private void checkOptions(){
        mapList = new ArrayList<>();
        if (radioButtonList!=null){
            for(int i=0; i<radioButtonList.size(); i++){
                boolean isSelected = radioButtonList.get(i).isChecked();
                if (isSelected == true){
                    isiza++;
                    Map<String,Object> map = new HashMap<>();
                    map.put("answer_id",radioButtonList.get(i).getId());
                    map.put("topic_id",surveyIDMap.get(radioButtonList.get(i).getId()));
                    if (surveyCONMap.get(radioButtonList.get(i).getId())==null){
                        map.put("content","");
                    }
                    else {
                        map.put("content",surveyCONMap.get(radioButtonList.get(i).getId()));
                    }

                    map.put("answer_name",surveyNAMEMap.get(radioButtonList.get(i).getId()));
                    map.put("topic_name",surveyTOPMap.get(radioButtonList.get(i).getId()));
                    map.put("type",type);
                    map.put("username",psp.getUSERNAME());
                    map.put("userid", psp.getUSERID());
                    map.put("period_of_time",DataManager.myQuestions.data.QuestionlistPage.get(0).period_of_time);
                    mapList.add(map);
                }

            }

        }
        if (checkBoxList != null){
            for(int i=0; i<checkBoxList.size(); i++){
                boolean isSelected = checkBoxList.get(i).isChecked();
                if (isSelected == true){
                    isiza++;
                    ischeck2++;
                    Map<String,Object> map = new HashMap<>();
                    map.put("answer_id",checkBoxList.get(i).getId());
                    map.put("topic_id",surveyIDMap.get(checkBoxList.get(i).getId()));
                    map.put("answer_name",surveyNAMEMap.get(checkBoxList.get(i).getId()));
                    map.put("topic_name",surveyTOPMap.get(checkBoxList.get(i).getId()));
                    map.put("content","");
                    map.put("type",type);
                    map.put("username",psp.getUSERNAME());
                    map.put("userid", psp.getUSERID());
                    map.put("period_of_time",DataManager.myQuestions.data.QuestionlistPage.get(0).period_of_time);
                    mapList.add(map);
                }
            }
        }

    }


}
