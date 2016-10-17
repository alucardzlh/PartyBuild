package com.example.a25908.partybuild.Activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a25908.partybuild.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
/**
 * 关于我们
 * @author yusi
 *
 */
public class AboutsActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    private ImageView back;
    @ViewInject(R.id.title)
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abouts);
        ViewUtils.inject(this);
        title.setText("关于我们");
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
