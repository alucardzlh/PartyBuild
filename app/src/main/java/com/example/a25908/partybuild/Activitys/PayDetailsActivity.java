package com.example.a25908.partybuild.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Views.Toast;
import com.example.a25908.partybuild.wxapi.Constants;
import com.example.a25908.partybuild.wxapi.WXPayEntryActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yolanda.nohttp.RequestMethod;

/**
 * 支付详情页
 *
 * @author weixuan
 */
public class PayDetailsActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    ImageView returnT;
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.pd_qian)
    TextView pd_qian;
    @ViewInject(R.id.rg_zf)
    RadioGroup rg_zf;
    @ViewInject(R.id.rb_zfb)
    RadioButton rb_zfb;//支付宝
    @ViewInject(R.id.rb_wx)
    RadioButton rb_wx;//微信
    @ViewInject(R.id.btn_zf)
    Button btn_zf;
    private Intent intent;
    private int pay;
    private int type = -1;//-1未选择0支付宝1微信

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_details);
        ViewUtils.inject(this);
        intent = getIntent();
        title.setText("支付详情");
        returnT.setVisibility(View.VISIBLE);
        returnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pay = intent.getIntExtra("pay", 0);
        pd_qian.setText("￥" + pay);
        //单选
        rg_zf.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_zfb://支付宝
                        type = 0;
                        break;
                    case R.id.rb_wx://微信
                        type = 1;
                        IWXAPI api = WXAPIFactory.createWXAPI(getApplicationContext(), Constants.APP_ID);
                        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
                        android.widget.Toast.makeText(PayDetailsActivity.this, String.valueOf(isPaySupported), android.widget.Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });
        //立刻支付
        btn_zf.setText("立即支付 ￥" + pay);
        btn_zf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (type) {//-1未选择0支付宝1微信
                    case -1:
                        Toast.show("请选择支付方式");
                        break;
                    case 0:
                        break;
                    case 1:
                        weChatPay();
                        break;
                }
            }
        });
    }

    private void weChatPay() {
        String url = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";//生成测试订单地址
        GsonRequest PayRequest= new GsonRequest(url, RequestMethod.GET);
        CallServer.getInstance().add(PayDetailsActivity.this,PayRequest, GsonCallBack.getInstance(),0x500,true,false,true);
        IWXAPI api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);//Constants.APP_ID


        try {
            PayReq payRequest = new PayReq();
            payRequest.appId =Constants.APP_ID;//应用IDConstants.APP_ID
            payRequest.partnerId = DataManager.wechatpay.partnerid;//微信支付分配的商户号
            payRequest.prepayId = DataManager.wechatpay.prepayid;//微信返回的支付交易会话ID
            payRequest.packageValue = "Sign=WXPay";//暂填固定值Sign=WXPay
            payRequest.nonceStr = DataManager.wechatpay.noncestr;//随机字符串,不长于32位数,推荐随机数生成算法
            payRequest.timeStamp = DataManager.wechatpay.timestamp;//时间戳,请见接口规则-参数设定
            payRequest.sign = DataManager.wechatpay.sign;//签名,详见签名生成算法
            //payRequest.extData= "app data"; // optional
            api.registerApp(Constants.APP_ID);
            android.widget.Toast.makeText(PayDetailsActivity.this, "正常调起支付", android.widget.Toast.LENGTH_SHORT).show();
            api.sendReq(payRequest);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PAY_GET", "异常："+e.getMessage());
            android.widget.Toast.makeText(PayDetailsActivity.this, "异常："+e.getMessage(), android.widget.Toast.LENGTH_SHORT).show();
        }
    }


}
