package com.example.a25908.partybuild.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a25908.partybuild.Adapters.DynamicAdapters;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.R;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weixuan
 * 党群动态
 */
public class Fragment3 extends Fragment {
	private SuperRecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private DynamicAdapters adapters;
    private List<DataManager.Dynamic> list;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.activity_fragment3, container,false);
        recyclerView = (SuperRecyclerView) v.findViewById(R.id.recyclerView);
        recyclerViewInit();

		return v;
	}

    /**
     * 初始化RecyclerView
     */
    private void recyclerViewInit(){
        list = new ArrayList<>();
        addlist();


    }

    /**
     * 添加数据
     */
    private void addlist(){
        DataManager.Dynamic dynamic = new DataManager.Dynamic();
        dynamic.context1 = "阿斯顿撒撒反对的地方撒发达方式表现出不屑的发生的v大师傅但是";
        dynamic.iv = R.drawable.ceshi_item1;
        dynamic.time1 = "今天 14:20";
        dynamic.zanshu = 14;
        dynamic.name = "好榜样";
        DataManager.Dynamic.CommentItem commentItem = new DataManager.Dynamic.CommentItem();
        commentItem.setContent("哈哈哈哈");
        commentItem.setUser("哈哈哈");
        dynamic.pinlun.add(commentItem);
        dynamic.photos.add(R.drawable.ceshi_item1);
        dynamic.photos.add(R.mipmap.banner);
        dynamic.photos.add(R.mipmap.ic_launcher);
        list.add(dynamic);

        DataManager.Dynamic dynamic1 = new DataManager.Dynamic();
        dynamic1.context1 = "无为而让他同意而儿额头二人太热特热";
        dynamic1.iv = R.drawable.ceshi_item1;
        dynamic1.time1 = "今天 14:20";
        dynamic1.zanshu = 14;
        dynamic1.name = "好办样";
        DataManager.Dynamic.CommentItem commentItem1 = new DataManager.Dynamic.CommentItem();
        commentItem1.setContent("阿三打算打算打算哈");
        commentItem1.setUser("有钱人");
        dynamic1.pinlun.add(commentItem1);
        dynamic1.photos.add(R.drawable.ceshi_item1);
        dynamic1.photos.add(R.mipmap.banner);
        dynamic1.photos.add(R.mipmap.ic_launcher);
        dynamic1.photos.add(R.drawable.ceshi_item1);
        list.add(dynamic1);

        DataManager.Dynamic dynamic2 = new DataManager.Dynamic();
        dynamic2.context1 = "啊请问我的夫人有条件公公高考";
        dynamic2.iv = R.drawable.ceshi_item1;
        dynamic2.time1 = "今天 11:20";
        dynamic2.zanshu = 56;
        dynamic2.name = "19356";
        DataManager.Dynamic.CommentItem commentItem2 = new DataManager.Dynamic.CommentItem();
        commentItem2.setContent("应用于如若try也如统驭土语体育");
        commentItem2.setUser("呃呃呃");
        dynamic2.pinlun.add(commentItem2);
        DataManager.Dynamic.CommentItem commentItem4 = new DataManager.Dynamic.CommentItem();
        commentItem4.setContent("事实上大多数");
        commentItem4.setUser("啊啊啊");
        dynamic2.pinlun.add(commentItem4);
        DataManager.Dynamic.CommentItem commentItem24 = new DataManager.Dynamic.CommentItem();
        commentItem24.setContent("撒打算广泛大锅饭说过的");
        commentItem24.setUser("小星星");
        dynamic2.pinlun.add(commentItem24);
        dynamic2.photos.add(R.mipmap.ic_launcher);
        dynamic2.photos.add(R.mipmap.banner);
        dynamic2.photos.add(R.mipmap.ic_launcher);
        list.add(dynamic2);

        DataManager.Dynamic dynamic3 = new DataManager.Dynamic();
        dynamic3.context1 = "北京南京美女美女，慢慢来";
        dynamic3.iv = R.mipmap.banner;
        dynamic3.time1 = "昨天 14:35";
        dynamic3.zanshu = 10;
        dynamic3.name = "哈哈哈";
        list.add(dynamic3);
        DataManager.Dynamic dynamic4 = new DataManager.Dynamic();
        dynamic4.context1 = "啊啊飒飒v了肯定v了可是vi两句啊大家";
        dynamic4.iv = R.drawable.ceshi_item1;
        dynamic4.time1 = "昨天 10:30";
        dynamic4.zanshu = 10;
        dynamic4.name = "呵呵";
        dynamic4.photos.add(R.mipmap.ic_launcher);
        dynamic4.photos.add(R.drawable.ceshi_item1);
        list.add(dynamic4);
        DataManager.Dynamic dynamic5 = new DataManager.Dynamic();
        dynamic5.context1 = "你你那安安啊飒飒是密码锁密码锁的，啊";
        dynamic5.iv = R.mipmap.banner;
        dynamic5.time1 = "昨天 13:10";
        dynamic5.zanshu = 10;
        dynamic5.name = "广告君";
        dynamic5.photos.add(R.mipmap.banner);
        dynamic5.photos.add(R.mipmap.banner);
        list.add(dynamic5);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);//创建默认的线性LayoutManager
        recyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        adapters = new DynamicAdapters(getActivity(),list);
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
//                int lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
//                int totalItemCount = layoutManager.getItemCount();



            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                if(newState != RecyclerView.SCROLL_STATE_IDLE){
//                    Glide.with(getActivity()).pauseRequests();
//                }

            }
        });

    }


}
