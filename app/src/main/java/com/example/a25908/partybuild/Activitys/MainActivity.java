package com.example.a25908.partybuild.Activitys;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a25908.partybuild.Fragments.Fragment1;
import com.example.a25908.partybuild.Fragments.Fragment2;
import com.example.a25908.partybuild.Fragments.Fragment3;
import com.example.a25908.partybuild.Fragments.Fragment4;
import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.QRCode_app.CaptureActivity;
import com.example.a25908.partybuild.QRCode_decode.FinishListener;
import com.example.a25908.partybuild.QRCode_util.Util;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.List;

import static com.example.a25908.partybuild.Utils.FileUtils.deleteDir;
import static com.example.a25908.partybuild.Utils.URLconstant.Quit;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;


/**Fragment 主控制界面
 * @author yusi
 *
 */
public class MainActivity extends FragmentActivity {
    private long exitTime = 0;

    @ViewInject(R.id.address)
    public static LinearLayout address;
    @ViewInject(R.id.ewm)
    public static ImageView ewm;
    @ViewInject(R.id.title)
    public static TextView title;
    @ViewInject(R.id.fileclear)
    public static TextView adddt;
//    33
    @ViewInject(R.id.groups)
    RadioGroup rg;
    @ViewInject(R.id.content)
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragment;
    @ViewInject(R.id.rtns1)
    private RadioButton rb1;
    @ViewInject(R.id.rtns2)
    private RadioButton rb2;
    @ViewInject(R.id.rtns3)
    private RadioButton rb3;
    @ViewInject(R.id.rtns4)
    private RadioButton rb4;
    PartySharePreferences psp;



    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        ViewUtils.inject(this);
        mViewPager.setCurrentItem(0);
        initView();
        MainActivity.ewm.setVisibility(View.VISIBLE);
        MainActivity.ewm.setImageResource(R.mipmap.ewm);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //READ_EXTERNAL_STORAGE
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    0);
            return;
        }
        MainActivity.ewm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 0);
            }
        });
    }

    private void initView() {
        psp = new PartySharePreferences();
        MainActivity.address.setVisibility(View.GONE);

        Util.currentActivity = this;
//        MainActivity.mSearchEditText.setVisibility(View.GONE);


        mFragment = new ArrayList<Fragment>();
        Fragment rb1Fragment = new Fragment1();
        Fragment rb2Fragment = new Fragment2();
        Fragment rb3Fragment = new Fragment3();
        Fragment rb4Fragment = new Fragment4();
        mFragment.add(rb1Fragment);
        mFragment.add(rb2Fragment);
        mFragment.add(rb3Fragment);
        mFragment.add(rb4Fragment);
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mFragment.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mFragment.get(arg0);
            }
        };
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                int current = mViewPager.getCurrentItem();
                switch (current) {
                    case 0:
                        MainActivity.ewm.setVisibility(View.VISIBLE);
                        MainActivity.address.setVisibility(View.GONE);
//                        MainActivity.mSearchEditText.setVisibility(View.GONE);
                        MainActivity.ewm.setImageResource(R.mipmap.ewm);
                        MainActivity.title.setVisibility(View.VISIBLE);
                        MainActivity.title.setText("移动党建");
                        MainActivity.adddt.setVisibility(View.GONE);
                        MainActivity.ewm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 0);
                            }
                        });
                        rb1.setChecked(true);
                        rb1.setTextColor(getResources().getColor(R.color.yellow));
                        rb2.setTextColor(getResources().getColor(R.color.huiwhite));
                        rb3.setTextColor(getResources().getColor(R.color.huiwhite));
                        rb4.setTextColor(getResources().getColor(R.color.huiwhite));
                        break;
                    case 1:
                        MainActivity.adddt.setVisibility(View.GONE);
                        MainActivity.ewm.setVisibility(View.GONE);
                        MainActivity.title.setText("党员名册");
//                        MainActivity.mSearchEditText.setHint("模糊检索");
                        MainActivity.address.setVisibility(View.GONE);
//                        MainActivity.mSearchEditText.setVisibility(View.GONE);
                        MainActivity.title.setVisibility(View.VISIBLE);

                        rb2.setChecked(true);
                        rb1.setTextColor(getResources().getColor(R.color.huiwhite));
                        rb2.setTextColor(getResources().getColor(R.color.yellow));
                        rb3.setTextColor(getResources().getColor(R.color.huiwhite));
                        rb4.setTextColor(getResources().getColor(R.color.huiwhite));
                        break;
                    case 2:
                        MainActivity.ewm.setVisibility(View.GONE);
                        MainActivity.adddt.setText("");
                        MainActivity.adddt.setVisibility(View.VISIBLE);
                        MainActivity.adddt.setBackgroundResource(R.drawable.ic_add_black_24dp);
                        MainActivity.title.setVisibility(View.VISIBLE);
                        MainActivity.title.setText("党群动态");
                        MainActivity.address.setVisibility(View.GONE);
//                        MainActivity.mSearchEditText.setVisibility(View.GONE);
                        MainActivity.adddt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(MainActivity.this,HairDynamicActivity.class));
                            }
                        });


                        rb3.setChecked(true);
                        rb1.setTextColor(getResources().getColor(R.color.huiwhite));
                        rb2.setTextColor(getResources().getColor(R.color.huiwhite));
                        rb3.setTextColor(getResources().getColor(R.color.yellow));
                        rb4.setTextColor(getResources().getColor(R.color.huiwhite));
                        break;
                    case 3:
                        MainActivity.adddt.setVisibility(View.GONE);
                        MainActivity.address.setVisibility(View.GONE);
                        MainActivity.ewm.setVisibility(View.GONE);
//                        MainActivity.mSearchEditText.setVisibility(View.GONE);
                        MainActivity.ewm.setVisibility(View.GONE);
                        MainActivity.title.setText("个人中心");
                        MainActivity.title.setVisibility(View.VISIBLE);

                        rb4.setChecked(true);
                        rb1.setTextColor(getResources().getColor(R.color.huiwhite));
                        rb2.setTextColor(getResources().getColor(R.color.huiwhite));
                        rb3.setTextColor(getResources().getColor(R.color.huiwhite));
                        rb4.setTextColor(getResources().getColor(R.color.yellow));
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arb0, float arb1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }

    @OnClick({R.id.rtns1, R.id.rtns2, R.id.rtns3, R.id.rtns4})
    public void onclick(View v) {
        switch (v.getId()) {
            case R.id.rtns1:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.rtns2:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.rtns3:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.rtns4:
                mViewPager.setCurrentItem(3);
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                deleteDir(Environment.getExternalStorageDirectory() + "/Credit/cache");//正常退出时，清除缓存
                GsonRequest Request= new GsonRequest(URLINSER +Quit, RequestMethod.GET);
                Request.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                Request.add("KeyNo", psp.getUSERID());
                Request.add("deviceId", new Build().MODEL);
                Request.add("username",psp.getUSERNAME());
                CallServer.getInstance().add(MainActivity.this, Request, GsonCallBack.getInstance(), 0x02999, true, false, true);
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == 0)// 从二维码照相机回主页
        {
            if (resultCode == RESULT_OK) {

                Bundle bundle = intent.getExtras();
                String con = bundle.getString("SCAN_RESULT");
                String substr = con.substring(0,4);
                if (substr.equals("http")){
                    Intent intent2 = new Intent();
                    intent2.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(con.toString());
                    intent2.setData(content_url);
                    startActivity(intent2);
                }
                else {
                    // 显示扫描到的内容
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("扫描内容");
                    builder.setMessage(bundle.getString("SCAN_RESULT"));
                    builder.setPositiveButton(R.string.button_ok, null);
                    builder.setOnCancelListener(null);
                    builder.show();
                }

//                // 显示
//                imageView.setImageBitmap((Bitmap) intent
//                        .getParcelableExtra("bitmap"));
            }
            if (resultCode == 300) {
                Bundle bundle = intent.getExtras();
                String str=bundle.getString("result");
                String[] sr=str.split("\\?");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("扫描内容");
                builder.setMessage(str);
                builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
                builder.setOnCancelListener(null);
                builder.show();
            }
            if (resultCode == 200) {

//                imageView.setImageBitmap((Bitmap) intent
//                        .getParcelableExtra("QR_CODE"));
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode==0){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                MainActivity.ewm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 0);
                    }
                });
            }
            else {
                com.example.a25908.partybuild.Views.Toast.show("权限获取失败，无法扫描二维码，请到设置中开放存储权限");
            }
        }

    }

}
