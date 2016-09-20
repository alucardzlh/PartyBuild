package com.example.a25908.partybuild.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        vh.mpay_time.setText(list1.get(position).PERIOD_OF_TIME);
        vh.mpay_money.setText(list1.get(position).MONEY);
        if (list1.get(position).TYPE.equals("0")){
            vh.mpay_type.setText("未缴费");
        }
        else {
            vh.mpay_type.setText("已缴费");
        }

        return view;
    }

    public class ViewHolder {
        TextView mpay_time;
        TextView mpay_money;
        TextView mpay_type;
    }
}
