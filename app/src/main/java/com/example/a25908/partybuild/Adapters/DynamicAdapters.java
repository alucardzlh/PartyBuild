package com.example.a25908.partybuild.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a25908.partybuild.Activitys.ImagePagerActivity;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Views.CommentListView;
import com.example.a25908.partybuild.Views.ExpandTextView;
import com.example.a25908.partybuild.Views.MultiImageView;
import com.example.a25908.partybuild.Views.RoundImageView;

import java.util.List;

/**
 * 党群动态的adapter
 * Created by weixuan on 2016/9/1.
 */

public class DynamicAdapters extends BaseRecycleViewAdapter {

    private Context context;
    private List<DataManager.Dynamic> list;

    public DynamicAdapters(Context context,List<DataManager.Dynamic> list){
        this.context = context;
        this.list = list;
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
        viewHolder.commentList.setDatas(list.get(position).pinlun);
        viewHolder.item_dt_tx.setImageResource(list.get(position).iv);
        viewHolder.item_dt_name.setText(list.get(position).name);
        viewHolder.item_dt_context.setText(list.get(position).context1);
        viewHolder.item_dt_time.setText(list.get(position).time1);
        viewHolder.item_dt_zanshu.setText(list.get(position).zanshu+"人觉的很赞");
        //删除
        viewHolder.item_dt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除
                Toast.makeText(context,"点击了第"+position+"个item的删除",Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.item_dt_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean is = true;
                if (is){
                    viewHolder.item_dt_zanshu.setText(list.get(position).zanshu+1+"人觉的很赞");
                    is =false;
                }
                else {
                    Toast.makeText(context,"已点赞",Toast.LENGTH_SHORT).show();
                }

            }
        });
        //评论
        viewHolder.item_dt_huihu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String pinlun = viewHolder.item_dt_pinglun.getText().toString();
                if (!pinlun.equals("")){
                    DataManager.Dynamic.CommentItem commentItem1 = new DataManager.Dynamic.CommentItem();
                    commentItem1.setContent(pinlun);
                    commentItem1.setUser(new DataManager.Dynamic.User("自己"));
                    list.get(position).pinlun.add(commentItem1);
                    viewHolder.commentList.setDatas(list.get(position).pinlun);
                    Toast.makeText(context,"评论成功",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context,"请输入内容",Toast.LENGTH_SHORT).show();
                }

            }
        });

        //处理评论列表
        viewHolder.commentList.setOnItemClickListener(new CommentListView.OnItemClickListener() {
            @Override
            public void onItemClick(final int commentPosition) {
                Toast.makeText(context,"点击了 "+list.get(position).pinlun.get(commentPosition).getUser(),Toast.LENGTH_SHORT).show();
                viewHolder.item_dt_pinglun.requestFocus();//获取edittext焦点
                //打开软键盘
                InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                //回复评论
                viewHolder.item_dt_huihu.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String pinlun = viewHolder.item_dt_pinglun.getText().toString();
                        if (!pinlun.equals("")){
                            DataManager.Dynamic.CommentItem commentItem1 = new DataManager.Dynamic.CommentItem();
                            commentItem1.setContent(pinlun);
                            commentItem1.setUser(new DataManager.Dynamic.User("10","自己"));
                            commentItem1.setToReplyUser(new DataManager.Dynamic.User(list.get(position).pinlun.get(commentPosition).getUser().getUsername()));
                            list.get(position).pinlun.add(commentItem1);
                            viewHolder.commentList.setDatas(list.get(position).pinlun);
                            Toast.makeText(context,"评论成功",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context,"请输入内容",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });


        //图片处理
        if (holder instanceof ImageViewHolder) {
            final List<String> photos =list.get(position).photos;
            if (photos != null && photos.size() > 0) {
                ((ImageViewHolder) holder).multiImageView.setVisibility(View.VISIBLE);
                ((ImageViewHolder) holder).multiImageView.setList(photos);
                ((ImageViewHolder) holder).multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //imagesize是作为loading时的图片size
                       ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
                       ImagePagerActivity.startImagePagerActivity(context, photos, position, imageSize);
                    }
                });
            } else {
                ((ImageViewHolder) holder).multiImageView.setVisibility(View.GONE);
            }

        }
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return list.size();
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
