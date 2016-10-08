package com.example.a25908.partybuild.Activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Utils.FileUtils;
import com.example.a25908.partybuild.Utils.net.download.DownloadProgressListener;
import com.example.a25908.partybuild.Utils.net.download.FileDownloader;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.example.a25908.partybuild.Utils.FileUtils.getFile;

/**
 * 下载详情
 */
public class FileContentActivity extends BaseActivity {
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 0;
    @ViewInject(R.id.returnT)
    private ImageView back;
    @ViewInject(R.id.title)
    private TextView title;

    private static final int PROCESSING = 1;
    private static final int FAILURE = -1;
    private TextView fname; //
    private TextView fsize; //
    private TextView pathText; // url地址
    private TextView resultView;
    private Button downloadButton;
    private Button stopButton;
    private ProgressBar progressBar;

    private Handler handler = new UIHandler();

    /**
     * 开线程控制进度条
     */
    private final class UIHandler extends Handler {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PROCESSING: // 更新进度
                    progressBar.setProgress(msg.getData().getInt("size"));
                    float num = (float) progressBar.getProgress()
                            / (float) progressBar.getMax();
                    int result = (int) (num * 100); // 计算进度
                    resultView.setText(result + "%");
                    if (progressBar.getProgress() == progressBar.getMax()) {
                        Toast.makeText(getApplicationContext(), "下载成功",
                                Toast.LENGTH_SHORT).show();
                        resultView.setText("下载成功!");
                        downloadButton.setText("已下载");
                        downloadButton.setEnabled(false);
                        stopButton.setEnabled(false);
                    }
                    break;
                case FAILURE: // 下载失败
                    Toast.makeText(getApplicationContext(), "下载失败",
                            Toast.LENGTH_SHORT).show();
                    downloadButton.setEnabled(true);
                    stopButton.setEnabled(false);
                    break;
            }
        }
    }

    List<String> list=new ArrayList<>();
    String name;
    String namehz, hz;
    long l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_content);
        ViewUtils.inject(this);
        title.setText("下载");
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent i=getIntent();
        name=i.getStringExtra("name");
        String path=i.getStringExtra("path");
        String size=i.getStringExtra("size");
        namehz=i.getStringExtra("namehz");
        fname = (TextView) findViewById(R.id.fname);
        fsize = (TextView) findViewById(R.id.fsize);
        fsize.setText(size);
        pathText = (TextView) findViewById(R.id.path);
        resultView = (TextView) findViewById(R.id.resultView);
        downloadButton = (Button) findViewById(R.id.downloadbutton);
        stopButton = (Button) findViewById(R.id.stopbutton);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        String [] ddr=namehz.split("\\.");
        DataManager.DucomentName=name;
        hz=ddr[ddr.length-1];
        fname.setText(name+"."+hz);
        if(!path.equals("")){
            pathText.setText(path);
        }else{
            pathText.setText("文件已损坏!");
            pathText.setVisibility(View.VISIBLE);
            downloadButton.setEnabled(false);
            stopButton.setEnabled(false);
        }


        ButtonClickListener listener = new ButtonClickListener();
        downloadButton.setOnClickListener(listener);
        stopButton.setOnClickListener(listener);
        init();
    }
    public void init(){
        File file=new File(Environment.getExternalStorageDirectory() + "/PartyBuild/Documents");
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //READ_EXTERNAL_STORAGE
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_EXTERNAL_STORAGE_REQUEST_CODE);
                return;
            }
        }
        list=getFile(file);
        List<String> list1=new ArrayList<>();//文件名
        List<String> list2=new ArrayList<>();//文件路径
        List<String> list3=new ArrayList<>();//文件大小
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
        for(int i=0;i<list1.size();i++){
            if(list1.get(i).equals((fname.getText().toString()))){
                resultView.setText("下载成功!");
                downloadButton.setText("已下载");
                downloadButton.setEnabled(false);
                stopButton.setEnabled(false);
                fsize.setText(list3.get(i));
            }
        }

    }
    private final class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.downloadbutton: // 开始下载
                    // http://abv.cn/music/光辉岁月.mp3，可以换成其他文件下载的链接
                    String path = pathText.getText().toString();
                    String filename = path.substring(path.lastIndexOf('/') + 1);
                    try {
                        // URL编码（这里是为了将中文进行URL编码）
                        filename = URLEncoder.encode(filename, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    path = path.substring(0, path.lastIndexOf("/") + 1) + filename;
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        // File savDir =
                        // Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
                        // 保存路径
                        String file = Environment.getExternalStorageDirectory()+"/PartyBuild/Documents";
                        File savDir=new File(file);
                        download(path, savDir);

                    } else {
                        Toast.makeText(getApplicationContext(),
                                "SD卡不存在", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getApplicationContext(),
                            "正在下载...", Toast.LENGTH_SHORT).show();
                    downloadButton.setEnabled(false);
                    stopButton.setEnabled(true);

                    break;
                case R.id.stopbutton: // 暂停下载
                    exit();
                    Toast.makeText(getApplicationContext(),
                            "已停止下载!", Toast.LENGTH_SHORT).show();
                    downloadButton.setEnabled(true);
                    stopButton.setEnabled(false);
                    break;
            }
        }

        /*
         * 由于用户的输入事件(点击button, 触摸屏幕....)是由主线程负责处理的，如果主线程处于工作状态，
         * 此时用户产生的输入事件如果没能在5秒内得到处理，系统就会报“应用无响应”错误。
         * 所以在主线程里不能执行一件比较耗时的工作，否则会因主线程阻塞而无法处理用户的输入事件，
         * 导致“应用无响应”错误的出现。耗时的工作应该在子线程里执行。
         */
        private DownloadTask task;

        private void exit() {
            if (task != null)
                task.exit();
        }

        private void download(String path, File savDir) {
            task = new DownloadTask(path, savDir);
            new Thread(task).start();
        }

        /**
         *
         * UI控件画面的重绘(更新)是由主线程负责处理的，如果在子线程中更新UI控件的值，更新后的值不会重绘到屏幕上
         * 一定要在主线程里更新UI控件的值，这样才能在屏幕上显示出来，不能在子线程中更新UI控件的值
         *
         */
        private final class DownloadTask implements Runnable {
            private String path;
            private File saveDir;
            private FileDownloader loader;

            public DownloadTask(String path, File saveDir) {
                this.path = path;
                this.saveDir = saveDir;
            }

            /**
             * 退出下载
             */
            public void exit() {
                if (loader != null)
                    loader.exit();
            }

            DownloadProgressListener downloadProgressListener = new DownloadProgressListener() {
                @Override
                public void onDownloadSize(int size) {
                    Message msg = new Message();
                    msg.what = PROCESSING;
                    msg.getData().putInt("size", size);
                    handler.sendMessage(msg);
                }
            };

            public void run() {
                try {
                    // 实例化一个文件下载器
                    loader = new FileDownloader(getApplicationContext(), path,
                            saveDir, 3);
                    // 设置进度条最大值
                    progressBar.setMax(loader.getFileSize());
                    loader.download(downloadProgressListener);
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.sendMessage(handler.obtainMessage(FAILURE)); // 发送一条空消息对象
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode==0){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }
            else {
                com.example.a25908.partybuild.Views.Toast.show("权限获取失败，部分功能无法使用，请到设置中开放权限");
            }

        }
    }
}
