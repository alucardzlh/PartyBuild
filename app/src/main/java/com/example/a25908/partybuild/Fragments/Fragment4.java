package com.example.a25908.partybuild.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a25908.partybuild.Activitys.AboutsActivity;
import com.example.a25908.partybuild.Activitys.LoginActivity;
import com.example.a25908.partybuild.Activitys.MyFilesActivity;
import com.example.a25908.partybuild.Activitys.MyPartyPayActivity;
import com.example.a25908.partybuild.Activitys.MydataActivity;
import com.example.a25908.partybuild.Activitys.OpinionActivity;
import com.example.a25908.partybuild.Activitys.TestWebActivity;
import com.example.a25908.partybuild.Activitys.tsteActivity;
import com.example.a25908.partybuild.Dialogs.WaitDialog;
import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.FileUtils;
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.example.a25908.partybuild.Views.RoundImageView;
import com.yolanda.nohttp.RequestMethod;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import static com.example.a25908.partybuild.Utils.URLconstant.PARTYPAY;
import static com.example.a25908.partybuild.Utils.URLconstant.UPDATEUSERDATEURL;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;
import static com.example.a25908.partybuild.Utils.URLconstant.USERDATEDWBMURL;
import static com.example.a25908.partybuild.Utils.URLconstant.USERDATEURL;

/**
 * @author yusi
 * 个人中心
 */
public class Fragment4 extends Fragment {
    private static final String IMAGE_FILE_NAME = "avatarImage.jpg";// 图片文件名称
    private static final int REQUESTCODE_PICK = 0;        // 相册选图标记
    private static final int REQUESTCODE_TAKE = 1;        // 相机拍照标记
    private static final int REQUESTCODE_CUTTING = 2;    // 图片裁切标记
    View v;
    PartySharePreferences psp;
    public static Handler handler;
    AlertDialog.Builder builder;
    RoundImageView userimg;
    WaitDialog wd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_fragment4, container, false);
        psp = PartySharePreferences.getLifeSharedPreferences();
        wd=new WaitDialog(getActivity());
        show();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0://我的资料-中转
                        wd.show();
                        GsonRequest LoginRequest = new GsonRequest(URLINSER + USERDATEDWBMURL, RequestMethod.GET);
                        LoginRequest.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                        LoginRequest.add("KeyNo", psp.getUSERID());
                        LoginRequest.add("deviceId", new Build().MODEL);
                        CallServer.getInstance().add(getActivity(), LoginRequest, GsonCallBack.getInstance(), 0x0023, true, false, true);
                        break;
                    case 1://我的党费
                        wd.dismiss();
                        startActivity(new Intent(getActivity(), MyPartyPayActivity.class));
                        break;
                    case 2://我的资料-跳转
                        wd.dismiss();
                        startActivity(new Intent(getActivity(), MydataActivity.class));
                        break;
                }
            }
        };
        return v;
    }

    public void show() {
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("请选择上传方式：");
        final String[] items = new String[]{"本地", "拍照"};
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (items[which]) {
                    case "本地":
                        Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                        // 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
                        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(pickIntent, REQUESTCODE_PICK);
                        break;
                    case "拍照":
                        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //下面这句指定调用相机拍照后的照片存储的路径
                        takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
                        startActivityForResult(takeIntent, REQUESTCODE_TAKE);
                        break;

                    default:
                        break;
                }
                //关闭对话框
                dialog.dismiss();
            }
        });

        userimg = (RoundImageView) v.findViewById(R.id.userimg);//头像
        if(!psp.getICONSTEAM().equals("null")){
            userimg.setImageBitmap(FileUtils.stringtoBitmap(psp.getICONSTEAM()));
        }else{
            userimg.setImageDrawable(getResources().getDrawable(R.mipmap.appicon));
        }
        TextView username = (TextView) v.findViewById(R.id.username);//名字
        TextView mtime = (TextView) v.findViewById(R.id.mtime);//入党时间
        username.setText(psp.getUSERNAME());
        mtime.setText(psp.getPARTYTIME());
        RelativeLayout my1 = (RelativeLayout) v.findViewById(R.id.my1);
        RelativeLayout my2 = (RelativeLayout) v.findViewById(R.id.my2);
        RelativeLayout my3 = (RelativeLayout) v.findViewById(R.id.my3);
        RelativeLayout my4 = (RelativeLayout) v.findViewById(R.id.my4);
        RelativeLayout my5 = (RelativeLayout) v.findViewById(R.id.my5);

        TextView Outlogin = (TextView) v.findViewById(R.id.Outlogin);

        userimg.setOnClickListener(listener);
        username.setOnClickListener(listener);
        mtime.setOnClickListener(listener);
        my1.setOnClickListener(listener);
        my2.setOnClickListener(listener);
        my3.setOnClickListener(listener);
        my4.setOnClickListener(listener);
        my5.setOnClickListener(listener);
        Outlogin.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.userimg://头像
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    break;
                case R.id.username://姓名
                    startActivity(new Intent(getActivity(), TestWebActivity.class));
                    break;
                case R.id.mtime://入党时期
                    startActivity(new Intent(getActivity(), tsteActivity.class));
                    break;
                case R.id.my1://我的资料
//                    ?KeyNo=1&deviceId=123&token=2cfd4560539f887a5e420412b370b361
                    GsonRequest LoginRequest = new GsonRequest(URLINSER + USERDATEURL, RequestMethod.GET);
                    LoginRequest.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                    LoginRequest.add("KeyNo", psp.getUSERID());
                    LoginRequest.add("deviceId", new Build().MODEL);
                    CallServer.getInstance().add(getActivity(), LoginRequest, GsonCallBack.getInstance(), 0x002, true, false, true);
                    break;
                case R.id.my2://我的文档
                    startActivity(new Intent(getActivity(), MyFilesActivity.class));
                    break;
                case R.id.my3://我的党费
                    wd.show();
                    GsonRequest PartyPayRequest = new GsonRequest(URLINSER + PARTYPAY, RequestMethod.GET);
                    PartyPayRequest.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                    PartyPayRequest.add("deviceId", new Build().MODEL);
                    PartyPayRequest.add("KeyNo", psp.getUSERID());
                    CallServer.getInstance().add(getActivity(), PartyPayRequest, GsonCallBack.getInstance(), 0x103, true, false, true);
                    break;
                case R.id.my4://关于
                    startActivity(new Intent(getActivity(), AboutsActivity.class));
                    break;
                case R.id.my5://意见反馈
                    startActivity(new Intent(getActivity(), OpinionActivity.class));
                    break;
                case R.id.Outlogin://注销
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                    psp.putICONSTEAM("");
                    psp.putLoginStatus(false);
                    DataManager.PartyerList.data.UserlistPage.clear();
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE_PICK:// 直接从相册获取
                try {
                    startPhotoZoom(data.getData());
                } catch (NullPointerException e) {
                    e.printStackTrace();// 用户点击取消操作
                }
                break;
            case REQUESTCODE_TAKE:// 调用相机拍照
                File temp = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                startPhotoZoom(Uri.fromFile(temp));
                break;
            case REQUESTCODE_CUTTING:// 取得裁剪后的图片
                if (data != null) {
                    setPicToView(data);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            // 取得SDCard图片路径做显示
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(null, photo);
            userimg.setImageDrawable(drawable);
            String pic = null;
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                //将bitmap一字节流输出 Bitmap.CompressFormat.PNG 压缩格式，100：压缩率，baos：字节流
                photo.compress(Bitmap.CompressFormat.PNG, 100, baos);
                baos.close();
                byte[] buffer = baos.toByteArray();
                //将图片的字节流数据加密成base64字符输出
                pic = Base64.encodeToString(buffer, 0, buffer.length, Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            GsonRequest Request = new GsonRequest(URLINSER + UPDATEUSERDATEURL, RequestMethod.POST);
            Request.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
            Request.add("KeyNo", psp.getUSERID());
            Request.add("deviceId", new Build().MODEL);
            Request.add("head_img", pic);//头像(base64)
            CallServer.getInstance().add(getActivity(), Request, GsonCallBack.getInstance(), 0x0022, true, false, true);
            psp.putICONSTEAM(pic);
        }
    }
}