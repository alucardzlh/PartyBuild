package com.example.a25908.partybuild.Activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
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
import com.example.a25908.partybuild.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

import static com.example.a25908.partybuild.Utils.FileUtils.deleteDir;


/**
 * @author yusi
 * Fragment 主控制界面
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



    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        ViewUtils.inject(this);
        mViewPager.setCurrentItem(0);
        initView();
    }

    private void initView() {
        MainActivity.address.setVisibility(View.VISIBLE);
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
                        MainActivity.ewm.setVisibility(View.GONE);
                        MainActivity.address.setVisibility(View.VISIBLE);
//                        MainActivity.mSearchEditText.setVisibility(View.GONE);
                        MainActivity.title.setVisibility(View.VISIBLE);
                        MainActivity.title.setText("移动党建");
                        MainActivity.adddt.setVisibility(View.GONE);
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
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
