package com.example.a25908.partybuild.Activitys;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.a25908.partybuild.Contacts.SearchEditText;
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

/**
 * @author yusi
 * Fragment 主控制界面
 */
public class MainActivity extends FragmentActivity {
    @ViewInject(R.id.address)
    public static LinearLayout address;
    @ViewInject(R.id.ewm)
    public static ImageView ewm;
    @ViewInject(R.id.title)
    public static TextView title;
    @ViewInject(R.id.txt_filter_edit)
    public static SearchEditText mSearchEditText;
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
        MainActivity.mSearchEditText.setVisibility(View.GONE);

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
                        MainActivity.address.setVisibility(View.VISIBLE);
                        MainActivity.mSearchEditText.setVisibility(View.GONE);
                        MainActivity.title.setVisibility(View.VISIBLE);
                        MainActivity.title.setText("移动党建");
                        rb1.setChecked(true);
                        rb1.setTextColor(getResources().getColor(R.color.yellow));
                        rb2.setTextColor(getResources().getColor(R.color.huiwhite));
                        rb3.setTextColor(getResources().getColor(R.color.huiwhite));
                        rb4.setTextColor(getResources().getColor(R.color.huiwhite));
                        break;
                    case 1:
                        MainActivity.mSearchEditText.setHint("模糊检索");
                        MainActivity.address.setVisibility(View.GONE);
                        MainActivity.mSearchEditText.setVisibility(View.VISIBLE);
                        MainActivity.title.setVisibility(View.GONE);

                        rb2.setChecked(true);
                        rb1.setTextColor(getResources().getColor(R.color.huiwhite));
                        rb2.setTextColor(getResources().getColor(R.color.yellow));
                        rb3.setTextColor(getResources().getColor(R.color.huiwhite));
                        rb4.setTextColor(getResources().getColor(R.color.huiwhite));
                        break;
                    case 2:
                        MainActivity.title.setVisibility(View.VISIBLE);
                        MainActivity.title.setText("党群动态");
                        MainActivity.address.setVisibility(View.GONE);
                        MainActivity.mSearchEditText.setVisibility(View.GONE);


                        rb3.setChecked(true);
                        rb1.setTextColor(getResources().getColor(R.color.huiwhite));
                        rb2.setTextColor(getResources().getColor(R.color.huiwhite));
                        rb3.setTextColor(getResources().getColor(R.color.yellow));
                        rb4.setTextColor(getResources().getColor(R.color.huiwhite));
                        break;
                    case 3:
                        MainActivity.address.setVisibility(View.GONE);
                        MainActivity.ewm.setVisibility(View.GONE);
                        MainActivity.mSearchEditText.setVisibility(View.GONE);
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
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
//    }
}
