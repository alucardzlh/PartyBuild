package com.example.a25908.partybuild.Utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    /**
     * 获取视频文件截图
     *
     * @param path 视频文件的路径
     * @return Bitmap 返回获取的Bitmap
     */
    public static Bitmap getVideoThumb(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        return media.getFrameAtTime();
    }

    /**
     * 获取视频文件缩略图 API>=8(2.2)
     *
     * @param path 视频文件的路径
     * @param kind 缩略图的分辨率：MINI_KIND、MICRO_KIND、FULL_SCREEN_KIND
     * @return Bitmap 返回获取的Bitmap
     */
    public static Bitmap getVideoThumb2(String path, int kind) {
        return ThumbnailUtils.createVideoThumbnail(path, kind);
    }

    public static Bitmap getVideoThumb2(String path) {
        return getVideoThumb2(path, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
    }
//    以上是获取视频文件的截图和缩略图的方法，你可能还需要把Bitmap保存成文件：

    /**
     * Bitmap保存成File
     *
     * @param bitmap input bitmap
     * @param name   output file's name
     * @return String output file's path
     */
    public static String bitmap2File(Bitmap bitmap, String name) {
        File f = new File(Environment.getExternalStorageDirectory() + name + ".jpg");
        if (f.exists()) f.delete();
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            return null;
        }
        return f.getAbsolutePath();
    }

    /**
     * //创建文件夹
     */
    public static void FileNewsExists() {
        File destDir1 = new File(Environment.getExternalStorageDirectory() + "/PartyBuild");//项目文件夹
        File destDir3 = new File(Environment.getExternalStorageDirectory() + "/PartyBuild/Documents");//项目文档文件夹
        if (!destDir1.exists()) {
            destDir1.mkdirs();
        }
        if (!destDir3.exists()) {
            destDir3.mkdirs();
        }
    }

    /**
     * 递归文件夹 查找文件
     * @param file
     * @return
     */
    public static List<String> getFile(File file){
        if (!file.exists()) {
            file.mkdirs();
        }
        List<String> list=new ArrayList<>();
        File[] files=file.listFiles(filter);
        for(File f:files){
            if(f.isDirectory()){
                getFile(f);
                System.out.println();
            }else{
                list.add(f.getName()+"&"+f.getPath());
                System.out.println("文件名："+f.getName()+">>>路径："+f.getPath());
            }
        }
        return list;
    }

    /**
     * 递归文件类型
     */
    static FileFilter filter=new FileFilter() {

        @Override
        public boolean accept(File pathname) {
            // TODO Auto-generated method stub
            if(pathname.isDirectory()){
                return true;
            }else{
                return pathname.getName().matches("^.*\\.txt$") || pathname.getName().matches("^.*\\.dox$");
            }

        }
    };
    /**
     * 转换文件大小
     * */
    public String FormentFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    public long getFileSizes(File f) throws Exception {
        long s = 0;
        if (f.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(f);
            s = fis.available();
            fis.close();
        } else {
            f.createNewFile();
            System.out.println("文件夹不存在");
        }
        return s;
    }

    /**
     * 删除文件夹和文件夹里面的文件
     *
     * @param path
     */
    public static void deleteDir(String path) {//传文件夹路径
        File dir = new File(path);
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDir(path); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    /**
     *   // 将Bitmap转换成字符串
     * @param bitmap
     * @return
     */
    public static String bitmaptoString(Bitmap bitmap) {
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    /**
     * // 将字符串转换成Bitmap类型
     * @param string
     * @return
     */
    public static Bitmap stringtoBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
