package com.example.a25908.partybuild.Fragments;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.a25908.partybuild.Activitys.HairDynamicActivity;
import com.example.a25908.partybuild.Adapters.DynamicAdapters;
import com.example.a25908.partybuild.Dialogs.WaitDialog;
import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.FileUtils;
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.example.a25908.partybuild.Views.Toast;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.List;

import static com.example.a25908.partybuild.Model.DataManager.mydynamic;
import static com.example.a25908.partybuild.Utils.URLconstant.DONGTAI;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;

/**
 * @author weixuan
 * 党群动态
 */
public class Fragment3 extends Fragment{
	private SuperRecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private DynamicAdapters adapters;
    public static List<DataManager.Mydynamic.DataBean.DynamiclistPageBean> list_dy;
    PartySharePreferences psp;
    public static Handler handler;
    public static WaitDialog waitDialog;
    protected boolean isVisible;

    private boolean touthis = false;//禁止滚动判定

    private boolean toastis = true;//判断滚到低端

    private boolean scrolled=false;//判断是否有滚动位移
    /**
     * 当Fragment可见时加载数据
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint())//当Fragment可见时加载数据
        {
            isVisible = true;
            psp = new PartySharePreferences();
            addData(psp);
        }
        else {
            isVisible = false;
        }

    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.activity_fragment3, container,false);
        recyclerView = (SuperRecyclerView) v.findViewById(R.id.recyclerView);
        psp = new PartySharePreferences();
        waitDialog = new WaitDialog(getActivity());
        list_dy = new ArrayList<>();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1://初始化
                        rvinit();
                        break;
                   case 2://评论失败
                       Toast.show("评论失败");
                        break;
                    case 3://删除成功
                        addData2(psp);
                        Toast.show("删除成功");
                        break;
                    case 4://刷新
                        list_dy.clear();
                        rvinit();
                       break;
                    case 5://加载
                        recyclerView.showMoreProgress();
                        list_dy.addAll(mydynamic.data.DynamiclistPage);
                        adapters.setDatas(list_dy);
                        adapters.notifyDataSetChanged();
                        touthis = false;
                        break;
                    case 6://发布成功
                        HairDynamicActivity.wd.dismiss();
                        HairDynamicActivity.instance.finish();
                        Toast.show("发布成功,请等待审核通过");
                        addData2(psp);
                        break;
                    case 7:
                        Fragment3.waitDialog.dismiss();
                        onThumbnailClick(FileUtils.stringtoBitmap(String.valueOf(DataManager.myimageid.data.DynamicImg.path)));
                        break;
                }
            }
        };



		return v;
	}

    /**
     * 初始化RecyclerView(网络数据)
     */
    private void rvinit(){
        toastis = true;
        list_dy = DataManager.mydynamic.data.DynamiclistPage;
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);//创建默认的线性LayoutManager
        recyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        adapters = new DynamicAdapters(getActivity(), list_dy);
        recyclerView.setAdapter(adapters);
        adapters.notifyDataSetChanged();
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return touthis;
            }
        });

        //下拉刷新
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addData2(psp);
                        recyclerView.setRefreshing(true);
                    }
                }, 10);
            }
        });
        //加载更多
        recyclerView.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxastVisiblePosition) {

                if (Integer.valueOf(DataManager.mydynamic.data.Paging.TotalPage)>Integer.valueOf(DataManager.mydynamic.data.Paging.CurrentPage)) {
                    recyclerView.showMoreProgress();
                    touthis = true;
                    GsonRequest TCTPRequest = new GsonRequest(URLINSER + DONGTAI, RequestMethod.GET);
                    TCTPRequest.add("KeyNo", psp.getUSERID());
                    TCTPRequest.add("deviceId", new Build().MODEL);
                    TCTPRequest.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                    TCTPRequest.add("PageSize",6);
                    TCTPRequest.add("PageIndex",Integer.valueOf(DataManager.mydynamic.data.Paging.CurrentPage)+1);
                    CallServer.getInstance().add(getActivity(), TCTPRequest, GsonCallBack.getInstance(), 0x3022, true, false, true);
                }
                else {
                    recyclerView.hideMoreProgress();
                    if(toastis){
                        Toast.show("没有动态了");
                        toastis = false;
                    }


                }
            }
        },1);



        //自动加载
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                Glide.with(getActivity()).resumeRequests();//图片加载重启
                if (dy!=0){
                    scrolled = true;
                }

            }

            @Override
            public void onScrollStateChanged(RecyclerView irecyclerView, int newState) {
                super.onScrollStateChanged(irecyclerView, newState);
                switch (newState){
                    case RecyclerView.SCROLL_STATE_IDLE: // The RecyclerView is not currently scrolling.
                        if (adapters.getScrolling()&&scrolled) {
                            //对于滚动不加载图片
//                            adapters.setScrolling(false);
                            adapters.notifyDataSetChanged();
                        }
                        scrolled=false;
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING: // The RecyclerView is currently being dragged by outside input such as user touch input.
//                        adapters.setScrolling(false);
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING: // The RecyclerView is currently animating to a final position while not under
//                        adapters.setScrolling(true);
                        break;
                }
            }
        });
    }


    /**
     * 查询数据
     * @param psp
     */
    private void addData(PartySharePreferences psp){
        GsonRequest TCTPRequest = new GsonRequest(URLINSER + DONGTAI, RequestMethod.GET);
        TCTPRequest.add("KeyNo",psp.getUSERID());
        TCTPRequest.add("deviceId",new Build().MODEL);
        TCTPRequest.add("token", MD5.MD5s(psp.getUSERID() + new  Build().MODEL));
        TCTPRequest.add("PageSize",5);
        TCTPRequest.add("PageIndex", 1);
        CallServer.getInstance().add(getActivity(),TCTPRequest, GsonCallBack.getInstance(),0x302,true,false,true);
    }

    /**
     * 刷新、加载
     * @param psp
     */
    private void addData2(PartySharePreferences psp){
        GsonRequest TCTPRequest = new GsonRequest(URLINSER + DONGTAI, RequestMethod.GET);
        TCTPRequest.add("KeyNo",psp.getUSERID());
        TCTPRequest.add("deviceId",new Build().MODEL);
        TCTPRequest.add("token", MD5.MD5s(psp.getUSERID() + new  Build().MODEL));
        TCTPRequest.add("PageSize",5);
        TCTPRequest.add("PageIndex", 1);
        CallServer.getInstance().add(getActivity(),TCTPRequest, GsonCallBack.getInstance(),0x3021,true,false,true);
    }

    /**
     * 显示大图
     */
    public void onThumbnailClick(Bitmap img) {
        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar);
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
        ImageView imgView = new ImageView(getActivity());
        imgView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imgView.setImageBitmap(img);
        return imgView;
    }


}
