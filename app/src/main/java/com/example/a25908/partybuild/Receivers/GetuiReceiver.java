package com.example.a25908.partybuild.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.a25908.partybuild.Activitys.DetailsPageActivity;
import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.igexin.sdk.PushConsts;
import com.yolanda.nohttp.RequestMethod;

import static com.example.a25908.partybuild.Utils.URLconstant.PARTYDETAILS;
import static com.example.a25908.partybuild.Utils.URLconstant.SURVEY;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;

/**
 * Created by zhirongkeji on 16/9/26.
 * 个推透传接收器
 */

public class GetuiReceiver extends BroadcastReceiver {
    PartySharePreferences psp;
    public static Handler handler;
    @Override
    public void onReceive(final Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        psp = new PartySharePreferences();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        context.startActivity(new Intent(context, DetailsPageActivity.class).putExtra("fl",110).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 1:
                        context.startActivity(new Intent(context, DetailsPageActivity.class).putExtra("fl",110).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 2:
                        context.startActivity(new Intent(context, DetailsPageActivity.class).putExtra("fl",110).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case 3:
                        context.startActivity(new Intent(context, DetailsPageActivity.class).putExtra("fl",110).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                }
            }
        };
        switch (bundle.getInt(PushConsts.CMD_ACTION)){
            case PushConsts.GET_CLIENTID:

                String cid = bundle.getString("clientid");
                // TODO:处理cid返回
                break;
            case PushConsts.GET_MSG_DATA:
                String taskid = bundle.getString("taskid");
                String messageid = bundle.getString("messageid");
                byte[] payload = bundle.getByteArray("payload");
                if (payload != null) {
                    String data = new String(payload);
                    // TODO:接收处理透传（payload）数据
                    String[]  strs=data.split(",");
//                    for (int i=0;i<strs.length;i++){
//                        Log.e("ggg",strs[i]);
//                    }
                    switch (strs[0]){
                        case "0":
                            //党群动态

                            break;
                        case "1":
                            //党委通知、学习园地、党员扶持、人物长廊
                            GsonRequest Request2 = new GsonRequest(URLINSER +PARTYDETAILS, RequestMethod.GET);
                            Request2.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                            Request2.add("KeyNo", psp.getUSERID());
                            Request2.add("deviceId", new Build().MODEL);
                            Request2.add("username", psp.getUSERNAME());
                            Request2.add("newsid", strs[2]);
                            Request2.add("type", strs[1]);
                            if (strs[1].equals("0")){
                                CallServer.getInstance().add(context, Request2, GsonCallBack.getInstance(), 0x00411, true, false, true);
                            }
                            else if(strs[1].equals("1")){
                                CallServer.getInstance().add(context, Request2, GsonCallBack.getInstance(), 0x00511, true, false, true);
                            }
                            else if(strs[1].equals("2")){
                                CallServer.getInstance().add(context, Request2, GsonCallBack.getInstance(), 0x00611, true, false, true);
                            }
                            else if(strs[1].equals("3")){
                                CallServer.getInstance().add(context,Request2, GsonCallBack.getInstance(),0x20213,true,false,true);
                            }

                            break;
                        case "2":
                            //党建视频

                            break;
                        case "3":
//                            //问卷调查
                            GsonRequest Request= new GsonRequest(URLINSER +SURVEY, RequestMethod.GET);
                            Request.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                            Request.add("KeyNo", psp.getUSERID());
                            Request.add("deviceId", new Build().MODEL);
                            CallServer.getInstance().add(context, Request, GsonCallBack.getInstance(), 0x0011, true, false, true);
                            break;

                    }



                }
                break;
            default:
                break;

        }
    }
}
