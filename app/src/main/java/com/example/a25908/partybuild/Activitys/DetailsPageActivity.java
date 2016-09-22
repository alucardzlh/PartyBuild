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
import android.os.StrictMode;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
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

import com.example.a25908.partybuild.Adapters.commListAdapter;
import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.example.a25908.partybuild.Utils.URLconstant;
import com.example.a25908.partybuild.Views.MyListView;
import com.example.a25908.partybuild.Views.Toast;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yolanda.nohttp.RequestMethod;

import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;//这个特别容易导错
import org.jsoup.select.Elements;

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
    private WebView pcontent;

    @ViewInject(R.id.et_comn)
    private EditText et_comn;//评论内容框
    @ViewInject(R.id.et_send)
    private TextView et_send;//评论发表
    @ViewInject(R.id.mylist_comm)
    private MyListView mylist_comm;//评论列表

    @ViewInject(R.id.et_delete)
    private LinearLayout et_delete;//删除
    PartySharePreferences psp;
    public static  Handler handler;
    String dtr1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);
        ViewUtils.inject(this);
        struct();
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
//            dtr1=str.replaceAll("<img src=\"","<img  src=\""+IMGURL);
            if(str.indexOf("http") == -1){
                dtr1=str.replaceAll("src=\"","src=\""+IMGURL);
            }else{
                dtr1=str;
            }

//            pcontent.setMovementMethod(ScrollingMovementMethod.getInstance());// 设置可滚动
//            pcontent.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页
//            String html="<p><strong>强调</strong></p><p><em>斜体</em></p>"
//                    +"<p><a href=\"http://www.dreamdu.com/xhtml/\">超链接HTML入门</a>学习HTML!</p><p><font color=\"#aabb00\">颜色1"
//                    +"</p><p><font color=\"#00bbaa\">颜色2</p><h1>标题1</h1><h3>标题2</h3><h6>标题3</h6><p>大于>小于<</p><p>" +
//                    "下面是网络图片</p><img src=\"http://avatar.csdn.net/0/3/8/2_zhang957411207.jpg\"/>";
////            pcontent.setText(Html.fromHtml("<html><body>"+str33+"</body></html>"));
//
//            pcontent.setText(Html.fromHtml(dtr1, imgGetter1, null));
//            pcontent.loadData(dtr1, "text/html; charset=UTF-8",null);
            WebSettings webSettings = pcontent.getSettings();
            String cacheDirPath = Environment.getExternalStorageDirectory() + "/PartyBuild/Cache"; //缓存路径
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //缓存模式
            webSettings.setAppCachePath(cacheDirPath); //设置缓存路径
            webSettings.setAppCacheEnabled(true); //开启缓存功能

            pcontent.loadDataWithBaseURL(null,getNewContent(dtr1), "text/html", "utf-8", null);
            /**
             * 查询评论
             */
            GsonRequest Request = new GsonRequest(URLINSER +PARTYDETAILSCOMN, RequestMethod.GET);
            Request.add("token", MD5.MD5s(DataManager.partyCommDetailsList.data.NewsList.newsid + new Build().MODEL));
            Request.add("KeyNo", DataManager.partyCommDetailsList.data.NewsList.newsid);
            Request.add("deviceId", new Build().MODEL);
            Request.add("type", 0);//评论内容类型 ( 0 查询党委通知、学习园地、党员扶持评论)
//                Request.add("PageIndex", 1);
//                Request.add("PageSize", 10);
            CallServer.getInstance().add(DetailsPageActivity.this, Request, GsonCallBack.getInstance(), 0x007, true, false, true);

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
                        GsonRequest Request = new GsonRequest(URLINSER +PARTYDETAILSCOMN, RequestMethod.GET);
                        Request.add("token", MD5.MD5s(DataManager.partyCommDetailsList.data.NewsList.newsid + new Build().MODEL));
                        Request.add("KeyNo", DataManager.partyCommDetailsList.data.NewsList.newsid);
                        Request.add("deviceId", new Build().MODEL);
                        Request.add("type", 0);//评论内容类型 ( 0 查询党委通知、学习园地、党员扶持评论)
//                Request.add("PageIndex", 1);
//                Request.add("PageSize", 10);
                        CallServer.getInstance().add(DetailsPageActivity.this, Request, GsonCallBack.getInstance(), 0x007, true, false, true);

                        Toast.show("评论发表成功!");
                        break;
                    case 1:
                        Toast.show("评论发表失败!");
                        break;
                    case 2://查询评论
                        commListAdapter adapter=new commListAdapter(DetailsPageActivity.this,DataManager.CommentMarList.data.commentList);
                        mylist_comm.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
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

    Html.ImageGetter imgGetter1 = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            Drawable drawable = null;
            URL url;
            try {
                url = new URL(source);
                drawable = Drawable.createFromStream(url.openStream(), "");  //获取网路图片
            } catch (Exception e) {
                return null;
            }
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
                    .getIntrinsicHeight());
            return drawable;
        }
    };
    /**
     * 这里面的resource就是fromhtml函数的第一个参数里面的含有的url
     */
    Html.ImageGetter imgGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            Log.i("RG", "source---?>>>" + source);
            Drawable drawable = null;
            URL url;
            try {
                url = new URL(source);
                Log.i("RG", "url---?>>>" + url);
                drawable = Drawable.createFromStream(url.openStream(), ""); // 获取网路图片
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            Log.i("RG", "url---?>>>" + url);
            return drawable;
        }
    };

    public static void struct() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork() // or
                // .detectAll()
                // for
                // all
                // detectable
                // problems
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects() // 探测SQLite数据库操作
                .penaltyLog() // 打印logcat
                .penaltyDeath().build());
    }

    private String getNewContent(String htmltext){

        Document doc=Jsoup.parse(htmltext);
        Elements elements=doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width","100%").attr("height","auto");
        }

        Log.d("VACK", doc.toString());
        return doc.toString();
    }

}
