package com.example.a25908.partybuild.Activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a25908.partybuild.Adapters.FilesListAdapter;
import com.example.a25908.partybuild.Adapters.TheConstitutionOfThePartyAdapter;
import com.example.a25908.partybuild.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yusi
 * 文档中心
 */
public class FilesActivity extends Activity {
    @ViewInject(R.id.returnT)
    private ImageView back;
    @ViewInject(R.id.title)
    private TextView title;

    @ViewInject(R.id.files_list)
    private ListView files_list;

    FilesListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);
        ViewUtils.inject(this);
        title.setText("文档中心");
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        List<String> list1=new ArrayList<>();
        for(int i=0;i<20;i++){
            list1.add("《中国共产党党建党规》.dox");
        }
        List<String> list2=new ArrayList<>();
        for(int i=0;i<20;i++){
            list2.add("24KB");
        }
        adapter=new FilesListAdapter(FilesActivity.this,list1,list2);
        files_list.setAdapter(adapter);
    }
}
