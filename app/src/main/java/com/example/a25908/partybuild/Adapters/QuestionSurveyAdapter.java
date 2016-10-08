package com.example.a25908.partybuild.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.a25908.partybuild.Activitys.QuestionSurveyActivity;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Views.Toast;

import java.util.List;

/**
 * 问卷调查的适配器
 * Created by yusi on 2016/9/2.
 */

public class QuestionSurveyAdapter extends BaseAdapter {
    private Context context;
    private List<DataManager.MyQuestions.DataBean.QuestionlistPageBean> list;
//    private List<RadioButton> radioButtonList;// 装载RadioButton的集合

    private int radioButtonSize = 0;// 计算布局中RadioButton的个数
    private int checkBoxSize = 0;// 计算布局中CheckBox个数

    public int answer_id;//回答id
    public int topic_id;//题目id
    public String content = "";//内容

    public QuestionSurveyAdapter(Context context, List<DataManager.MyQuestions.DataBean.QuestionlistPageBean> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        aViewHodler viewHodler = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item_questionsur,null);
            viewHodler = new aViewHodler();
            viewHodler.smititle = (TextView) view.findViewById(R.id.smititle);
            viewHodler.biglin = (LinearLayout) view.findViewById(R.id.biglin);
            view.setTag(viewHodler);
        }
        else {
            viewHodler = (aViewHodler) view.getTag();
        }
        viewHodler.smititle.setText(list.get(i).topic_name);

        if (list.get(i).type==0){//单选
            RadioGroup radioGroup = new RadioGroup(context);
            // 设置RadioGroup为垂直
            radioGroup.setOrientation(RadioGroup.VERTICAL);
            radioGroup.setBackgroundResource(R.drawable.maintop_shape3);
            RadioButton radio;
            for(int b=0; b<list.get(i).alist.size(); b++){
                radio = new RadioButton(context);
                radio.setText(list.get(i).alist.get(b).answer_name);
                // radioGroup.setTag(list.get(i).surveyOptionID);
                // 设置id，我是将封装数据中选项有一个唯一的id当做该控件id了
//                radio.setId(list.get(i).alist.get(b).answer_id);
                radio.setId(b);
//                surveyIDMap.put(list.get(i).surveyOptionID, moduleSurveyID);
//                radioButtonList.add(radio);
                radioGroup.addView(radio);
            }
            viewHodler.biglin.addView(radioGroup);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i2) {
                    if (list.get(i).alist.get(i2).answer_name.equals("其他")){
                        final EditText ed = new EditText(context);
                        ed.setHint("请输入您的意见...");
                        ed.setLines(3);
                        final int a = list.get(i).alist.get(i2).topic_id;
                        final int b = list.get(i).alist.get(i2).answer_id;
                        new AlertDialog.Builder(context)
                                .setTitle("其他意见")
                                .setView(ed)
                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        content = ed.getText().toString();
                                        topic_id = a;
                                        answer_id = b;
                                        QuestionSurveyActivity.handler.sendEmptyMessage(0);
                                    }
                                })
                        .setNegativeButton("取消",null)
                        .setCancelable(false)
                        .show();
                    }
                    else {
                        topic_id = list.get(i).alist.get(i2).topic_id;
                        answer_id = list.get(i).alist.get(i2).answer_id;
                        QuestionSurveyActivity.handler.sendEmptyMessage(0);
                    }
                }
            });
        }
        else {//多选
            LinearLayout layout = new LinearLayout(context);
            // 设置LinearLayout垂直
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setBackgroundResource(R.drawable.maintop_shape3);
            for(int b=0; b<list.get(i).alist.size(); b++) {
                final CheckBox cb = new CheckBox(context);
                cb.setText(list.get(i).alist.get(b).answer_name);
                // cb.setTag(list.get(i).surveyOptionID);
                // 设置id，与RadioButton一样
                cb.setId(b);
//                surveyIDMap.put(list.get(i).surveyOptionID, moduleSurveyID);
//                checkBoxList.add(cb);
                layout.addView(cb);
                final int d = list.get(i).alist.get(b).topic_id;
                final int c = list.get(i).alist.get(b).answer_id;
                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b2) {
                        if (b2){
                            Toast.show("选中了"+cb.getText().toString());
                            topic_id = d;
                            answer_id = c;
                            QuestionSurveyActivity.handler.sendEmptyMessage(1);
                        }
                    }
                });
            }
            viewHodler.biglin.addView(layout);
        }



        return view;
    }

    class aViewHodler{
        public TextView smititle;//小标题
        public LinearLayout biglin;//

    }
}
