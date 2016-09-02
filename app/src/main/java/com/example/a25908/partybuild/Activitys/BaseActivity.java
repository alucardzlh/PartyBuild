package com.example.a25908.partybuild.Activitys;

import android.app.Activity;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;

import com.example.a25908.partybuild.R;

/**
 * Created by 25908 on 2016/8/29.
 */
/**
 * @author lilonghai
 * acticity 继承类
 */
public class BaseActivity extends Activity {
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            overridePendingTransition(R.anim.finish_tran_one, R.anim.finish_tran_two);
        }
        return super.onKeyDown(keyCode, event);
    }
}
