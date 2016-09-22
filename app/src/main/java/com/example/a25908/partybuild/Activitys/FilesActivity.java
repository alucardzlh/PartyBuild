package com.example.a25908.partybuild.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a25908.partybuild.Adapters.FilesListAdapter;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Utils.FileUtils;
import com.example.a25908.partybuild.Utils.URLconstant;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yusi
 *         文档中心
 */
public class FilesActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    private ImageView back;
    @ViewInject(R.id.title)
    private TextView title;

    @ViewInject(R.id.files_list)
    private ListView files_list;
    FilesListAdapter adapter;

//    ---------------

    private static final String Path = "http://210.30.12.1:8080/mp3/DJ.mp3";

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

        adapter = new FilesListAdapter(FilesActivity.this,  DataManager.DucomentRoomList.data.commentList);
        files_list.setAdapter(adapter);
        files_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(FilesActivity.this,FileContentActivity.class).putExtra("name",DataManager.DucomentRoomList.data.commentList.get(position).NAME).putExtra("path",DataManager.DucomentRoomList.data.commentList.get(position).PATH).putExtra("size", FileUtils.FormentFileSize(DataManager.DucomentRoomList.data.commentList.get(position).SIZE)));
            }
        });
    }
}
