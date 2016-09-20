package com.example.a25908.partybuild.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.a25908.partybuild.Adapters.DynamicAdapters;
import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.yolanda.nohttp.RequestMethod;

import java.util.ArrayList;
import java.util.List;

import static com.example.a25908.partybuild.Utils.URLconstant.DONGTAI;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;

/**
 * @author weixuan
 * 党群动态
 */
public class Fragment3 extends Fragment {
	private SuperRecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private DynamicAdapters adapters;
    public static List<DataManager.Mydynamic.DataBean.DynamiclistPageBean> list_dy;
    PartySharePreferences psp;
    public static Handler handler;
    protected boolean isVisible;
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
            GsonRequest TCTPRequest = new GsonRequest(URLINSER + DONGTAI, RequestMethod.GET);
            TCTPRequest.add("KeyNo",psp.getUSERID());
            TCTPRequest.add("deviceId",new Build().MODEL);
            TCTPRequest.add("token", MD5.MD5s(psp.getUSERID() + new  Build().MODEL));
            CallServer.getInstance().add(getActivity(),TCTPRequest, GsonCallBack.getInstance(),0x302,true,false,true);
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

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1://加载评论
                        rvinit();

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
        list_dy = new ArrayList<>();
        list_dy.addAll(DataManager.mydynamic.data.DynamiclistPage);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);//创建默认的线性LayoutManager
        recyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        adapters = new DynamicAdapters(getActivity(), list_dy);
        recyclerView.setAdapter(adapters);


        //下拉刷新
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        //自动加载
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Glide.with(getActivity()).resumeRequests();//图片加载重启

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState != RecyclerView.SCROLL_STATE_IDLE){
                    Glide.with(getActivity()).pauseRequests();//图片加载暂停
                }

            }
        });
    }

//    /**
//     * 初始化RecyclerView
//     */
//    private void recyclerViewInit(){
//        list_dy = new ArrayList<>();
////        addlist();
//
//
//    }



    /**
     * 添加数据
     */
//    private void addlist(){
//        DataManager.Dynamic dynamic = new DataManager.Dynamic();
//        dynamic.context1 = "阿斯顿撒撒反对的地方撒发达方式表现出不屑的发生的v大师傅但是阿斯顿撒撒反对的地方撒发达方式表现出不屑的发生的v大师傅但是阿斯顿撒撒反对的地方撒发达方式表现出不屑的发生的v大师傅但是阿斯顿撒撒反对的地方撒发达方式表现出不屑的发生的v大师傅但是";
//        dynamic.iv = R.drawable.ceshi_item1;
//        dynamic.time1 = "今天 14:20";
//        dynamic.Dtid = "1";
//        dynamic.zanshu = 14;
//        dynamic.Dtname = "好榜样";
//        DataManager.Dynamic.CommentItem commentItem = new DataManager.Dynamic.CommentItem();
//        commentItem.context2 = "不错，哈哈哈 ";
//        commentItem.Userid = "1";
//        commentItem.Username = "哈哈";
//        dynamic.pinlun.add(commentItem);
//        DataManager.Dynamic.CommentItem commentItem56 = new DataManager.Dynamic.CommentItem();
//        commentItem56.context2 = "阿三打算打算打算哈";
//        commentItem56.Userid = "2";
//        commentItem56.Username = "雷锋";
//        dynamic.pinlun.add(commentItem56);
//        dynamic.photos.add("http://img0.imgtn.bdimg.com/it/u=4126537434,739604392&fm=11&gp=0.jpg");
//        dynamic.photos.add("http://img2.imgtn.bdimg.com/it/u=2033262106,335703526&fm=21&gp=0.jpg");
//        dynamic.photos.add("http://img1.imgtn.bdimg.com/it/u=1618497096,1247878033&fm=21&gp=0.jpg");
//        list_dy.add(dynamic);
//
//        DataManager.Dynamic dynamic1 = new DataManager.Dynamic();
//        dynamic1.context1 = "无为而让他同意而儿额头二人太热特热";
//        dynamic1.iv = R.drawable.ceshi_item1;
//        dynamic1.time1 = "今天 14:20";
//        dynamic1.zanshu = 14;
//        dynamic1.Dtid = "2";
//        dynamic1.Dtname = "好办样";
//        DataManager.Dynamic.CommentItem commentItem1 = new DataManager.Dynamic.CommentItem();
//        commentItem1.context2 = "阿三打算打算打算哈";
//        commentItem1.Userid = "2";
//        commentItem1.Username = "雷锋";
//        dynamic1.pinlun.add(commentItem1);
//        dynamic1.photos.add("http://img2.imgtn.bdimg.com/it/u=2033262106,335703526&fm=21&gp=0.jpg");
//        dynamic1.photos.add("http://img0.imgtn.bdimg.com/it/u=3903822374,3422085572&fm=21&gp=0.jpg");
//        dynamic1.photos.add("http://img4.imgtn.bdimg.com/it/u=1190416981,1869094977&fm=21&gp=0.jpg");
//        dynamic1.photos.add("http://img2.imgtn.bdimg.com/it/u=3631161198,2976176838&fm=21&gp=0.jpg");
//        dynamic1.photos.add("http://img0.imgtn.bdimg.com/it/u=3903822374,3422085572&fm=21&gp=0.jpg");
//        list_dy.add(dynamic1);
//
//        DataManager.Dynamic dynamic2 = new DataManager.Dynamic();
//        dynamic2.context1 = "啊请问我的夫人有条件公公高考";
//        dynamic2.iv = R.drawable.ceshi_item1;
//        dynamic2.time1 = "今天 11:20";
//        dynamic2.zanshu = 56;
//        dynamic2.Dtid = "3";
//        dynamic2.Dtname = "19356";
//        DataManager.Dynamic.CommentItem commentItem2 = new DataManager.Dynamic.CommentItem();
//        commentItem2.context2 = "应用于如若try也如统驭土语体育";
//        commentItem2.Userid = "3";
//        commentItem2.Username = "哟哟";
//        dynamic2.pinlun.add(commentItem2);
//        DataManager.Dynamic.CommentItem commentItem4 = new DataManager.Dynamic.CommentItem();
//        commentItem4.context2 = "事实上大多数";
//        commentItem4.Userid = "4";
//        commentItem4.Username = "焦点";
//        dynamic2.pinlun.add(commentItem4);
//        DataManager.Dynamic.CommentItem commentItem24 = new DataManager.Dynamic.CommentItem();
//        commentItem24.context2 = "撒打算广泛大锅饭说过的";
//        commentItem24.Userid = "5";
//        commentItem24.Username = "党建";
//        dynamic2.pinlun.add(commentItem24);
//        dynamic2.photos.add("http://img5.imgtn.bdimg.com/it/u=1315034029,2947494580&fm=21&gp=0.jpg");
//        dynamic2.photos.add("http://img2.imgtn.bdimg.com/it/u=798299207,518299881&fm=21&gp=0.jpg");
//        dynamic2.photos.add("http://img3.imgtn.bdimg.com/it/u=4151314378,285163215&fm=21&gp=0.jpg");
//        dynamic1.photos.add("http://img2.imgtn.bdimg.com/it/u=2033262106,335703526&fm=21&gp=0.jpg");
//        dynamic1.photos.add("http://img0.imgtn.bdimg.com/it/u=3903822374,3422085572&fm=21&gp=0.jpg");
//        dynamic1.photos.add("http://img4.imgtn.bdimg.com/it/u=1190416981,1869094977&fm=21&gp=0.jpg");
//        list_dy.add(dynamic2);
//
//        DataManager.Dynamic dynamic3 = new DataManager.Dynamic();
//        dynamic3.context1 = "北京南京美女美女，慢慢来啊啊飒飒v了肯定v了可是vi两句啊大家啊啊飒飒v了肯定v了可是vi两句啊大家啊啊飒飒v了肯定v了可是vi两句啊大家啊啊飒飒v了肯定v了可是vi两句啊大家啊啊飒飒v了肯定v了可是vi两句啊大家啊啊飒飒v了肯定v了可是vi两句啊大家";
//        dynamic3.iv = R.mipmap.banner;
//        dynamic3.time1 = "昨天 14:35";
//        dynamic3.zanshu = 10;
//        dynamic3.Dtid = "4";
//        dynamic3.Dtname = "哈哈哈";
//        list_dy.add(dynamic3);
//
//
//        DataManager.Dynamic dynamic4 = new DataManager.Dynamic();
//        dynamic4.context1 = "啊啊飒飒v了肯定v了可是vi两句啊大家";
//        dynamic4.iv = R.drawable.ceshi_item1;
//        dynamic4.time1 = "昨天 10:30";
//        dynamic4.zanshu = 10;
//        dynamic4.Dtid = "5";
//        dynamic4.Dtname = "呵呵";
//        dynamic4.photos.add("http://img3.imgtn.bdimg.com/it/u=4151314378,285163215&fm=21&gp=0.jpg");
//        dynamic4.photos.add("http://img2.imgtn.bdimg.com/it/u=2033262106,335703526&fm=21&gp=0.jpg");
//        dynamic4.photos.add("http://img3.imgtn.bdimg.com/it/u=4151314378,285163215&fm=21&gp=0.jpg");
//        dynamic4.photos.add("http://img2.imgtn.bdimg.com/it/u=2033262106,335703526&fm=21&gp=0.jpg");
//        list_dy.add(dynamic4);
//
//        DataManager.Dynamic dynamic5 = new DataManager.Dynamic();
//        dynamic5.context1 = "你你那安安啊飒飒是密码锁密码锁的，啊";
//        dynamic5.iv = R.mipmap.banner;
//        dynamic5.time1 = "昨天 13:10";
//        dynamic5.zanshu = 10;
//        dynamic5.Dtid = "6";
//        dynamic5.Dtname = "广告君";
//        dynamic5.photos.add("http://img5.imgtn.bdimg.com/it/u=1899617423,3475112956&fm=21&gp=0.jpg");
//        dynamic5.photos.add("http://img5.imgtn.bdimg.com/it/u=1271087648,2401885005&fm=11&gp=0.jpg");
//        dynamic5.photos.add("http://img5.imgtn.bdimg.com/it/u=1899617423,3475112956&fm=21&gp=0.jpg");
//        dynamic5.photos.add("http://img2.imgtn.bdimg.com/it/u=2881951061,450106641&fm=21&gp=0.jpg");
//        dynamic5.photos.add("http://img4.imgtn.bdimg.com/it/u=3722775870,2809264964&fm=21&gp=0.jpg");
//        list_dy.add(dynamic5);
//
//        layoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(layoutManager);//创建默认的线性LayoutManager
//        recyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
//        adapters = new DynamicAdapters(getActivity(), list_dy);
//        recyclerView.setAdapter(adapters);
//
//
//        //下拉刷新
//        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        recyclerView.setRefreshing(false);
//                    }
//                }, 2000);
//            }
//        });
//        //自动加载
//        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                Glide.with(getActivity()).resumeRequests();//图片加载重启
//
//
//
//            }
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if(newState != RecyclerView.SCROLL_STATE_IDLE){
//                    Glide.with(getActivity()).pauseRequests();//图片加载暂停
//                }
//
//            }
//        });
//
//    }


}
