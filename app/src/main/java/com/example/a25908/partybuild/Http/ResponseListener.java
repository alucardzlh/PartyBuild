package com.example.a25908.partybuild.Http;

import android.content.Context;
import android.content.DialogInterface;

import com.example.a25908.partybuild.Views.Toast;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.NotFoundCacheError;
import com.yolanda.nohttp.error.ServerError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.URLError;
import com.yolanda.nohttp.error.UnKnownHostError;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import Dialogs.WaitDialog;

/**
 * Created by alucard on 2016/5/14.
 */
public class ResponseListener<T> implements OnResponseListener<T> {
    private Request<T> mRequest;
    private HttpCallBack<T> callback;
    private WaitDialog mDialog;
    private boolean isShowError;

    public ResponseListener(Request<T> request, Context context, HttpCallBack<T> callback, boolean isShowDialog, boolean isCanCancel, boolean isShowError) {
        this.callback = callback;
        this.mRequest = request;
        this.isShowError = isShowError;
        if (context != null && isShowDialog) {
            mDialog = new WaitDialog(context);
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    mRequest.cancel();
                }
            });
        }
    }

    @Override
    public void onStart(int what) {

    }

    @Override
    public void onFailed(int what, Response<T> response) {

        if (isShowError) {
            /*if (exception instanceof ClientError) {
                Toast.show("客户端发生错误");
            } else*/
            if (response.getException() instanceof ServerError) {
                Toast.show("服务器发生错误");
            } else if (response.getException() instanceof NetworkError) {
                Toast.show("请检查网络");
            } else if (response.getException() instanceof TimeoutError) {
                Toast.show("请求超时，网络不好或服务器不稳定");
            } else if (response.getException() instanceof UnKnownHostError) {
                Toast.show("未发现指定服务器");
            } else if (response.getException() instanceof URLError) {
                Toast.show("URL错误");
            } else if (response.getException() instanceof NotFoundCacheError) {
                Toast.show("没有发现缓存");
            } else {
                Toast.show("服务器连接失败");
            }

        }


        if(callback!=null){
         callback.onFailed(what, response);

        }
    }

    @Override
    public void onSucceed(int what, Response response) {
        if (callback != null)
            callback.onSucceed(what, response);
    }

//    @Override
//    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
//        if (isShowError) {
//            /*if (exception instanceof ClientError) {
//                Toast.show("客户端发生错误");
//            } else*/
//            if (exception instanceof ServerError) {
//                Toast.show("服务器发生错误");
//            } else if (exception instanceof NetworkError) {
//                Toast.show("请检查网络");
//            } else if (exception instanceof TimeoutError) {
//                Toast.show("请求超时，网络不好或服务器不稳定");
//            } else if (exception instanceof UnKnownHostError) {
//                Toast.show("未发现指定服务器");
//            } else if (exception instanceof URLError) {
//                Toast.show("URL错误");
//            } else if (exception instanceof NotFoundCacheError) {
//                Toast.show("没有发现缓存");
//            } else {
//                Toast.show("服务器连接失败");
//            }
//        }
//        if (callback != null)
//            callback.onFailed(what, url, tag, exception, responseCode, networkMillis);
//    }

    @Override
    public void onFinish(int what) {

    }
}
