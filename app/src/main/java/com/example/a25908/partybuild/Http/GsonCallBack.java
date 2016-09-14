package com.example.a25908.partybuild.Http;

import com.example.a25908.partybuild.Activitys.LoginActivity;
import com.example.a25908.partybuild.Activitys.MydataActivity;
import com.example.a25908.partybuild.Activitys.OpinionActivity;
import com.example.a25908.partybuild.Fragments.Fragment4;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yolanda.nohttp.rest.Response;

import java.util.Map;

/**
 * Created by 25908 on 2016/8/29.
 */

public class GsonCallBack implements HttpCallBack {
    Gson gson;
    static Map<String, Object> map;//公用map
    String jsonString;//公用String
    private static GsonCallBack instance;
    PartySharePreferences psp = PartySharePreferences.getLifeSharedPreferences();

    public static GsonCallBack getInstance() {
        if (instance == null) {
            instance = new GsonCallBack();
        }
        return instance;
    }

    @Override
    public void onSucceed(int what, Response response) {
        gson = new Gson();
        switch (what) {
            case 0x001://登陆
                jsonString = (String) response.get();
                DataManager.userlist=gson.fromJson(jsonString,DataManager.User.class);
                if(DataManager.userlist.message.equals("success")){
                    psp.putLoginStatus(true);
                    psp.putUser(DataManager.userlist);
                    psp.getEMAIL();
                    LoginActivity.handler.sendEmptyMessage(0);
                }else{
                    LoginActivity.handler.sendEmptyMessage(1);
                }
                break;
            case 0x002://请求个人资料
                jsonString = (String) response.get();
                DataManager.MyDataList=gson.fromJson(jsonString,DataManager.MyData.class);
                Fragment4.handler.sendEmptyMessage(0);
                break;
            case 0x0021://修改个人资料
                jsonString = (String) response.get();
                DataManager.MyDataList=gson.fromJson(jsonString,DataManager.MyData.class);
                if( DataManager.MyDataList.message.equals("success")){
                    MydataActivity.handler.sendEmptyMessage(0);
                }else{
                    MydataActivity.handler.sendEmptyMessage(1);
                }
                break;
            case 0x0022://修改头像
                jsonString = (String) response.get();
                DataManager.MyDataList=gson.fromJson(jsonString,DataManager.MyData.class);
                break;
            case 0x003://党员名册
                jsonString = (String) response.get();
                DataManager.PartyerList=gson.fromJson(jsonString,DataManager.Partyer.class);
                break;
            case 0x102://意见反馈提交
                jsonString = (String) response.get();
                map = gson.fromJson(jsonString,new TypeToken<Map<String, Object>>(){}.getType());
                if (map.get("message").equals("success")){
                    OpinionActivity.handler.sendEmptyMessage(0);
                }
                else {
                    OpinionActivity.handler.sendEmptyMessage(1);
                }
                break;
            case 0x103://我的党费
                jsonString = (String) response.get();
                DataManager.myPartyPaylist = gson.fromJson(jsonString,DataManager.MyPartyPay.class);
                if (DataManager.myPartyPaylist.message.equals("success")){
                    Fragment4.handler.sendEmptyMessage(1);
                }

                break;
        }
    }

    @Override
    public void onFailed(int what, Response response) {

    }
}
