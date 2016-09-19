package com.example.a25908.partybuild.Activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Utils.URLconstant;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


/**
 * 党委通知，学习园地，党员扶持的详情页
 */
public class DetailsPageActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    private ImageView returnT;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.imageView_dp)
    private ImageView imageView_dp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);
        ViewUtils.inject(this);
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
            imageView_dp.setImageResource(R.mipmap.banner2);
            imageView_dp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DetailsPageActivity.this, WebVedioActivity.class).putExtra("url", URLconstant.videourl));
                }
            });
        }
        else {
            imageView_dp.setImageResource(R.mipmap.banner);
        }
    }
}
