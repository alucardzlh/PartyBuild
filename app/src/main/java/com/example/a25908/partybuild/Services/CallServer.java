package com.example.a25908.partybuild.Services;

import android.content.Context;

import com.example.a25908.partybuild.Http.HttpCallBack;
import com.example.a25908.partybuild.Http.ResponseListener;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;


/**
 * Created by alucard on 2016/5/14.
 */
public class CallServer {

    private static CallServer instance;
    private RequestQueue queue;

    public synchronized static CallServer getInstance() {
        if (instance == null) {
            instance = new CallServer();
        }
        return instance;
    }

    private CallServer() {
        queue = NoHttp.newRequestQueue();
    }

    /**
     * 添加一个请求到请求队列
     * @param context 上下文
     * @param request  请求对象
     * @param callback  接受回掉结果
     * @param what 当多个请求用同一个responseListener接受结果时，用来区分请求
     * @param isShowDialog 是否显示dialog
     * @param isCanCancel  是否能被用户取消
     * @param isShowError 是否提示用户错误信息
     * @param <T>
     */
    public <T> void add(Context context, Request<T> request, HttpCallBack<T> callback, int what, boolean isShowDialog, boolean isCanCancel, boolean isShowError) {
    queue.add(what,request,new ResponseListener<T>(request,context,callback,isShowDialog,isCanCancel,isShowError));
    }

    /**
     * 取消这个sign标记的所有请求
     */
    public void cancelBySign(Object sign) {
        queue.cancelBySign(sign);
    }

    /**
     * 取消队列中所有请求
     */
    public void cancelAll() {
        queue.cancelAll();
    }

    /**
     * 退出app时停止所有请求
     */
    public void stopAll() {
        queue.stop();
    }
}





