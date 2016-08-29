package com.example.a25908.partybuild.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 25908 on 2016/8/29.
 */

public class PartySharePreferences {
    private static final String SPNAME = "PartyBuild";
    private PartySharePreferences() {
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
}
