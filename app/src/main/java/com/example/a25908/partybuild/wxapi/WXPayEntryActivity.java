package com.example.a25908.partybuild.wxapi;

import android.app.Activity;
import android.os.Bundle;


import com.example.a25908.partybuild.Views.Toast;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by zhirongkeji on 16/9/27.
 * 微信Activity
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq baseReq) {

    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法

    /**
     * 0	成功	展示成功页面
     * -1	错误	可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
     * -2	用户取消	无需处理。发生场景：用户不支付了，点击取消，返回APP。
     * @param baseResp
     */
    @Override
    public void onResp(BaseResp baseResp) {
       if(baseResp.getType()== ConstantsAPI.COMMAND_PAY_BY_WX){
           if(baseResp.errCode==0){
               Toast.show("支付成功");
           }else {
               Toast.show("支付失败,请重试");
           }
           finish();
       }
    }











}
