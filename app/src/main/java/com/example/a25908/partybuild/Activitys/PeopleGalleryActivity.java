package com.example.a25908.partybuild.Activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a25908.partybuild.Adapters.PeopleGalleryAdapter;
import com.example.a25908.partybuild.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yusi
 *人物长廊
 */
public class PeopleGalleryActivity extends Activity {
    @ViewInject(R.id.returnT)
    private ImageView back;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.PeopleGallery_list)
    private ListView PeopleGallery_list;

    PeopleGalleryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_gallery);
        ViewUtils.inject(this);
        title.setText("人物长廊");
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        List<String> list=new ArrayList<>();
        for(int i=0;i<20;i++){
            list.add(getResources().getString(R.string.op_conhint));
        }
        adapter=new PeopleGalleryAdapter(PeopleGalleryActivity.this,list);
        PeopleGallery_list.setAdapter(adapter);
    }
}
