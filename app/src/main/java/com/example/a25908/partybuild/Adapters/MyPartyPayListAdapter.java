package com.example.a25908.partybuild.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.a25908.partybuild.Activitys.MyPartyPayActivity;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;

import java.util.List;

/**
 * @author yusi
 * 我的党费lsitAdapter
 */
public class MyPartyPayListAdapter extends BaseAdapter {
    private Context context;
    private List<DataManager.MyPartyPay.DataBean.PartyMemberlistBean> list1;
    private int Allmoney = 0;

    public int getAllmoney(){
        return Allmoney;
    }

    public MyPartyPayListAdapter(Context context, List<DataManager.MyPartyPay.DataBean.PartyMemberlistBean> list1) {
        this.context = context;
        this.list1 = list1;
    }

    @Override
    public int getCount() {
        return list1.size();
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder vh = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.activity_my_party_pay_item, null);
            vh = new ViewHolder();
            vh.mpay_time=(TextView) view.findViewById(R.id.mpay_time);
            vh.mpay_money=(TextView) view.findViewById(R.id.mpay_money);
            vh.mpay_type=(TextView) view.findViewById(R.id.mpay_type);
            vh.pay_cb = (CheckBox) view.findViewById(R.id.pay_cb);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        vh.mpay_time.setText(list1.get(position).PERIOD_OF_TIME);
        vh.mpay_money.setText(list1.get(position).MONEY + "  元");
        if (list1.get(position).TYPE.equals("0")){
            vh.mpay_type.setText("未缴费");
            vh.mpay_type.setTextColor(Color.rgb(160,160,160));
            vh.pay_cb.setVisibility(View.VISIBLE);
            vh.pay_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        Allmoney = Allmoney + Integer.parseInt(list1.get(position).MONEY);
                        MyPartyPayActivity.handler.sendEmptyMessage(0);
                    }
                    else {
                        Allmoney = Allmoney - Integer.parseInt(list1.get(position).MONEY);
                        MyPartyPayActivity.handler.sendEmptyMessage(0);
                    }
                }
            });
        }
        else {
            vh.mpay_type.setTextColor(Color.rgb(74,207,26));
            vh.mpay_type.setText("已缴费");
            vh.pay_cb.setVisibility(View.GONE);
        }







        return view;
    }

    public class ViewHolder {
        TextView mpay_time;//时间
        TextView mpay_money;//钱
        TextView mpay_type;//缴费状态
        CheckBox pay_cb;//选中状态
    }
}
