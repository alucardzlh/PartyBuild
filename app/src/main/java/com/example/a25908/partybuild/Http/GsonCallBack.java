package com.example.a25908.partybuild.Http;

import com.example.a25908.partybuild.Activitys.AnswerActivity;
import com.example.a25908.partybuild.Activitys.DetailsPageActivity;
import com.example.a25908.partybuild.Activitys.HairDynamicActivity;
import com.example.a25908.partybuild.Activitys.LoginActivity;
import com.example.a25908.partybuild.Activitys.MydataActivity;
import com.example.a25908.partybuild.Activitys.OpinionActivity;
import com.example.a25908.partybuild.Activitys.PalmPartySchoolActivity;
import com.example.a25908.partybuild.Activitys.PartyCommitteeActivity;
import com.example.a25908.partybuild.Activitys.PartyPayActivity;
import com.example.a25908.partybuild.Activitys.PartyVideoActivity;
import com.example.a25908.partybuild.Activitys.PartymembersdetailsActivity;
import com.example.a25908.partybuild.Activitys.PeopleGalleryActivity;
import com.example.a25908.partybuild.Activitys.QuestionSurvey2Activity;
import com.example.a25908.partybuild.Activitys.QuestionsActivity;
import com.example.a25908.partybuild.Activitys.StudyActivity;
import com.example.a25908.partybuild.Activitys.SupportActivity;
import com.example.a25908.partybuild.Fragments.Fragment1;
import com.example.a25908.partybuild.Fragments.Fragment2;
import com.example.a25908.partybuild.Fragments.Fragment3;
import com.example.a25908.partybuild.Fragments.Fragment4;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.Receivers.GetuiReceiver;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.example.a25908.partybuild.Views.Toast;
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
                if (DataManager.userlist == null){
                    LoginActivity.handler.sendEmptyMessage(3);
                }
                else if(DataManager.userlist.message.equals("success")){
                    psp.putLoginStatus(true);
                    psp.putUser(DataManager.userlist);
                    psp.getEMAIL();
                    LoginActivity.handler.sendEmptyMessage(0);
                }
                else if(DataManager.userlist.message.equals("账号或密码错误")){
                    LoginActivity.handler.sendEmptyMessage(2);
                }

                else{
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
                    if (DataManager.MyDataList.status.equals("mobile_error")){
                        Toast.show("手机号码格式不支持");
                        MydataActivity.handler.sendEmptyMessage(0);
                    }
                    MydataActivity.handler.sendEmptyMessage(0);
                }else{
                    MydataActivity.handler.sendEmptyMessage(1);
                }
                break;
            case 0x0023://请求单位部门
                jsonString = (String) response.get();
                DataManager.MyDataBMList=gson.fromJson(jsonString,DataManager.MyDataBM.class);
                Fragment4.handler.sendEmptyMessage(2);
                break;
            /**
             * //修改头像
             */
            case 0x0022://修改头像
                jsonString = (String) response.get();
                DataManager.messageMar=gson.fromJson(jsonString,DataManager.message.class);
                break;
            /**
             * //党员名册
             */
            case 0x003:
                jsonString = (String) response.get();
                DataManager.PartyerList=gson.fromJson(jsonString,DataManager.Partyer.class);
                Fragment2.handler.sendEmptyMessage(0);
                break;
            /**
             * //党员名册（）
             */
            case 0x0033:
                jsonString = (String) response.get();
                DataManager.PartyerList=gson.fromJson(jsonString,DataManager.Partyer.class);
                HairDynamicActivity.handler.sendEmptyMessage(2);
                break;
            /**
             * //党员名册(以部门为主)
             */
            case 0x00323:
                jsonString = (String) response.get();
                DataManager.zzPlayerList=gson.fromJson(jsonString,DataManager.ZZplayer.class);
                Fragment2.handler.sendEmptyMessage(1);
                break;
            /**
             * //党员名册备注修改
             */
            case 0x0031:
                jsonString = (String) response.get();
                DataManager.messageMar=gson.fromJson(jsonString,DataManager.message.class);
                if(DataManager.messageMar.message.equals("success")){
                    PartymembersdetailsActivity.handler.sendEmptyMessage(0);
                }else{
                    PartymembersdetailsActivity.handler.sendEmptyMessage(1);
                }
                break;

            case 0x0299://二维码生成
                jsonString = (String) response.get();
                DataManager.messageMar=gson.fromJson(jsonString,DataManager.message.class);
                if (DataManager.messageMar.message.equals("success")){
                    PartymembersdetailsActivity.handler.sendEmptyMessage(10);
                }
                else {
                    PartymembersdetailsActivity.handler.sendEmptyMessage(11);
                }

                break;
            /**
             * //党委通知
             */
            case 0x004:
                jsonString = (String) response.get();
                DataManager.paertyCommList=gson.fromJson(jsonString,DataManager.paertyComm.class);
                if(DataManager.paertyCommList.data.commentList.size()>0 && DataManager.paertyCommList.data.commentList!=null){
                    Fragment1.handler.sendEmptyMessage(0);
                }else{
                    Fragment1.handler.sendEmptyMessage(500);
                }
                break;
            /**
             * //党委通知详情
             */
            case 0x0041:
                jsonString = (String) response.get();
                DataManager.partyCommDetailsList=gson.fromJson(jsonString,DataManager.partyCommDetails.class);
                PartyCommitteeActivity.handler.sendEmptyMessage(0);
                break;
            /**
             * //党委通知、人物长廊、学习园地、支部活动、党员扶持推送详情
             */
            case 0x00411:
                jsonString = (String) response.get();
                DataManager.partyCommDetailsList=gson.fromJson(jsonString,DataManager.partyCommDetails.class);
                if (DataManager.partyCommDetailsList.data.NewsList!=null){//DataManager.partyCommDetailsList.message.equals("success")
                    GetuiReceiver.handler.sendEmptyMessage(0);
                }else {
                    GetuiReceiver.handler.sendEmptyMessage(8);
                }

                break;
            case 0x0124://支部活动
                jsonString = (String) response.get();
                DataManager.paertyCommList=gson.fromJson(jsonString,DataManager.paertyComm.class);
                if(DataManager.paertyCommList.data.commentList.size()>0 && DataManager.paertyCommList.data.commentList!=null){
                    Fragment1.handler.sendEmptyMessage(10);
                }else{
                    Fragment1.handler.sendEmptyMessage(500);
                }
                break;
            case 0x01245://支部活动推送
                jsonString = (String) response.get();
                DataManager.partyCommDetailsList=gson.fromJson(jsonString,DataManager.partyCommDetails.class);
                if(DataManager.partyCommDetailsList.message.equals("success")){
                    GetuiReceiver.handler.sendEmptyMessage(4);
                }
                break;
            /**
             * //学习园地
             */
            case 0x005:
                jsonString = (String) response.get();
                DataManager.paertyCommList=gson.fromJson(jsonString,DataManager.paertyComm.class);
                if(DataManager.paertyCommList.data.commentList.size()>0 && DataManager.paertyCommList.data.commentList!=null){
                    Fragment1.handler.sendEmptyMessage(4);
                }else{
                    Fragment1.handler.sendEmptyMessage(500);
                }
                break;
            case 0x0051://学习园地详情
                jsonString = (String) response.get();
                DataManager.partyCommDetailsList=gson.fromJson(jsonString,DataManager.partyCommDetails.class);
                StudyActivity.handler.sendEmptyMessage(0);
                break;
            case 0x00511://学习园地详情
                jsonString = (String) response.get();
                DataManager.partyCommDetailsList=gson.fromJson(jsonString,DataManager.partyCommDetails.class);
                GetuiReceiver.handler.sendEmptyMessage(1);
                break;
            case 0x006://党员扶持
                jsonString = (String) response.get();
                DataManager.paertyCommList=gson.fromJson(jsonString,DataManager.paertyComm.class);
                if(DataManager.paertyCommList.data.commentList.size()>0 && DataManager.paertyCommList.data.commentList!=null){
                    Fragment1.handler.sendEmptyMessage(6);
                }else{
                    Fragment1.handler.sendEmptyMessage(500);
                }
                break;
            case 0x0061://党员扶持详情
                jsonString = (String) response.get();
                DataManager.partyCommDetailsList=gson.fromJson(jsonString,DataManager.partyCommDetails.class);
                SupportActivity.handler.sendEmptyMessage(0);
                break;
            case 0x00611://党员扶持详情
                jsonString = (String) response.get();
                DataManager.partyCommDetailsList=gson.fromJson(jsonString,DataManager.partyCommDetails.class);
                GetuiReceiver.handler.sendEmptyMessage(2);
                break;
            case 0x007://查询 党建通知，学习园地，党员扶持 详情的评论
                jsonString = (String) response.get();
                DataManager.CommentMarList=gson.fromJson(jsonString,DataManager.CommentMar.class);
                if(DataManager.CommentMarList.data.commentList.size()>0) {
                    DetailsPageActivity.handler.sendEmptyMessage(2);
                }
                break;
            case 0x0071://添加  党建通知，学习园地，党员扶持 详情的评论
                jsonString = (String) response.get();
                DataManager.messageMar=gson.fromJson(jsonString,DataManager.message.class);
                if(DataManager.messageMar.message.equals("success")){
                    DetailsPageActivity.handler.sendEmptyMessage(0);
                }else{
                    DetailsPageActivity.handler.sendEmptyMessage(1);
                }
                break;
            case 0x008://文档中心
                jsonString = (String) response.get();
                DataManager.DucomentRoomList=gson.fromJson(jsonString,DataManager.DucomentRoom.class);
                Fragment1.handler.sendEmptyMessage(7);
                break;
            case 0x009://党建视频
                jsonString = (String) response.get();
                DataManager.partyvideoList=gson.fromJson(jsonString,DataManager.partyvideo.class);
                Fragment1.handler.sendEmptyMessage(3);
                break;
            case 0x0091://党建视频详情
                jsonString = (String) response.get();
                DataManager.mypartyvideo=gson.fromJson(jsonString,DataManager.Mypartyvideo.class);
                PartyVideoActivity.handler.sendEmptyMessage(0);
                break;
            case 0x00912://党建视频推送详情
                jsonString = (String) response.get();
                DataManager.mypartyvideo=gson.fromJson(jsonString,DataManager.Mypartyvideo.class);
                PartyVideoActivity.handler.sendEmptyMessage(0);
                break;
            case 0x0010://在线答疑
                jsonString = (String) response.get();
                DataManager.FAQmarList=gson.fromJson(jsonString,DataManager.FAQmar.class);
                Fragment1.handler.sendEmptyMessage(5);
                break;
            case 0x00101://在线答疑之想组织提问
                jsonString = (String) response.get();
                DataManager.messageMar=gson.fromJson(jsonString,DataManager.message.class);
                if(DataManager.messageMar.message.equals("success")){
                    QuestionsActivity.handler.sendEmptyMessage(0);
                }else{
                    QuestionsActivity.handler.sendEmptyMessage(1);
                }
                break;
            case 0x001011://再次在线答疑
                jsonString = (String) response.get();
                DataManager.FAQmarList=gson.fromJson(jsonString,DataManager.FAQmar.class);
                AnswerActivity.handler.sendEmptyMessage(0);
                break;
            case 0x0011://问卷调查
                jsonString = (String) response.get();
                DataManager.myQuestions=gson.fromJson(jsonString,DataManager.MyQuestions.class);
                if(DataManager.myQuestions.message.equals("success")){
                    if (DataManager.myQuestions.status.equals("repeated")){
                        Fragment1.handler.sendEmptyMessage(9);
                    }
                    else{
                        Fragment1.handler.sendEmptyMessage(8);
                    }
                }else{
                    Fragment1.handler.sendEmptyMessage(500);
                }

                break;
            case 0x001221://问卷调查推送
                jsonString = (String) response.get();
                DataManager.myQuestions=gson.fromJson(jsonString,DataManager.MyQuestions.class);
                if(DataManager.myQuestions.message.equals("success")){
                    if (DataManager.myQuestions.status.equals("repeated")){
                        GetuiReceiver.handler.sendEmptyMessage(6);
                    }
                    else{
                        GetuiReceiver.handler.sendEmptyMessage(5);
                    }
                }

                break;
            case 0x0012://问卷调查提交
                jsonString = (String) response.get();
                DataManager.myQuestions=gson.fromJson(jsonString,DataManager.MyQuestions.class);
                if( DataManager.myQuestions.message.equals("success")){
                    if (!DataManager.myQuestions.status.equals("REPEATED")){
                        QuestionSurvey2Activity.handler.sendEmptyMessage(0);
                    }
                }

                break;
            case 0x102://意见反馈提交
                jsonString = (String) response.get();
                map = gson.fromJson(jsonString,new TypeToken<Map<String, Object>>(){}.getType());
                if (map.get("message").equals("success")){
                    OpinionActivity.handler.sendEmptyMessage(0);
                }
                break;
            case 0x103://我的党费
                jsonString = (String) response.get();
                DataManager.myPartyPaylist = gson.fromJson(jsonString,DataManager.MyPartyPay.class);
                if (DataManager.myPartyPaylist.message.equals("success")){
                    Fragment4.handler.sendEmptyMessage(1);
                }

                break;
            case 0x104://党费查询
                jsonString = (String) response.get();
                DataManager.myPartyPaylist = gson.fromJson(jsonString,DataManager.MyPartyPay.class);
                if (DataManager.myPartyPaylist.message.equals("success")){
                    PartyPayActivity.handler.sendEmptyMessage(0);
                }

                break;
            case 0x105://党费查询（党员名册）
                jsonString = (String) response.get();
                DataManager.myPartyPaylist = gson.fromJson(jsonString,DataManager.MyPartyPay.class);
                if (DataManager.myPartyPaylist.message.equals("success")){
                    PartymembersdetailsActivity.handler.sendEmptyMessage(17);
                }

            case 0x02999://退出
                jsonString = (String) response.get();
                DataManager.messageMar=gson.fromJson(jsonString,DataManager.message.class);


                break;



            case 0x201://人物长廊
                jsonString = (String) response.get();
                DataManager.palmparty = gson.fromJson(jsonString,DataManager.MyPALMPARTY.class);
                if (DataManager.palmparty.message.equals("success")){
                    PalmPartySchoolActivity.handler.sendEmptyMessage(1);
                }
                break;
            case 0x2023://人物长廊详情
                jsonString = (String) response.get();
                DataManager.partyCommDetailsList=gson.fromJson(jsonString,DataManager.partyCommDetails.class);
                PeopleGalleryActivity.handler.sendEmptyMessage(1);

                break;
            case 0x20213://人物长廊推送详情
                jsonString = (String) response.get();
                DataManager.partyCommDetailsList=gson.fromJson(jsonString,DataManager.partyCommDetails.class);
                GetuiReceiver.handler.sendEmptyMessage(3);

                break;
            case 0x202://党的章程
                jsonString = (String) response.get();
                DataManager.myTCTPdanggui = gson.fromJson(jsonString,DataManager.TCTPdanggui.class);
                if (DataManager.myTCTPdanggui.message.equals("success")){
                    PalmPartySchoolActivity.handler.sendEmptyMessage(2);
                }
                break;

            case 0x203://党规党纪
                jsonString = (String) response.get();
                DataManager.myTCTPdanggui = gson.fromJson(jsonString,DataManager.TCTPdanggui.class);
                if (DataManager.myTCTPdanggui.message.equals("success")){
                    PalmPartySchoolActivity.handler.sendEmptyMessage(3);
                }
                break;
            case 0x302://动态查看
                jsonString = (String) response.get();
                DataManager.mydynamic = gson.fromJson(jsonString,DataManager.Mydynamic.class);
                if (DataManager.mydynamic.message.equals("success")){
                    Fragment3.handler.sendEmptyMessage(1);
                }
                break;
            case 0x3021://动态刷新
                jsonString = (String) response.get();
                DataManager.mydynamic = gson.fromJson(jsonString,DataManager.Mydynamic.class);
                if (DataManager.mydynamic.message.equals("success")){
                    Fragment3.handler.sendEmptyMessage(4);
                }
                break;
            case 0x3022://动态查看加载更多
                jsonString = (String) response.get();
                DataManager.mydynamic = gson.fromJson(jsonString,DataManager.Mydynamic.class);
                if (DataManager.mydynamic.message.equals("success")){
                    Fragment3.handler.sendEmptyMessage(5);
                }
                break;
            case 0x301://动态发布
                jsonString = (String) response.get();
                map = gson.fromJson(jsonString,new TypeToken<Map<String, Object>>(){}.getType());
                if(map==null) {
                    HairDynamicActivity.handler.sendEmptyMessage(2);
                }
                else if (map.get("message").equals("success")){
                    Fragment3.handler.sendEmptyMessage(6);
                }
                break;
            case 0x303://动态评论、点赞取消赞
                jsonString = (String) response.get();
                map = gson.fromJson(jsonString,new TypeToken<Map<String, Object>>(){}.getType());
                if (!map.get("message").equals("success")){
                    Fragment3.handler.sendEmptyMessage(2);
                }
                break;
            case 0x304://动态删除
                jsonString = (String) response.get();
                map = gson.fromJson(jsonString,new TypeToken<Map<String, Object>>(){}.getType());
                if (map.get("message").equals("success")){
                    Fragment3.handler.sendEmptyMessage(3);
                }
                break;
            case 0x305://动态图片获取
                jsonString = (String) response.get();
                DataManager.myimageid = gson.fromJson(jsonString,DataManager.Imageid.class);
                if (DataManager.myimageid.message.equals("success")){
                    Fragment3.handler.sendEmptyMessage(7);
                }
                break;
            case 0x251://版本更新
                jsonString = (String) response.get();
                DataManager.newVersion=gson.fromJson(jsonString,DataManager.NewVersion.class);
                break;
            case 0x250://支付宝密钥
                jsonString = (String) response.get();
                DataManager.myzhifubao=gson.fromJson(jsonString,DataManager.zhifubao.class);
                if (DataManager.myzhifubao == null){
                    Toast.show("网络异常，支付宝组件无法使用，请调试网络环境！");
                }
                else if(!DataManager.myzhifubao.message.equals("success")){
                    Toast.show("系统异常，支付宝组件无法使用！");
                }
                break;
            case 0x500://微信支付实体类
                jsonString = (String) response.get();
                DataManager.wechatpay=gson.fromJson(jsonString,DataManager.WechatPay.class);

        }
    }

    @Override
    public void onFailed(int what, Response response) {

    }
}
