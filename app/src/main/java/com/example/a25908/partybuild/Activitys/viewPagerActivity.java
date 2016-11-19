package com.example.a25908.partybuild.Activitys;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Utils.PartySharePreferences;

import java.util.ArrayList;
import java.util.List;


/**
 * 导航页
 * @author weixuan
 */
public class viewPagerActivity extends BaseActivity {

    //ViwePager
    private ViewPager viewPager;
    private Button btn;
    //导航页资源
    private int[] images = new int[]{
            R.mipmap.ydy1,
            R.mipmap.ydy2,
            R.mipmap.ydy3,
    };
    //圆点与圆点之间的边距
    private int left;
    //用来存放导航图片实例（保证唯一性，滑动的时候不重复创建）
    private List<ImageView> imageViews;
    //存放三个灰色圆点的线性布局
    private LinearLayout ll;
    //用来存放红色圆点和灰色圆点的相对布局
    private RelativeLayout rl;
    //红色圆点ImageView
    private ImageView red_Iv;
    PartySharePreferences psp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        initView();
        //初始化导航页面和灰色圆点
        for (int i = 0; i < images.length; i++) {
            ImageView iv = new ImageView(viewPagerActivity.this);
            iv.setImageResource(images[i]);
            imageViews.add(iv);
            //动态加载灰色圆点
            ImageView gray_Iv = new ImageView(this);
            gray_Iv.setImageResource(R.drawable.dian_sred);
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(20,
                            20);
            //从第二个开始有边距
            if (i > 0) {
                layoutParams.leftMargin = 20;   //注意单位是px
            }
            gray_Iv.setLayoutParams(layoutParams);
            ll.addView(gray_Iv);
        }
        red_Iv = new ImageView(this);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(20, 20);
        red_Iv.setImageResource(R.drawable.dian_red);
        red_Iv.setLayoutParams(layoutParams2);
        rl.addView(red_Iv);
        //任何一个组件都可以得到视图树
        red_Iv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            //视图完成绘制的时候调用
            @Override
            public void onGlobalLayout() {
                left = ll.getChildAt(1).getLeft() - ll.getChildAt(0).getLeft();
                System.out.println(left);
                //移除视图树的监听
                red_Iv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        //为ViewPager添加适配器
        viewPager.setAdapter(new MyAdapter());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //导航页被选择的时候调用
            @Override
            public void onPageSelected(int position) {
                if (position == images.length - 1) {
                    btn.setVisibility(View.VISIBLE);
                }else {
                    btn.setVisibility(View.GONE);
                }
            }
            //导航页滑动的时候调用
            //positionOffset:滑动的百分比（[0,1}）
            @Override
            public void onPageScrolled(int position, float positionOffset, int arg2) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) red_Iv.getLayoutParams();
                layoutParams.leftMargin = (int) (left * positionOffset + position * left);
                red_Iv.setLayoutParams(layoutParams);
            }
            //导航页滑动的状态改变的时候调用
            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }
    //初始化组件
    private void initView() {
        psp = new PartySharePreferences();
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        imageViews = new ArrayList<ImageView>();
        ll = (LinearLayout) findViewById(R.id.ll);
        rl = (RelativeLayout) findViewById(R.id.rl);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(psp.getLoginStatus()==true){
//                    startActivity(new Intent(viewPagerActivity.this,MainActivity.class));
//                }else{
                    psp.putAppStatus(true);
                    finish();
                    startActivity(new Intent(viewPagerActivity.this,LoginActivity.class));

//                }
            }
        });
    }
    //PagerAdapter有四个方法
    class MyAdapter extends PagerAdapter {
        //返回导航页的个数
        @Override
        public int getCount() {
            return images.length;
        }
        //判断是否由对象生成
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        //加载页面
        //ViewGroup:父控件指ViewPager
        //position:当前子控件在父控件中的位置
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = imageViews.get(position);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            container.addView(iv);
            return iv;
        }
        //移除页面
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}