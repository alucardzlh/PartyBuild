package com.example.a25908.partybuild.Activitys;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Utils.URLconstant;
import com.example.a25908.partybuild.Views.CommonVideoView;

public class WebVedioActivity extends AppCompatActivity {
    CommonVideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_vedio);
        videoView = (CommonVideoView) findViewById(R.id.common_videoView);
//        videoView.start("https://sec.ch9.ms/ch9/82d9/de312353-af35-48b7-a20a-e648489f82d9/Win10UpdateForDevs_high.mp4");
        videoView.start(URLconstant.videourl);
    }

    @Override public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            videoView.setFullScreen();
        }else {
            videoView.setNormalScreen();
        }
    }
}
