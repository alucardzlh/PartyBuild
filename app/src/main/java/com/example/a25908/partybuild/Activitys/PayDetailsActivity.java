package com.example.a25908.partybuild.Activitys;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.H5PayActivity;
import com.alipay.sdk.app.PayTask;
import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.AuthResult;
import com.example.a25908.partybuild.Utils.OrderInfoUtil2_0;
import com.example.a25908.partybuild.Utils.PayResult;
import com.example.a25908.partybuild.Views.Toast;
import com.example.a25908.partybuild.wxapi.Constants;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yolanda.nohttp.RequestMethod;

import java.util.Map;

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
    // 商户PID
    public static final String PID = "2088121879452764";
    //APPID
    public static final String APPID = "2016101202114397";
    // 商户收款账号
    public static final String TARGET_ID ="13979151616"; //
    //支付宝密钥
    public static  String RSA_PRIVATE;
    // 支付宝公钥
    public static final String RSA_PUBLIC = "";//可不填
    private static final int SDK_PAY_FLAG = 1;//支付宝支付判定
    private static final int SDK_AUTH_FLAG = 2;//支付宝授权判定
    String 缴费金额="0.01",缴费类型="党费支付",缴费时间,缴费地址,订单编号;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_details);
        ViewUtils.inject(this);
        String stt = new String(Base64.decode(DataManager.myzhifubao.data.SecretKeyList.ali_name.getBytes(), Base64.DEFAULT));
        RSA_PRIVATE = stt+"=";
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
                        Toast.show("开发中...");
//                        IWXAPI api = WXAPIFactory.createWXAPI(getApplicationContext(), Constants.APP_ID);
//                        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
//                        android.widget.Toast.makeText(PayDetailsActivity.this, String.valueOf(isPaySupported), android.widget.Toast.LENGTH_SHORT).show();
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
                    case 0://支付宝
                        //pay(view);
                        payV2(view);

                        break;
                    case 1://微信
                        //weChatPay();
                        break;
                }
            }
        });
    }

    /**
     * 微信支付方法
     */
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



    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        android.widget.Toast.makeText(PayDetailsActivity.this, "支付成功", android.widget.Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        android.widget.Toast.makeText(PayDetailsActivity.this, "支付失败", android.widget.Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        android.widget.Toast.makeText(PayDetailsActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), android.widget.Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        android.widget.Toast.makeText(PayDetailsActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), android.widget.Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        };
    };


    /**
     * 支付宝支付业务
     *
     * @param v
     */
    public void payV2(View v) {
        if (TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA_PRIVATE)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PayDetailsActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝账户授权业务
     *
     * @param v
     */
    public void authV2(View v) {
        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA_PRIVATE)
                || TextUtils.isEmpty(TARGET_ID)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         */
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, RSA_PRIVATE);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(PayDetailsActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    /**
     * get the sdk version. 获取SDK版本号
     *
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        android.widget.Toast.makeText(this, version, android.widget.Toast.LENGTH_SHORT).show();
    }

    /**
     * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
     *
     * @param v
     */
    public void h5Pay(View v) {
        Intent intent = new Intent(this, H5PayActivity.class);
        Bundle extras = new Bundle();
        /**
         * url是测试的网站，在app内部打开页面是基于webview打开的，demo中的webview是H5PayDemoActivity，
         * demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现，
         * 商户可以根据自己的需求来实现
         */
        String url = "http://m.taobao.com";
        // url可以是一号店或者淘宝等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
        extras.putString("url", url);
        intent.putExtras(extras);
        startActivity(intent);
    }


}
