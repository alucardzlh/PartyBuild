package com.example.a25908.partybuild.Activitys;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a25908.partybuild.Adapters.MyGridAdapterClaim2;
import com.example.a25908.partybuild.Dialogs.WaitDialog;
import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.example.a25908.partybuild.Views.MyGridView;
import com.example.a25908.partybuild.Views.Toast;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yolanda.nohttp.RequestMethod;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;

import static com.example.a25908.partybuild.Utils.URLconstant.DONGTAIFABU;
import static com.example.a25908.partybuild.Utils.URLconstant.PARTYRTLISTURL;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;

/**
 * 发表动态
 * @author weixuan
 */
public class HairDynamicActivity extends BaseActivity {
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 0;
    @ViewInject(R.id.returnT)
    private ImageView back;
    @ViewInject(R.id.fileclear)
    private TextView fileclear;//发布
    @ViewInject(R.id.com_photo)
    private ImageView com_photo;//选择图片
    @ViewInject(R.id.dt_et_con)
    private EditText dt_et_con;//内容
    @ViewInject(R.id.myGridViewtc)
    private MyGridView myGridViewtc;//图片
    @ViewInject(R.id.tixing)
    private LinearLayout tixing;//提醒
    @ViewInject(R.id.tixing_tx)
    private TextView tixing_tx;//提醒列表
    public static WaitDialog wd;

    public static HairDynamicActivity instance = null;

    private static final String IMAGE_FILE_NAME = "avatarImage.jpg";// 图片文件名称
    private static String urlpath;			// 图片本地路径
    private static final int REQUESTCODE_PICK = 0;		// 相册选图标记
    private static final int REQUESTCODE_TAKE = 1;		// 相机拍照标记
    private static final int REQUESTCODE_CUTTING = 2;	// 图片裁切标记

    private static final String IMAGE_FILE_LOCATION = "file:///sdcard/temp.jpg";//temp file
    Uri imageUri = Uri.parse(IMAGE_FILE_LOCATION);//The Uri to store the big bitmap

    AlertDialog.Builder builder;
    public Drawable[] imgs1; //九张图片数组
    PartySharePreferences psp;

    List<String> listStirng=new ArrayList<>();
    public static Handler handler;

    MyGridAdapterClaim2 adapters;
    ArrayList<Drawable> myList=new ArrayList<Drawable>();
    boolean isShowDelete=false;//是否长按状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hair_dynamic);
        ViewUtils.inject(this);
        init();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what==1){
                    wd.dismiss();
                    Toast.show("发布成功");
                    finish();
                }
                else if(msg.what==2){
                    wd.dismiss();
                    startActivity(new Intent(HairDynamicActivity.this,RemindActivity.class));
                }
                else {
                    Toast.show("返回为空");
                }
            }
        };

    }

    private void init(){
        instance = this;
        wd = new WaitDialog(HairDynamicActivity.this);
        wd.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        psp = new PartySharePreferences();
        back.setVisibility(View.VISIBLE);
        fileclear.setText("发布");
        fileclear.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tixing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(DataManager.PartyerList.data.UserlistPage.size()>0 && DataManager.PartyerList.data.UserlistPage!=null){
                        handler.sendEmptyMessage(2);
                    }else{
                        wd.show();
                        GsonRequest Request = new GsonRequest(URLINSER +PARTYRTLISTURL, RequestMethod.GET);
                        Request.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                        Request.add("KeyNo", psp.getUSERID());
                        Request.add("deviceId", new Build().MODEL);
                        Request.add("udid", "");
                        CallServer.getInstance().add(HairDynamicActivity.this, Request, GsonCallBack.getInstance(), 0x0033, true, false, true);
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                    wd.show();
                    GsonRequest Request = new GsonRequest(URLINSER +PARTYRTLISTURL, RequestMethod.POST);
                    Request.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                    Request.add("KeyNo", psp.getUSERID());
                    Request.add("udid", "");
                    Request.add("deviceId", new Build().MODEL);
                    CallServer.getInstance().add(HairDynamicActivity.this, Request, GsonCallBack.getInstance(), 0x0033, true, false, true);
                }

            }
        });
        fileclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(dt_et_con.getText().toString().trim())&myList.isEmpty()){
                    Toast.show("请输入内容或选择照片");
                }
                else if(!TextUtils.isEmpty(dt_et_con.getText())&myList.isEmpty()){
                    wd.show();
                    GsonRequest fabu = new GsonRequest(URLINSER + DONGTAIFABU, RequestMethod.POST);
                    fabu.add("KeyNo",psp.getUSERID());
                    fabu.add("username",psp.getUSERNAME());
                    fabu.add("content",dt_et_con.getText().toString());
                    fabu.add("deviceId",new Build().MODEL);
                    fabu.add("token", MD5.MD5s(psp.getUSERID() + new  Build().MODEL));
                    CallServer.getInstance().add(HairDynamicActivity.this,fabu, GsonCallBack.getInstance(),0x301,true,false,true);
                }
                else if (myList!=null){
                    addImgs1();
                }
            }
        });
        builder= new AlertDialog.Builder(this);
        builder.setTitle("请选择上传方式：");
        final String[] items = new String[]{"本地", "拍照"};
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                imgs1=new Drawable[9];
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


        myGridViewtc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                ImageView bg= (ImageView)view.findViewById(R.id.ivc_items);
                if(!isShowDelete){
                    Observable.just(myList.get(position))
                            .subscribe(new Action1<Drawable>() {
                                @Override
                                public void call(Drawable drawable) {
                                    onThumbnailClick(drawable);
                                }
                            });

                }else{
                    delete(position);
                    adapters=new MyGridAdapterClaim2(HairDynamicActivity.this,myList);
                    adapters.setIsShowDelete(isShowDelete);
                    myGridViewtc.setAdapter(adapters);
                    adapters.notifyDataSetChanged();
                }

            }
        });
        myGridViewtc.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (isShowDelete) {
                    isShowDelete = false;
                } else {
                    isShowDelete=true;
                    adapters.setIsShowDelete(isShowDelete);
                }
                adapters.setIsShowDelete(isShowDelete);
                return true;
            }
        });


        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                //READ_EXTERNAL_STORAGE
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                        READ_EXTERNAL_STORAGE_REQUEST_CODE);
                return;
            }
        }
        com_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }

    /**
     * 删除图片改容器
     */
    private void addImgs1(){
        for(int i=0;i<myList.size();i++){
            BitmapDrawable bd = (BitmapDrawable) myList.get(i);
            Bitmap bitmap = bd.getBitmap();
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                //将bitmap一字节流输出 Bitmap.CompressFormat.PNG 压缩格式，100：压缩率，baos：字节流
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                baos.close();
                byte[] buffer = baos.toByteArray();
                //将图片的字节流数据加密成base64字符输出
                String pic= Base64.encodeToString(buffer, 0, buffer.length,Base64.DEFAULT);
                listStirng.add(pic);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(imgs1!=null||listStirng.size()>0) {
            String attchmentDescS = "";
            String attchmentSteamS = "";
                            /*for(int j=0;j<i;j++){
                                attchmentDescS=attchmentDescS+"pic@";
                            }*/
            for (int c = 0; c < listStirng.size(); c++) {
                attchmentDescS = attchmentDescS + "pic@";//
                attchmentSteamS = attchmentSteamS + listStirng.get(c) + "@";
            }
            wd.show();
            GsonRequest fabu2 = new GsonRequest(URLINSER + DONGTAIFABU, RequestMethod.POST);
            fabu2.add("KeyNo",psp.getUSERID());
            fabu2.add("username",psp.getUSERNAME());
            if (!TextUtils.isEmpty(dt_et_con.getText())){
                fabu2.add("content",dt_et_con.getText().toString());
            }
            fabu2.add("path",attchmentSteamS);//base64码内容，多文件以@符号分割
            fabu2.add("name",attchmentDescS);//图片描述内容，多文件以@符号分割
            fabu2.add("deviceId",new Build().MODEL);
            fabu2.add("token", MD5.MD5s(psp.getUSERID() + new  Build().MODEL));
            CallServer.getInstance().add(HairDynamicActivity.this,fabu2, GsonCallBack.getInstance(),0x301,true,false,true);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUESTCODE_PICK:// 直接从相册获取
                try {
                    startPhotoZoom(data.getData());
                } catch (NullPointerException e) {
                    e.printStackTrace();// 用户点击取消操作
                }
//                Observable.just(decodeUriAsBitmap(data.getData()))
//                        .subscribe(new Action1<Bitmap>() {
//                                       @Override
//                                       public void call(Bitmap bitmap) {
//                                           setPicToView2(bitmap);
//                                       }
//                                   }
//                        );

                break;
            case REQUESTCODE_TAKE:// 调用相机拍照
                File temp = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                startPhotoZoom(Uri.fromFile(temp));
                break;
            case REQUESTCODE_CUTTING:// 取得裁剪后的图片
//                if (data != null) {
////                    setPicToView(data);
//                    setPicToView2(decodeUriAsBitmap(data.getData()));
//                    Log.e("sssa",data+"");
//                }
                if(imageUri != null){
                    Bitmap bitmap = decodeUriAsBitmap(imageUri);//decode bitmap
                    setPicToView2(bitmap);
                    }
                break;
        }




        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * 裁剪图片方法实现
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 600);
        intent.putExtra("aspectY", 600);
        intent.putExtra("outputX", 600);
        intent.putExtra("outputY", 600);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
//        startActivityForResult(intent, CHOOSE_BIG_PICTURE);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        Log.e("ex",extras+"");
        if (extras != null) {
            // 取得SDCard图片路径做显示
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(null, photo);
            myList.add(drawable);;
            adapters = new MyGridAdapterClaim2(HairDynamicActivity.this, myList);
            myGridViewtc.setAdapter(adapters);
        }
    }
    private void setPicToView2(Bitmap bitmap) {
            Drawable drawable = new BitmapDrawable(null, bitmap);
            myList.add(drawable);
            adapters = new MyGridAdapterClaim2(HairDynamicActivity.this, myList);
            myGridViewtc.setAdapter(adapters);
    }

    private Bitmap decodeUriAsBitmap(Uri uri){
        Bitmap bitmap = null;
        try {
             bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            } catch (FileNotFoundException e) {
            e.printStackTrace();
             return null;
            }
        return bitmap;
    }

    /**
     * 显示大图
     * @param img
     */
    public void onThumbnailClick(Drawable img) {
        final Dialog dialog = new Dialog(HairDynamicActivity.this, android.R.style.Theme_Black_NoTitleBar);
        ImageView imgView = getView(img);
        dialog.setContentView(imgView);
        dialog.show();
        // 点击图片消失
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
    }
    /**
     * 获取图片
     * @param img
     * @return
     */
    private ImageView getView(Drawable img) {
        ImageView imgView = new ImageView(HairDynamicActivity.this);
        imgView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imgView.setImageDrawable(img);
        return imgView;
    }

    /**
     * 执行删除方法
     * @param position
     */
    private void delete(int position) {
        ArrayList<Drawable> newList = new ArrayList<Drawable>();
        if(isShowDelete){
            myList.remove(position);
            isShowDelete=false;
        }
        newList.addAll(myList);
        myList.clear();
        myList.addAll(newList);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode==0){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                com_photo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
            }
            else {
                Toast.show("权限获取失败，无法上传图片，请到设置中开放存储权限");
            }
        }

    }
}
