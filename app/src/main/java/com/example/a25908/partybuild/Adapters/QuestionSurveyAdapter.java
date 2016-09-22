package com.example.a25908.partybuild.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Utils.FileUtils;
import com.example.a25908.partybuild.Views.RoundImageView;

import java.util.List;

/**
 * 问卷调查的适配器
 * Created by yusi on 2016/9/2.
 */

public class QuestionSurveyAdapter extends BaseAdapter {
    private Context context;
    private List<DataManager.survey.DataBean.DynamiclistPageBean> list;

    public QuestionSurveyAdapter(Context context, List<DataManager.survey.DataBean.DynamiclistPageBean> list){
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        aViewHodler viewHodler = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item_questionsur,null);
            viewHodler = new aViewHodler();
            viewHodler.bigtitle = (TextView) view.findViewById(R.id.bigtitle);
            viewHodler.smititle = (TextView) view.findViewById(R.id.smititle);
            viewHodler.rg_room = (RadioGroup) view.findViewById(R.id.rg_room);
            viewHodler.rg_qita = (LinearLayout) view.findViewById(R.id.rg_qita);

            viewHodler.cb_room = (LinearLayout) view.findViewById(R.id.cb_room);

            view.setTag(viewHodler);
        }
        else {
            viewHodler = (aViewHodler) view.getTag();
        }
//
//        //添加单选按钮
//        for(int t = 0 ; t < 5 ; i++){
//            RadioButton radio = new RadioButton(context);
//            radio.setText("radio" + i);
//            viewHodler.rg_room.addView(radio);
//        }
//        //添加多选按钮
//        for(int t = 0 ; t < 5 ; i++){
//            CheckBox radio = new CheckBox(context);
//            radio.setText("radio" + i);
//            viewHodler.rg_room.addView(radio);
//        }

        return view;
    }

    class aViewHodler{
        public TextView bigtitle;//大标题
        public TextView smititle;//小标题
        public RadioGroup rg_room;//选项
        public LinearLayout rg_qita;//回答

        public LinearLayout cb_room;//多选容器

    }
}
