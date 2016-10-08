package com.example.a25908.partybuild;

import android.content.Context;

import com.example.a25908.partybuild.Utils.FileUtils;
import com.igexin.sdk.PushManager;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;

/**
 * Created by 25908 on 2016/8/29.
 * 全局
 */

public class Application extends android.app.Application {
    private static Application instance;
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        NoHttp.initialize(this);
        //打开Nohttp的调试模式
        Logger.setDebug(true);
        //设置Nohttp的日志tag
        Logger.setTag("forNoHttp");
        //个推代码
        PushManager.getInstance().initialize(this);
        //判定文件夹是否存在
        FileUtils.FileNewsExists();

    }

    /**
     * 得到应用程序的application
     *
     * @return {@link Application}
     */
    public static Application getInstance() {
        return instance;
    }

    public static Context getContext(){
        return mContext;
    }

}
