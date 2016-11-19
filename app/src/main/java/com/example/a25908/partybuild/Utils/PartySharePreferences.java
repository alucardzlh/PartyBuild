package com.example.a25908.partybuild.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.a25908.partybuild.Model.DataManager;

/**
 * Created by 25908 on 2016/8/29.
 */

public class PartySharePreferences {
    private static final String SPNAME = "PartyBuild";
    public PartySharePreferences() {
    }//私有构造方法

    private static PartySharePreferences psp;
    public static SharedPreferences sp;

    //初始化本地xml文件
    public static void init(Context ctx) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(SPNAME, ctx.MODE_PRIVATE);
        }
    }

    //单例模式
    public static PartySharePreferences getLifeSharedPreferences() {
        if (psp == null) {
            psp = new PartySharePreferences();
        }
        return psp;
    }

    /**
     * 保存用户信息
     * @param user 用户实体类
     */
    public void putUser(DataManager.User user) {
        if(sp!=null){
            if(user==null){
                Editor editor =sp.edit();
                editor.clear();
                editor.commit();
                return;
            }
            Editor editor = sp.edit();
            editor.putString("EMAIL", user.data.email+"");
            editor.putString("PASSWORD", user.data.password+"");
            editor.putString("USERNAME", user.data.username+"");
            editor.putString("ICONSTEAM", user.data.head_img+"");
            editor.putString("PARTYTIME", user.data.join_party+"");
            editor.putString("USERID", user.data.userid+"");
            editor.commit();
    }
    }
    /**
     * 保存cid
     */
    public void putCid(String b){
        if(sp!=null){
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("Cid", b);
            editor.commit();}
    }
    public String getCid() {
        if(sp!=null) {
            return sp.getString("Cid",null);
        }else{
            return null;
        }
    }
    /**
     * 保存是否第一次进入app
     */
    public void putAppStatus(Boolean b){
        if(sp!=null){
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("AppStatus", b);
            editor.commit();}
    }
    public Boolean getAppStatus(){
        if(sp!=null) {
            return sp.getBoolean("AppStatus", false);
        }
        return false;
    }

    /**
     * 保存是否登录状态
     * @param b
     */
    public void putLoginStatus(Boolean b){
        if(sp!=null){
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("LoginStatus", b);
            editor.commit();}
    }
    public Boolean getLoginStatus(){
        if(sp!=null) {
            return sp.getBoolean("LoginStatus", false);
        }
        return false;
    }
    /**
     * 获取头像base64位图
     * @return
     */
    public String getICONSTEAM() {
        if(sp!=null) {
            return sp.getString("ICONSTEAM", null);
        }else{
            return "";
        }
    }
    public void putICONSTEAM(String ICONSTEAM) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("ICONSTEAM", ICONSTEAM);
        editor.commit();
    }
    /**
     * 获取用户名
     * @return
     */
    public String getUSERNAME() {
        if(sp!=null) {
            return sp.getString("USERNAME", null);
        }else{
            return "";
        }
    }
    public void putUSERNAME(String USERNAME) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("USERNAME", USERNAME);
        editor.commit();
    }
    /**
     * 获取EMAIL账号
     * @return
     */
    public String getEMAIL() {
        if(sp!=null) {
            return sp.getString("EMAIL", null);
        }else{
            return "";
        }
    }
    public void putEMAIL(String EMAIL) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("EMAIL", EMAIL);
        editor.commit();
    }
    /**
     * 获取密码
     * @return
     */
    public String getPASSWORD() {
        if(sp!=null) {
            return sp.getString("PASSWORD", null);
        }else{
            return "";
        }
    }
    public void putPASSWORD(String PASSWORD) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("PASSWORD", PASSWORD);
        editor.commit();
    }
    /**
     * 获取用户ID
     * @return
     */
    public String getUSERID() {
        if(sp!=null) {
            return sp.getString("USERID", null);
        }else{
            return "";
        }
    }

    /**
     * 获取入党时间
     * @return
     */
    public String getPARTYTIME() {
        if(sp!=null) {
            return sp.getString("PARTYTIME", null);
        }else{
            return "";
        }
    }
}
