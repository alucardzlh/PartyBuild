package com.example.a25908.partybuild.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.a25908.partybuild.R;

public class tsteActivity extends BaseActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tste);
        Intent i=getIntent();
        String strUrl=i.getStringExtra("url");
        //隐藏导航栏
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
//        播放网页视频
        webView = (WebView) findViewById(R.id.webView);
        WebSettings setting = webView.getSettings();
        setSettings(setting);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://v.youku.com/v_show/id_XMTcxNjc3NTA2MA==.html");
    }

    private void setSettings(WebSettings setting) {
        setting.setJavaScriptEnabled(true);
        setting.setBuiltInZoomControls(true);
        setting.setDisplayZoomControls(false);
        setting.setSupportZoom(true);

        setting.setDomStorageEnabled(true);
        setting.setDatabaseEnabled(true);
        // 全屏显示
        setting.setLoadWithOverviewMode(true);
        setting.setUseWideViewPort(true);
    }
}