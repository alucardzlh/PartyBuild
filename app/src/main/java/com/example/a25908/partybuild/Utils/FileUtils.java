package com.example.a25908.partybuild.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 0;
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 1;
    private Context context;

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
        File destDir2 = new File(Environment.getExternalStorageDirectory() + "/PartyBuild/Cache");//项目缓存
        if (!destDir1.exists()) {
            destDir1.mkdirs();
        }
        if (!destDir2.exists()) {
            destDir2.mkdirs();
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
                return  pathname.getName().matches("^.*\\.doc$") || pathname.getName().matches("^.*\\.xls$") || pathname.getName().matches("^.*\\.xlsx$") || pathname.getName().matches("^.*\\.docx$");
            }

        }
    };
    /**
     * 转换文件大小
     * */
    public static String FormentFileSize(long fileS) {
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
    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
    /**
     * 检测String是否全是中文
     *
     * @param name
     * @return
     */

    public static boolean checkNameChese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }


    /**
     *     //判断email格式是否正确
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }


    /**
     * 判断是否全是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 解决图片内存溢出问题方法
     *
     * @param path
     * @param screenWidth
     * @param screenHeight
     * @return
     */
    public static Bitmap decodeBitmap(String path, int screenWidth, int screenHeight) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true; //设置为true, 加载器不会返回图片, 而是设置Options对象中以out开头的字段.即仅仅解码边缘区域
        BitmapFactory.decodeFile(path, opts);
        // 得到图片的宽和高
        int imageWidth = opts.outWidth;
        int imageHeight = opts.outHeight;
        // 计算缩放比例
        int widthScale = imageWidth / screenWidth;
        int heightScale = imageHeight / screenHeight;
        int scale = widthScale > heightScale ? widthScale : heightScale;
        // 指定加载可以加载出图片.
        opts.inJustDecodeBounds = false;
        // 使用计算出来的比例进行缩放
        opts.inSampleSize = scale;
        Bitmap bmp = BitmapFactory.decodeFile(path, opts);
        return bmp;
    }

}
