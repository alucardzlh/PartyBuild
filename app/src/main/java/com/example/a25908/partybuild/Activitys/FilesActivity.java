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


    public static List<DataManager.MyFilesModels> MyFilesList = new ArrayList<>();

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

        DataManager.MyFilesModels mf1 = new DataManager.MyFilesModels();
        mf1.FilesName = "《中国共产党党建党规1.0》.dox";
        mf1.FilesSize ="5.26 mb";
        mf1.FilesPath = URLconstant.url1;
        MyFilesList.add(mf1);
        DataManager.MyFilesModels mf2 = new DataManager.MyFilesModels();
        mf2.FilesName = "《中国共产党党建党规2.0》.dox";
        mf2.FilesSize ="5.26 mb";
        mf2.FilesPath = URLconstant.url2;
        MyFilesList.add(mf2);
        adapter = new FilesListAdapter(FilesActivity.this, MyFilesList);
        files_list.setAdapter(adapter);
        files_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(FilesActivity.this,FileContentActivity.class).putExtra("name",MyFilesList.get(position).FilesName).putExtra("path",MyFilesList.get(position).FilesPath));
            }
        });

//        files_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                DownloadQueue downloadQueue=NoHttp.newDownloadQueue();
//                //下载文件
//               DownloadRequest downloadRequest=NoHttp.createDownloadRequest(Path,null,null,true,false);
//// what 区分下载
//// downloadRequest 下载请求对象
//// downloadListener 下载监听
//                downloadQueue.add(0, downloadRequest, downloadListener);
////                暂停或者停止下载
////                downloadRequest.cancel();
//            }
//        });
    }

//    private DownloadListener downloadListener = new DownloadListener() {
//        //     // 下载出错
//        @Override
//        public void onDownloadError(int what, Exception exception) {
//
//        }
//
//        //     开始下载
//        @Override
//        public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {
//
//        }
//
//        //     // 下载进度变化
//        @Override
//        public void onProgress(int what, int progress, long fileCount) {
//
//        }
//
//        //      // 下载完成
//        @Override
//        public void onFinish(int what, String filePath) {
//            Toast.show("下载成功！");
//        }
//
//        //     // 下载暂停或者取消
//        @Override
//        public void onCancel(int what) {
//
//        }
//    };
}
