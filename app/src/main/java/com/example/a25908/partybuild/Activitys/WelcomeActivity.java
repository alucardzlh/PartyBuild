package com.example.a25908.partybuild.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Utils.PartySharePreferences;

public class WelcomeActivity extends BaseActivity {
    PartySharePreferences psp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        PartySharePreferences.init(this);
        psp=PartySharePreferences.getLifeSharedPreferences();
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);
                    finish();
//                    if(psp.getLoginStatus()==true){
//                        startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
//                    }else{
                    startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
//                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}
