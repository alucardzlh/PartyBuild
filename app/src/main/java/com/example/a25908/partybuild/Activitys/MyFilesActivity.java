package com.example.a25908.partybuild.Activitys;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a25908.partybuild.Adapters.MysFilesListAdapter;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Utils.FileUtils;
import com.example.a25908.partybuild.Views.Toast;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.a25908.partybuild.Utils.FileUtils.deleteDir;
import static com.example.a25908.partybuild.Utils.FileUtils.getFile;
import static com.example.a25908.partybuild.Utils.IntentFile.getChmFileIntent;
import static com.example.a25908.partybuild.Utils.IntentFile.getExcelFileIntent;
import static com.example.a25908.partybuild.Utils.IntentFile.getPptFileIntent;
import static com.example.a25908.partybuild.Utils.IntentFile.getTextFileIntent;
import static com.example.a25908.partybuild.Utils.IntentFile.getWordFileIntent;

/**
 * 我的文档
 * @author yusi
 *
 */
public class MyFilesActivity extends BaseActivity {
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 0;
    @ViewInject(R.id.returnT)
    private ImageView back;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.fileclear)
    private TextView fileclear;//清空文档

    @ViewInject(R.id.myfiles_list)
    private ListView files_list;

    MysFilesListAdapter adapter;
    List<String> list=new ArrayList<>();
    List<String> list1;
    List<String> list2;
    List<String> list3;
    long l;
    AlertDialog.Builder builder;
    public static AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_files);
        ViewUtils.inject(this);
        init();
        title.setText("我的文档");
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        fileclear.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fileclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //READ_EXTERNAL_STORAGE
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_EXTERNAL_STORAGE_REQUEST_CODE);
                return;
            }
        }
        File file=new File(Environment.getExternalStorageDirectory() + "/PartyBuild/Documents");
        list=getFile(file);
        list1=new ArrayList<>();//文件名
        list2=new ArrayList<>();//文件路径
        list3=new ArrayList<>();//文件大小
        for(int i=0;i<list.size();i++){
            String[] str=list.get(i).split("&");
            list1.add(str[0]);
            list2.add(str[1]);
        }
        for(int j=0;j<list2.size();j++){
            File files=new File(list2.get(j));
            FileUtils g = new FileUtils();
            try {
                l = g.getFileSizes(files);
            } catch (Exception e) {
                e.printStackTrace();
            }
            list3.add(g.FormentFileSize(l));
            System.out.println( "文件的大小为：" + g.FormentFileSize(l));
        }
        adapter=new MysFilesListAdapter(MyFilesActivity.this,list1,list3);
        files_list.setAdapter(adapter);
        files_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] str=list1.get(position).split("\\.");
                Intent i;
                switch (str[1]){
                    case "txt":
                        i=getTextFileIntent(list2.get(position),false);
                        startActivity(i);
                        break;
                    case "chm":
                        i=getChmFileIntent(list2.get(position));
                        startActivity(i);
                        break;
                    case "doc":
                        i=getWordFileIntent(list2.get(position));
                        startActivity(i);
                        break;
                    case "ppt":
                        i=getPptFileIntent(list2.get(position));
                        startActivity(i);
                        break;
                    case "pptx":
                        i=getPptFileIntent(list2.get(position));
                        startActivity(i);
                        break;
                    case "xls":
                        i=getExcelFileIntent(list2.get(position));
                        startActivity(i);
                        break;
                    case "xlsx":
                        i=getExcelFileIntent(list2.get(position));
                        startActivity(i);
                        break;
                    case "docx":
                        i=getExcelFileIntent(list2.get(position));
                        startActivity(i);
                        break;
                    case "pdf":
                        i=getExcelFileIntent(list2.get(position));
                        startActivity(i);
                        break;
                    default:
                        Toast.show("此文件无法打开...");
                        break;
                }
            }
        });
    }
    public  void  init(){
        builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("是否删除已下载得所有文档");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteDir(Environment.getExternalStorageDirectory() + "/PartyBuild/Documents");
                MyFilesActivity.this.finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.e("asas",requestCode+"");//拒绝为0
        if (requestCode==0){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                File file=new File(Environment.getExternalStorageDirectory() + "/PartyBuild/Documents");
                list=getFile(file);
                list1=new ArrayList<>();//文件名
                list2=new ArrayList<>();//文件路径
                list3=new ArrayList<>();//文件大小
                for(int i=0;i<list.size();i++){
                    String[] str=list.get(i).split("&");
                    list1.add(str[0]);
                    list2.add(str[1]);
                }
                for(int j=0;j<list2.size();j++){
                    File files=new File(list2.get(j));
                    FileUtils g = new FileUtils();
                    try {
                        l = g.getFileSizes(files);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    list3.add(g.FormentFileSize(l));
                    System.out.println( "文件的大小为：" + g.FormentFileSize(l));
                }
                adapter=new MysFilesListAdapter(MyFilesActivity.this,list1,list3);
                files_list.setAdapter(adapter);
                files_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String[] str=list1.get(position).split("\\.");
                        Intent i;
                        switch (str[1]){
                            case "txt":
                                i=getTextFileIntent(list2.get(position),false);
                                startActivity(i);
                                break;
                            case "chm":
                                i=getChmFileIntent(list2.get(position));
                                startActivity(i);
                                break;
                            case "doc":
                                i=getWordFileIntent(list2.get(position));
                                startActivity(i);
                                break;
                            case "ppt":
                                i=getPptFileIntent(list2.get(position));
                                startActivity(i);
                                break;
                            case "xls":
                                i=getExcelFileIntent(list2.get(position));
                                startActivity(i);
                                break;
                            default:
                                Toast.show("此文件无法打开...");
                                break;
                        }
                    }
                });
            }
            else {
                Toast.show("权限获取失败，部分功能无法使用，请到设置中开放权限");
            }

        }
    }
}
