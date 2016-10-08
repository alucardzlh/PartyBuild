package com.example.a25908.partybuild.Activitys;

import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a25908.partybuild.Dialogs.WaitDialog;
import com.example.a25908.partybuild.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import static com.example.a25908.partybuild.Utils.URLconstant.IMGURL;

/**
 * 组织架构
 * @author weixaun
 */
public class OrganizationalActivity extends BaseActivity {


    @ViewInject(R.id.returnT)
    private ImageView returnT;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.o_web_error)
    LinearLayout web_error;
    @ViewInject(R.id.o_fullview)
    WebView o_fullview;
    String url = IMGURL+"/zhirong.partybuild/partyOrganizationStructureController/queryPartyOrganizationArchitecture?unit_id=6&unit_name=江西智容";
//    String url = "https://www.baidu.com/"
            ;
    WaitDialog wd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizational);
        ViewUtils.inject(this);
        wd=new WaitDialog(this);
        title.setText("组织架构");
        returnT.setVisibility(View.VISIBLE);
        returnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        init();
    }
    private void init (){
        wd.show();
        WebSettings ws = o_fullview.getSettings();//网页设置
        //设置 缓存模式
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        ws.setDomStorageEnabled(true);
        ws.setJavaScriptEnabled(true);
        ws.setRenderPriority(WebSettings.RenderPriority.HIGH);//提高渲染优先级
        //ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        ws.setUseWideViewPort(true);//自适应
//        ws.setLoadWithOverviewMode(true);//自适应

        o_fullview.loadUrl(url);
        o_fullview.setWebViewClient( new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                wd.dismiss();
            }
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                o_fullview.setVisibility(View.GONE);
                web_error.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                o_fullview.setVisibility(View.GONE);
                web_error.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                o_fullview.setVisibility(View.GONE);
                web_error.setVisibility(View.VISIBLE);
            }
        });

    }
}
