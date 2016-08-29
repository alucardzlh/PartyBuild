package com.example.a25908.partybuild.Http;

import com.google.gson.Gson;
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


    public static GsonCallBack getInstance() {
        if (instance == null) {
            instance = new GsonCallBack();
        }
        return instance;
    }




    @Override
    public void onSucceed(int what, Response response) {

    }

    @Override
    public void onFailed(int what, Response response) {

    }
}
