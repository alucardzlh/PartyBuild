package com.example.a25908.partybuild.Activitys;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a25908.partybuild.Adapters.commListAdapter;
import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.example.a25908.partybuild.Views.CommonVideoView;
import com.example.a25908.partybuild.Views.ListenedScrollView;
import com.example.a25908.partybuild.Views.MyListView;
import com.example.a25908.partybuild.Views.Toast;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yolanda.nohttp.RequestMethod;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;

import static com.example.a25908.partybuild.R.id.fab;
import static com.example.a25908.partybuild.Utils.URLconstant.IMGURL;
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
    @ViewInject(R.id.headzz)
    private RelativeLayout headzz;

    @ViewInject(R.id.ptitle)
    private TextView ptitle;
    @ViewInject(R.id.ptime)
    private TextView ptime;
    @ViewInject(R.id.pcontent)
    private WebView pcontent;
    @ViewInject(R.id.dp_slv)
    private ListenedScrollView dp_slv;
    @ViewInject(R.id.common_videoView)
    private CommonVideoView videoView;
    @ViewInject(R.id.dp_time)
    private LinearLayout dp_time;


    @ViewInject(R.id.mylist_comm)
    private MyListView mylist_comm;//评论列表
    PartySharePreferences psp;
    public static  Handler handler;
    String dtr1;
    EditText et;
    Intent intent;
    FloatingActionButton fab1;
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);
        ViewUtils.inject(this);
        struct();
        fab1 = (FloatingActionButton) findViewById(fab);
        psp=PartySharePreferences.getLifeSharedPreferences();
        title.setText("详情");
        returnT.setVisibility(View.VISIBLE);
        returnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intent.getIntExtra("fl",0)==110){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else {
                    finish();
                }


            }
        });
        intent = getIntent();
        final int flag = intent.getIntExtra("flag",0);
        if (flag==1){
            fab1.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
            ptitle.setText(DataManager.mypartyvideo.data.videoPage.title);
            ptime.setText(DataManager.mypartyvideo.data.videoPage.add_time);
//            imageView_dp.setVisibility(View.VISIBLE);
            String str=DataManager.mypartyvideo.data.videoPage.content;
//            dtr1=str.replaceAll("<img src=\"","<img  src=\""+IMGURL);
            if(str.indexOf("http") == -1){
                dtr1=str.replaceAll("src=\"","src=\""+IMGURL);
            }else{
                dtr1=str;
            }
//            try {
//                BASE64Decoder decode = new BASE64Decoder();
//                byte[] b = decode.decodeBuffer(DataManager.partyvideoList.data.videolistPage.get(intent.getIntExtra("i",0)).img);
//                //把字节数组的图片写到另一个地方
//                File apple = new File(Environment.getExternalStorageDirectory() + "/PartyBuild/Cache/CarouselImg.jpg");
//                FileOutputStream fos = new FileOutputStream(apple);
//                fos.write(b);
//                fos.flush();
//                fos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            imageView_dp.setImageBitmap(FileUtils.decodeBitmap(Environment.getExternalStorageDirectory() + "/PartyBuild/Cache/CarouselImg.jpg", 500, 500));
//            imageView_dp.setImageBitmap(FileUtils.stringtoBitmap(DataManager.partyvideoList.data.videolistPage.get(intent.getIntExtra("i",0)).img));;
//            videoView.start(URLconstant.IMGURL+DataManager.mypartyvideo.data.videoPage.path);
            videoView.screenSwitchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int  i = getResources().getConfiguration().orientation;
                    if (i== Configuration.ORIENTATION_PORTRAIT){
                        DetailsPageActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    }else if (i==Configuration.ORIENTATION_LANDSCAPE){
                        DetailsPageActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    }
                }
            });

            WebSettings webSettings = pcontent.getSettings();
            String cacheDirPath = Environment.getExternalStorageDirectory() + "/PartyBuild/Cache"; //缓存路径
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //缓存模式
            webSettings.setAppCachePath(cacheDirPath); //设置缓存路径
            webSettings.setAppCacheEnabled(true); //开启缓存功能

            pcontent.loadDataWithBaseURL(null,getNewContent(dtr1), "text/html", "utf-8", null);
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

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showinit();
            }
        });
        dp_slv.setOnScrollListener(new ListenedScrollView.OnScrollListener() {
            @Override
            public void onBottomArrived() {
                //滑倒底部了
            }

            @Override
            public void onScrollStateChanged(ListenedScrollView view, int scrollState) {
                //滑动状态改变
                if (scrollState==0){
                    if (flag==1){
                        fab1.setVisibility(View.GONE);
                    }
                    else {
                        fab1.setVisibility(View.VISIBLE);
                    }

                }else if(scrollState==1) {
                    if (flag==1){
                        fab1.setVisibility(View.GONE);
                    }
                    else {
                        fab1.setVisibility(View.GONE);
                    }
                }
                else {
                    if (flag==1){
                        fab1.setVisibility(View.GONE);
                    }
                    else {
                        fab1.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                //滑动位置改变
            }
        });

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

    /**
     * 评论dialog
     */
    public void showinit() {
        et = new EditText(DetailsPageActivity.this);
//        et.setPadding(20,50,0,0);
        new AlertDialog.Builder(DetailsPageActivity.this)
                .setTitle("评论")
                .setView(et)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!TextUtils.isEmpty(et.getText().toString())){
                            GsonRequest Request = new GsonRequest(URLINSER +PARTYDETAILSADDCOMN, RequestMethod.GET);
                            Request.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                            Request.add("KeyNo", psp.getUSERID());
                            Request.add("deviceId", new Build().MODEL);
                            Request.add("type", 0);//评论内容类型 ( 0 查询党委通知、学习园地、党员扶持评论)
                            Request.add("nvid", DataManager.partyCommDetailsList.data.NewsList.newsid);
                            Request.add("content", et.getText().toString());
                            Request.add("username", psp.getUSERNAME());
                            CallServer.getInstance().add(DetailsPageActivity.this, Request, GsonCallBack.getInstance(), 0x0071, true, false, true);
                        }else{
                            Toast.show("评论内容不能为空!!!");
                        }

                    }
                })
                .setNegativeButton("取消", null)
                .setCancelable(false)
                .show();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            headzz.setVisibility(View.GONE);
            ptitle.setVisibility(View.GONE);
            dp_time.setVisibility(View.GONE);
            pcontent.setVisibility(View.GONE);
            videoView.setFullScreen();
        }else {
            headzz.setVisibility(View.VISIBLE);
            ptitle.setVisibility(View.VISIBLE);
            dp_time.setVisibility(View.VISIBLE);
            pcontent.setVisibility(View.VISIBLE);
            videoView.setNormalScreen();
        }
    }


}
