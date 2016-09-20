package com.example.a25908.partybuild.Activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.example.a25908.partybuild.Utils.URLconstant;
import com.example.a25908.partybuild.Views.Toast;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yolanda.nohttp.RequestMethod;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.a25908.partybuild.Utils.URLconstant.IMGURL;
import static com.example.a25908.partybuild.Utils.URLconstant.PARTYDETAILS;
import static com.example.a25908.partybuild.Utils.URLconstant.PARTYDETAILSADDCOMN;
import static com.example.a25908.partybuild.Utils.URLconstant.PARTYDETAILSCOMN;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;


/**
 * 党委通知，学习园地，党员扶持的详情页
 * @author 数据 yusi
 */
public class DetailsPageActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    private ImageView returnT;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.imageView_dp)
    private ImageView imageView_dp;

    @ViewInject(R.id.ptitle)
    private TextView ptitle;
    @ViewInject(R.id.ptime)
    private TextView ptime;
    @ViewInject(R.id.pcontent)
    private TextView pcontent;

    @ViewInject(R.id.et_comn)
    private EditText et_comn;//评论内容框
    @ViewInject(R.id.et_send)
    private TextView et_send;//评论发表

    @ViewInject(R.id.et_zan)
    private LinearLayout et_zan;//点赞

    @ViewInject(R.id.et_delete)
    private LinearLayout et_delete;//删除
    private String picName = "networkPic.jpg";
    /**网络图片Getter*/
    private NetworkImageGetter mImageGetter;
    PartySharePreferences psp;
    public static  Handler handler;
    private static final String TAG = "ImgLabelActivity";
    String dtr1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);
        ViewUtils.inject(this);
        psp=PartySharePreferences.getLifeSharedPreferences();
        title.setText("详情");
        returnT.setVisibility(View.VISIBLE);
        returnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        int flag = intent.getIntExtra("flag",0);
        if (flag==1){
            imageView_dp.setVisibility(View.VISIBLE);
            imageView_dp.setImageResource(R.mipmap.banner2);
            imageView_dp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DetailsPageActivity.this, WebVedioActivity.class).putExtra("url", URLconstant.videourl));
                }
            });
        }else {
            ptitle.setText(DataManager.partyCommDetailsList.data.NewsList.title);
            ptime.setText(DataManager.partyCommDetailsList.data.NewsList.add_time);
            String str=DataManager.partyCommDetailsList.data.NewsList.content;
            dtr1=str.replaceAll("<img src=\"","<img  src=\""+IMGURL);
            String dtr2=dtr1.replaceAll("<p>","<p style=\"font-size:28px\">");
//            pcontent.setText(Html.fromHtml(dtr1));

//            WebView Description = (WebView)findViewById(R.id.pcontent);
//            //支持javascript
//            Description.getSettings().setJavaScriptEnabled(true);
//            // 设置可以支持缩放
//            Description.getSettings().setSupportZoom(true);
//            // 设置出现缩放工具
//            Description.getSettings().setBuiltInZoomControls(true);
//            //扩大比例的缩放
//            Description.getSettings().setUseWideViewPort(true);
//            //自适应屏幕
//            Description.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//            Description.getSettings().setLoadWithOverviewMode(true);
//
//
//            Description.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//            WebSettings webSettings = Description.getSettings();
//            webSettings.setTextSize(WebSettings.TextSize.NORMAL);
//
//            Description.loadDataWithBaseURL (null, "<html>"+dtr2+"</html>", "text/html", "UTF-8",null);
            mImageGetter = new NetworkImageGetter();
            pcontent.setText(Html.fromHtml(dtr1, mImageGetter, null));

            /**
             * 查询评论
             */
//            GsonRequest Request = new GsonRequest(URLINSER +PARTYDETAILSCOMN, RequestMethod.GET);
//            Request.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
//            Request.add("KeyNo", psp.getUSERID());
//            Request.add("deviceId", new Build().MODEL);
//            Request.add("type", 0);//评论内容类型 ( 0 查询党委通知、学习园地、党员扶持评论)
//            Request.add("nvid", DataManager.partyCommDetailsList.data.NewsList.newsid);
////                Request.add("PageIndex", 1);
////                Request.add("PageSize", 10);
//            CallServer.getInstance().add(DetailsPageActivity.this, Request, GsonCallBack.getInstance(), 0x007, true, false, true);

        }
        et_comn.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(DetailsPageActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
                    sendShow(et_comn.getText().toString());
                }
                return false;
            }
        });
        et_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendShow(et_comn.getText().toString());
            }
        });
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        Toast.show("评论发表成功!");
                        break;
                    case 1:
                        Toast.show("评论发表失败!");
                        break;
                }
            }
        };
    }
    public void sendShow(String et){
        if(!et.equals("")){
            GsonRequest Request = new GsonRequest(URLINSER +PARTYDETAILSADDCOMN, RequestMethod.GET);
            Request.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
            Request.add("KeyNo", psp.getUSERID());
            Request.add("deviceId", new Build().MODEL);
            Request.add("type", 0);//评论内容类型 ( 0 查询党委通知、学习园地、党员扶持评论)
            Request.add("nvid", DataManager.partyCommDetailsList.data.NewsList.newsid);
            Request.add("content", et);
            Request.add("username", psp.getUSERNAME());
            CallServer.getInstance().add(DetailsPageActivity.this, Request, GsonCallBack.getInstance(), 0x0071, true, false, true);
        }
    }

    /**
     * 网络图片
     * @author Susie
     */
    private final class NetworkImageGetter implements Html.ImageGetter{

        @Override
        public Drawable getDrawable(String source) {

            Drawable drawable = null;
            // 判断是否以http开头
            if(source.startsWith("http")) {
                // 判断路径是否存在
                    // 不存在即开启异步任务加载网络图片
                    AsyncLoadNetworkPic networkPic = new AsyncLoadNetworkPic();
                    networkPic.execute(source);
            }
            return drawable;
        }
    }
    /**
     * 加载网络图片异步类
     * @author Susie
     */
    private final class AsyncLoadNetworkPic extends AsyncTask<String, Integer, Void> {

        @Override
        protected Void doInBackground(String... params) {
            // 加载网络图片
            loadNetPic(params);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // 当执行完成后再次为其设置一次
            pcontent.setText(Html.fromHtml(dtr1, mImageGetter, null));
        }
        /**加载网络图片*/
        private void loadNetPic(String... params) {
            String path = params[0];
            File file = new File(Environment.getExternalStorageDirectory(), picName);
            InputStream in = null;
            FileOutputStream out = null;
            try {
                URL url = new URL(path);
                HttpURLConnection connUrl = (HttpURLConnection) url.openConnection();
                connUrl.setConnectTimeout(5000);
                connUrl.setRequestMethod("GET");
                if(connUrl.getResponseCode() == 200) {
                    in = connUrl.getInputStream();
                    out = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int len;
                    while((len = in.read(buffer))!= -1){
                        out.write(buffer, 0, len);
                    }
                } else {
                    Log.i(TAG, connUrl.getResponseCode() + "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
