package com.example.a25908.partybuild.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a25908.partybuild.Fragments.Fragment3;
import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.FileUtils;
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.example.a25908.partybuild.Views.CommentListView;
import com.example.a25908.partybuild.Views.ExpandTextView;
import com.example.a25908.partybuild.Views.FormatCurrentData;
import com.example.a25908.partybuild.Views.MultiImageView;
import com.example.a25908.partybuild.Views.RoundImageView;
import com.yolanda.nohttp.RequestMethod;

import java.util.List;

import static com.example.a25908.partybuild.Utils.URLconstant.DONGTAIDELETE;
import static com.example.a25908.partybuild.Utils.URLconstant.DONGTAIPLFB;
import static com.example.a25908.partybuild.Utils.URLconstant.DONGTAIPRSISE;
import static com.example.a25908.partybuild.Utils.URLconstant.IMAGEID;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;

/**
 * 党群动态的adapter
 * Created by weixuan on 2016/9/1.
 */

public class DynamicAdapters extends BaseRecycleViewAdapter {

    private Context context;
    private long mLasttime = 0;
    private List<DataManager.Mydynamic.DataBean.DynamiclistPageBean> list;
    private  boolean scrollState=false;

    public DynamicAdapters(Context context,List<DataManager.Mydynamic.DataBean.DynamiclistPageBean> list){
        this.context = context;
        this.list = list;
    }
    public void setDataList(List<DataManager.Mydynamic.DataBean.DynamiclistPageBean> ComplainList){
        this.list=ComplainList;
    }
    public void getImage(){
        onThumbnailClick(FileUtils.stringtoBitmap(String.valueOf(DataManager.myimageid.data.DynamicImg.path)));
    }
    public void setScrolling(boolean scrollState) {
        this.scrollState = scrollState;
    }
    public boolean getScrolling() {
        return scrollState;
    }



    //创建新View，被LayoutManager所调用
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_dongtai, parent, false);
        viewHolder = new ImageViewHolder(view);



        return viewHolder;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final DynamicViewHolder viewHolder = (DynamicViewHolder) holder;
        final PartySharePreferences psp = new PartySharePreferences();

        if (!scrollState){
        //获取、处理评论
            viewHolder.commentList.setDatas(list.get(position).commentslist);}
            //评论
            viewHolder.item_dt_huihu.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    String pinlun = viewHolder.item_dt_pinglun.getText().toString();

                    if (!pinlun.trim().equals("")){
                        GsonRequest PLRequest = new GsonRequest(URLINSER + DONGTAIPLFB, RequestMethod.GET);
                        PLRequest.add("KeyNo",psp.getUSERID());
                        PLRequest.add("content",pinlun);
                        PLRequest.add("username",psp.getUSERNAME());
                        PLRequest.add("nvid", list.get(position).dynamicid);
                        PLRequest.add("type",2);
                        PLRequest.add("deviceId",new Build().MODEL);
                        PLRequest.add("token", MD5.MD5s(psp.getUSERID() + new  Build().MODEL));
                        CallServer.getInstance().add(context,PLRequest, GsonCallBack.getInstance(),0x303,true,false,true);
                        list.get(position).commentslist.add(addpinglun(pinlun,psp));
                        viewHolder.commentList.setDatas(list.get(position).commentslist);
//                        Toast.makeText(context,"评论成功",Toast.LENGTH_SHORT).show();
                        viewHolder.item_dt_pinglun.setText("");
                    }
                    else {
                        Toast.makeText(context,"请输入内容",Toast.LENGTH_SHORT).show();
                    }

                }
            });

            //处理评论回复
            viewHolder.commentList.setOnItemClickListener(new CommentListView.OnItemClickListener() {

                @Override
                public void onItemClick(final int commentPosition) {
                    final String name = list.get(position).commentslist.get(commentPosition).username;//点击的名字
                    final int id = list.get(position).commentslist.get(commentPosition).userid;//点击的名字 id
                    if (id!=Integer.parseInt(psp.getUSERID())){
                        viewHolder.item_dt_pinglun.requestFocus();//获取edittext焦点
                        viewHolder.item_dt_pinglun.setText("");
                        viewHolder.item_dt_pinglun.setHint("回复"+name+":");
                        //打开软键盘
                        final InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        //回复评论
                        viewHolder.item_dt_huihu.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                String pinlun1 = viewHolder.item_dt_pinglun.getText().toString();
                                if (!pinlun1.trim().equals("")){
                                    GsonRequest PLRequest = new GsonRequest(URLINSER + DONGTAIPLFB, RequestMethod.GET);
                                    PLRequest.add("KeyNo",psp.getUSERID());
                                    PLRequest.add("content",pinlun1);
                                    PLRequest.add("username",psp.getUSERNAME());
                                    PLRequest.add("nvid", list.get(position).dynamicid);
                                    PLRequest.add("type",2);
                                    PLRequest.add("to_username",name);
                                    PLRequest.add("to_userid",id);
                                    PLRequest.add("deviceId",new Build().MODEL);
                                    PLRequest.add("token", MD5.MD5s(psp.getUSERID() + new  Build().MODEL));
                                    CallServer.getInstance().add(context,PLRequest, GsonCallBack.getInstance(),0x303,true,false,true);
                                    list.get(position).commentslist.add(addpinglun2(pinlun1,psp,id,name));
                                    viewHolder.commentList.setDatas(list.get(position).commentslist);;
//                                    Toast.makeText(context,"评论成功",Toast.LENGTH_SHORT).show();
                                    viewHolder.item_dt_pinglun.clearFocus();//取消焦点
                                    //关闭软键盘
                                    imm.hideSoftInputFromWindow(viewHolder.item_dt_pinglun.getWindowToken(), 0);
                                    viewHolder.item_dt_pinglun.setText("");
                                    viewHolder.item_dt_pinglun.setHint("我也要评论");

                                }
                                else {
                                    Toast.makeText(context,"请输入内容",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }


                }
            });
//            //处理评论列表长按事件
//            viewHolder.commentList.setOnItemLongClickListener(new CommentListView.OnItemLongClickListener() {
//                @Override
//                public void onItemLongClick(int position) {
//
//                }
//            });



        //===============================================================
        //头像TextUtils.isEmpty()
        if (!list.get(position).head_img.isEmpty()){
            Bitmap bitmap = FileUtils.stringtoBitmap(list.get(position).head_img);
            viewHolder.item_dt_tx.setImageBitmap(bitmap);}
        else {viewHolder.item_dt_tx.setImageResource(R.mipmap.ic_launcher);}

        viewHolder.item_dt_name.setText(list.get(position).username);
        viewHolder.item_dt_context.setText(list.get(position).content);
        viewHolder.item_dt_time.setText(FormatCurrentData.getTimeRange(list.get(position).add_time));
        viewHolder.item_dt_zanshu.setText(list.get(position).praise+"人觉的很赞");

        //删除,判定是否是自己的动态
        if (psp.getUSERID().equals(list.get(position).userid)){
            viewHolder.item_dt_delete.setVisibility(View.VISIBLE);
            viewHolder.item_dt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GsonRequest DELETERequest = new GsonRequest(URLINSER + DONGTAIDELETE, RequestMethod.GET);
                DELETERequest.add("KeyNo",list.get(position).dynamicid);
                DELETERequest.add("deviceId",new Build().MODEL);
                DELETERequest.add("token", MD5.MD5s(list.get(position).dynamicid + new  Build().MODEL));
                CallServer.getInstance().add(context,DELETERequest, GsonCallBack.getInstance(),0x304,true,false,true);
            }
        });
        }
        else {
            viewHolder.item_dt_delete.setVisibility(View.GONE);
        }

        //点赞
        if(list.get(position).praiseType.equals("1")){
            viewHolder.item_dt_dzan.setText("已赞");
            viewHolder.item_dt_zantu.setImageResource(R.drawable.item_click_like_red);
            viewHolder.item_dt_dzan.setTextColor(Color.rgb(221,71,61));
        }
        else{
            viewHolder.item_dt_zantu.setImageResource(R.drawable.item_click_like);
            viewHolder.item_dt_dzan.setTextColor(Color.rgb(174,174,174));
            viewHolder.item_dt_dzan.setText("赞");

        }
        viewHolder.item_dt_zan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(System.currentTimeMillis()-mLasttime<700) {//防止快速点击操作
                    com.example.a25908.partybuild.Views.Toast.show("您点击太快，请稍后...");
                    return;
                }
                mLasttime = System.currentTimeMillis();

                if (list.get(position).praiseType.equals("0")){//点赞
                    GsonRequest PLRequest = new GsonRequest(URLINSER + DONGTAIPRSISE, RequestMethod.GET);
                    PLRequest.add("KeyNo",psp.getUSERID());
                    PLRequest.add("username",psp.getUSERNAME());
                    PLRequest.add("nvid", list.get(position).dynamicid);
                    PLRequest.add("type",2);
                    PLRequest.add("deviceId",new Build().MODEL);
                    PLRequest.add("token", MD5.MD5s(psp.getUSERID() + new  Build().MODEL));
                    CallServer.getInstance().add(context,PLRequest, GsonCallBack.getInstance(),0x303,true,false,true);
                    viewHolder.item_dt_dzan.setText("已赞");
                    viewHolder.item_dt_zantu.setImageResource(R.drawable.item_click_like_red);
                    viewHolder.item_dt_dzan.setTextColor(Color.rgb(221,71,61));
                    String zan = String.valueOf(Integer.parseInt(list.get(position).praise)+1);
                    viewHolder.item_dt_zanshu.setText(zan+"人觉的很赞");
                    list.get(position).praise = zan;
                    list.get(position).praiseType = "1";
                }
                else if (list.get(position).praiseType.equals("1")){//取消赞
                    GsonRequest PLRequest = new GsonRequest(URLINSER + DONGTAIPRSISE, RequestMethod.GET);
                    PLRequest.add("KeyNo",psp.getUSERID());
                    PLRequest.add("username",psp.getUSERNAME());
                    PLRequest.add("nvid", list.get(position).dynamicid);
                    PLRequest.add("type",2);
                    PLRequest.add("deviceId",new Build().MODEL);
                    PLRequest.add("token", MD5.MD5s(psp.getUSERID() + new  Build().MODEL));
                    CallServer.getInstance().add(context,PLRequest, GsonCallBack.getInstance(),0x303,true,false,true);
                    String zan = String.valueOf(Integer.parseInt(list.get(position).praise)-1);
                    viewHolder.item_dt_zanshu.setText(zan+"人觉的很赞");
                    viewHolder.item_dt_dzan.setText("赞");
                    viewHolder.item_dt_zantu.setImageResource(R.drawable.item_click_like);
                    viewHolder.item_dt_dzan.setTextColor(Color.rgb(174,174,174));
                    list.get(position).praise = zan;
                    list.get(position).praiseType = "0";
                }

            }
        });

        if (!scrollState){
            //图片处理
            if (holder instanceof ImageViewHolder) {
//            final List<String> photos = new ArrayList<>();
//            if (!list.get(position).imglist.isEmpty()){
//                for (int i = 0;list.get(position).imglist.size()>i;i++){
//                    photos.add(String.valueOf(list.get(position).imglist.get(i).path));
//                }
//            }


                if (!list.get(position).imglist.isEmpty()) {
                    ((ImageViewHolder) holder).multiImageView.setVisibility(View.VISIBLE);
                    ((ImageViewHolder) holder).multiImageView.setList(list.get(position).imglist);
                    ((ImageViewHolder) holder).multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int iposition) {
                            //点击查看大图
                            Fragment3.waitDialog.show();
                            GsonRequest DELETERequest6 = new GsonRequest(URLINSER + IMAGEID, RequestMethod.GET);
                            DELETERequest6.add("KeyNo",list.get(position).dynamicid);
                            DELETERequest6.add("deviceId",new Build().MODEL);
                            DELETERequest6.add("token", MD5.MD5s(list.get(position).dynamicid + new  Build().MODEL));
                            DELETERequest6.add("pictureid",list.get(position).imglist.get(iposition).pictureid);
                            CallServer.getInstance().add(context,DELETERequest6, GsonCallBack.getInstance(),0x305,true,false,true);
//                            onThumbnailClick(FileUtils.stringtoBitmap(String.valueOf(list.get(position).imglist.get(iposition).path)));
                        }
                    });
                } else {
                    ((ImageViewHolder) holder).multiImageView.setVisibility(View.GONE);
                }

            }
        }

//        //图片处理
//        if (holder instanceof ImageViewHolder) {
////            final List<String> photos = new ArrayList<>();
////            if (!list.get(position).imglist.isEmpty()){
////                for (int i = 0;list.get(position).imglist.size()>i;i++){
////                    photos.add(String.valueOf(list.get(position).imglist.get(i).path));
////                }
////            }
//
//
//            if (!list.get(position).imglist.isEmpty()) {
//                ((ImageViewHolder) holder).multiImageView.setVisibility(View.VISIBLE);
//                ((ImageViewHolder) holder).multiImageView.setList(list.get(position).imglist);
//                ((ImageViewHolder) holder).multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int iposition) {
//                        //点击查看大图
//                        onThumbnailClick(FileUtils.stringtoBitmap(String.valueOf(list.get(position).imglist.get(iposition).path)));
//                    }
//                });
//            } else {
//                ((ImageViewHolder) holder).multiImageView.setVisibility(View.GONE);
//            }
//
//        }
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 显示评论
     */
    private DataManager.Mydynamic.DataBean.DynamiclistPageBean.CommentslistBean addpinglun(String pinlun,PartySharePreferences psp){
        DataManager.Mydynamic.DataBean.DynamiclistPageBean.CommentslistBean commentslistBean = new DataManager.Mydynamic.DataBean.DynamiclistPageBean.CommentslistBean();
        commentslistBean.content = pinlun;
        commentslistBean.username = psp.getUSERNAME();
        commentslistBean.userid = Integer.parseInt(psp.getUSERID());
        return commentslistBean;
    }

    /**
     * 显示回复评论
     */
    private DataManager.Mydynamic.DataBean.DynamiclistPageBean.CommentslistBean addpinglun2(String pinlun,PartySharePreferences psp,int id,String name){
        DataManager.Mydynamic.DataBean.DynamiclistPageBean.CommentslistBean commentslistBean = new DataManager.Mydynamic.DataBean.DynamiclistPageBean.CommentslistBean();
        commentslistBean.content = pinlun;
        commentslistBean.username = psp.getUSERNAME();
        commentslistBean.userid = Integer.parseInt(psp.getUSERID());
        commentslistBean.to_username = name;
        commentslistBean.to_userid = id;
        return commentslistBean;
    }
    /**
     * 显示大图
     */
    public void onThumbnailClick(Bitmap img) {
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar);
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
    private ImageView getView(Bitmap img) {
        ImageView imgView = new ImageView(context);
        imgView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imgView.setImageBitmap(img);
        return imgView;
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    abstract class DynamicViewHolder extends RecyclerView.ViewHolder{
        public final static int TYPE_IMAGE = 2;
        public int viewType;
        private RoundImageView item_dt_tx;//头像
        private TextView item_dt_name;//名字
        private TextView item_dt_time;//时间
        private ExpandTextView item_dt_context; //内容
        private TextView item_dt_zanshu;//点赞数
        private LinearLayout item_dt_zan;//点赞
        private LinearLayout item_dt_delete;//删除
        private Button item_dt_huihu;//回复
        private EditText item_dt_pinglun;//评论
        private TextView item_dt_dzan;//点赞状态
        private ImageView item_dt_zantu;//点赞图片
        /** 评论列表 */
        public CommentListView commentList;

        public DynamicViewHolder(View itemView,int viewType) {
            super(itemView);
            this.viewType = viewType;
            ViewStub viewStub = (ViewStub) itemView.findViewById(R.id.viewStub);
            initSubView(viewType,viewStub);

            item_dt_tx = (RoundImageView) itemView.findViewById(R.id.item_dt_tx);
            item_dt_name = (TextView) itemView.findViewById(R.id.item_dt_name);
            item_dt_time = (TextView) itemView.findViewById(R.id.item_dt_time);
            item_dt_context = (ExpandTextView) itemView.findViewById(R.id.item_dt_context);
            item_dt_zanshu = (TextView) itemView.findViewById(R.id.item_dt_zanshu);
            item_dt_zan = (LinearLayout) itemView.findViewById(R.id.item_dt_zan);
            item_dt_delete = (LinearLayout) itemView.findViewById(R.id.item_dt_delete);
            item_dt_huihu = (Button) itemView.findViewById(R.id.item_dt_huihu);
            item_dt_pinglun = (EditText) itemView.findViewById(R.id.item_dt_pinglun);
            commentList = (CommentListView) itemView.findViewById(R.id.commentList);
            item_dt_dzan = (TextView) itemView.findViewById(R.id.item_dt_dzan);
            item_dt_zantu = (ImageView) itemView.findViewById(R.id.item_dt_zantu);
        }
        public abstract void initSubView(int viewType, ViewStub viewStub);
    }

    class ImageViewHolder extends DynamicViewHolder {
        /** 图片*/
        public MultiImageView multiImageView;

        public ImageViewHolder(View itemView){
            super(itemView, TYPE_IMAGE);
        }

        @Override
        public void initSubView(int viewType, ViewStub viewStub) {
            if(viewStub == null){
                throw new IllegalArgumentException("viewStub is null...");
            }
            viewStub.setLayoutResource(R.layout.viewstub_imgbody);
            View subView = viewStub.inflate();
            MultiImageView multiImageView = (MultiImageView) subView.findViewById(R.id.multiImagView);
            if(multiImageView != null){
                this.multiImageView = multiImageView;
            }
        }
    }



    }
