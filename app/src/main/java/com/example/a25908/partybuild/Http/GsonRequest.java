package com.example.a25908.partybuild.Http;

import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.RestRequest;
import com.yolanda.nohttp.rest.StringRequest;

/**
 * Created by alucard on 2016/5/14.
 */
public class GsonRequest extends RestRequest {

    public GsonRequest(String url) {
        super(url);
    }

    public  GsonRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public Object parseResponse(Headers responseHeaders, byte[] responseBody) throws Throwable {
        return StringRequest.parseResponseString(responseHeaders,responseBody);
    }

//    @Override
//    public Object parseResponse(String url, Headers responseHeaders, byte[] responseBody) {
//        return StringRequest.parseResponseString(url,responseHeaders,responseBody);
//    }

//    @Override
//    public String getAccept() {
//        return "application/json,text/html,application/xhtml+xml,application/xml,*/*;q=0.9";
//    }

//
//    @Override
//    public void setAccept(String accept) {
//        super.setAccept(accept);
//    }
}
