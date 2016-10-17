package com.example.a25908.partybuild.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a25908.partybuild.Activitys.OrganizationalActivity;
import com.example.a25908.partybuild.Activitys.PartymembersdetailsActivity;
import com.example.a25908.partybuild.Adapters.ParentAdapter;
import com.example.a25908.partybuild.Contacts.PinYinKit;
import com.example.a25908.partybuild.Contacts.PinyinComparator;
import com.example.a25908.partybuild.Contacts.SearchEditText;
import com.example.a25908.partybuild.Contacts.SideBar;
import com.example.a25908.partybuild.Contacts.SortAdapter;
import com.example.a25908.partybuild.Dialogs.WaitDialog;
import com.example.a25908.partybuild.Http.GsonCallBack;
import com.example.a25908.partybuild.Http.GsonRequest;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.Model.SortModel;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Services.CallServer;
import com.example.a25908.partybuild.Utils.MD5;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.lidroid.xutils.ViewUtils;
import com.yolanda.nohttp.RequestMethod;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.a25908.partybuild.R.id.sild_bar;
import static com.example.a25908.partybuild.Utils.URLconstant.PARTYRTLISTURL;
import static com.example.a25908.partybuild.Utils.URLconstant.PARTYRTLISTURL2;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;

/**
 * @author
 * 党员名册
 */
public class Fragment2 extends Fragment implements ExpandableListView.OnGroupExpandListener, ParentAdapter.OnChildTreeViewClickListener {
    public PinyinComparator comparator = new PinyinComparator();
    //    @ViewInject(R.id.txt_user_list_user_num)
    private TextView userListNumTxt;

    private String userListNumStr;

    //    @ViewInject(R.id.sild_bar)
    private SideBar sideBar;

    //    @ViewInject(R.id.list_view_user_list)
    private ListView sortListView;

    //    @ViewInject(R.id.txt_dialog)
    private TextView dialogTxt;

    //    private SearchEditText mSearchEditText;
    private SortAdapter adapter;

    private TextView text;

    private View headerView;
    private View headerView2;

    private ExpandableListView eList;

    public static SearchEditText mSearchEditText;

    List<SortModel> sortModelList=new ArrayList<>();
    LinearLayout zzjg;

    public static Handler handler;
    PartySharePreferences psp;
    WaitDialog waitDialog;
    private LinearLayout lin_js;

    boolean cx=false;

    private ParentAdapter adapter2;//ExpandableListView的适配器
    private List<DataManager.ZZplayer.DataBean.UserlistPageBean> parents;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment2, container, false);
        ViewUtils.inject(getActivity());
        waitDialog = new WaitDialog(getActivity());
        psp=PartySharePreferences.getLifeSharedPreferences();
        mSearchEditText = (SearchEditText) v.findViewById(R.id.txt_filter_edit);
        lin_js = (LinearLayout) v.findViewById(R.id.lin_js);
        zzjg= (LinearLayout) v.findViewById(R.id.zzjg);
        userListNumTxt= (TextView) v.findViewById(R.id.txt_user_list_user_num);
        sideBar= (SideBar) v.findViewById(sild_bar);
        sortListView= (ListView) v.findViewById(R.id.list_view_user_list);
        dialogTxt= (TextView) v.findViewById(R.id.txt_dialog);
        userListNumTxt= (TextView) v.findViewById(R.id.txt_user_list_user_num);
        eList = (ExpandableListView) v.findViewById(R.id.expandable_list);

        //模糊查询
        headerView2 = LayoutInflater.from(getActivity()).inflate(R.layout.headview,null);
        text = (TextView) headerView2.findViewById(R.id.headview2);
        Drawable drawable= getResources().getDrawable(R.drawable.ic_search_black_24dp);
/// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        text.setCompoundDrawables(drawable,null,null,null);
        text.setText("模糊查询");
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sideBar.setVisibility(View.VISIBLE);
                lin_js.setVisibility(View.VISIBLE);
                eList.setVisibility(View.GONE);
                sortListView.setVisibility(View.VISIBLE);
            }
        });
        //结构查询
        headerView = LayoutInflater.from(getActivity()).inflate(R.layout.headview,null);
        text = (TextView) headerView.findViewById(R.id.headview2);
        Drawable drawable2 = getResources().getDrawable(R.mipmap.zuzhichaxun);
/// 这一步必须要做,否则不会显示.
        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
        text.setCompoundDrawables(drawable2,null,null,null);
        text.setText("结构查询");
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cx){
                    sideBar.setVisibility(View.GONE);
                    lin_js.setVisibility(View.GONE);
                    eList.setVisibility(View.VISIBLE);
                    sortListView.setVisibility(View.GONE);

                }else {
                    waitDialog.show();
                    GsonRequest Request = new GsonRequest(URLINSER +PARTYRTLISTURL2, RequestMethod.POST);
                    Request.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                    Request.add("KeyNo", psp.getUSERID());
                    Request.add("deviceId", new Build().MODEL);
                    CallServer.getInstance().add(getActivity(), Request, GsonCallBack.getInstance(), 0x00323, true, false, true);
                }

            }
        });

        try{
            if(DataManager.PartyerList.data.UserlistPage.size()>0 && DataManager.PartyerList.data.UserlistPage!=null){
                handler.sendEmptyMessage(0);
            }else{
                GsonRequest Request = new GsonRequest(URLINSER +PARTYRTLISTURL, RequestMethod.GET);
                Request.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
                Request.add("KeyNo", psp.getUSERID());
                Request.add("deviceId", new Build().MODEL);
                Request.add("udid", "");
                CallServer.getInstance().add(getActivity(), Request, GsonCallBack.getInstance(), 0x003, true, false, true);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            GsonRequest Request = new GsonRequest(URLINSER +PARTYRTLISTURL, RequestMethod.POST);
            Request.add("token", MD5.MD5s(psp.getUSERID() + new Build().MODEL));
            Request.add("KeyNo", psp.getUSERID());
            Request.add("udid", "");
            Request.add("deviceId", new Build().MODEL);
            CallServer.getInstance().add(getActivity(), Request, GsonCallBack.getInstance(), 0x003, true, false, true);
        }
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        try{
                            if(DataManager.PartyerList.data.UserlistPage.size()>0 && DataManager.PartyerList.data.UserlistPage!=null) {
                                sideBar.setmTextDialog(dialogTxt);
                                // on touching listener of side bar
                                sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener()
                                {

                                    public void onTouchingLetterChanged(String str)
                                    {
                                        int position =  adapter.getPositionForSection(str.charAt(0));
                                        if (position != -1)
                                            sortListView.setSelection(position);
                                    }
                                });

                                final int size = DataManager.PartyerList.data.UserlistPage.size();
                                //        姓名集合
                                String[] str = new String[size];
                                //        头像集合
                                String [] img = new String[size];

                                for (int i = 0; i < size; i++) {
                                    str[i] = DataManager.PartyerList.data.UserlistPage.get(i).USERNAME;
                                    img[i] = DataManager.PartyerList.data.UserlistPage.get(i).HEAD_IMG;
                                }

                                sortListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
                                sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                                            long id) {
                                        //position 如果有headview  position =0 的第一个为headview
                                        Toast.makeText(getActivity(), ((SortModel) adapter.getItem(position-1)).name, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getActivity(), PartymembersdetailsActivity.class).putExtra("name", ((SortModel) adapter.getItem(position-1)).name));
                                    }
                                });


                                // call filledData to get datas
                                try {
                                    sortModelList = filledData(str, img);
                                } catch (BadHanyuPinyinOutputFormatCombination e1) {
                                    e1.printStackTrace();
                                }

                                userListNumTxt.setText("全部：" + "\t" + sortModelList.size() + "个联系人");

                                // sort by a-z
                                Collections.sort(sortModelList, comparator);
                                sortListView.addHeaderView(headerView);
                                adapter = new SortAdapter(getActivity(), sortModelList);
                                sortListView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                // search
//        mSearchEditText = (SearchEditText) .findViewById(R.id.txt_filter_edit);
                                // filter
                                mSearchEditText.addTextChangedListener(new TextWatcher()
                                {

                                    public void onTextChanged(CharSequence str, int arg1, int arg2, int arg3)
                                    {
                                        try{
                                            filerData(str.toString());
                                            if(TextUtils.isEmpty(mSearchEditText.getText().toString())){
                                                zzjg.setVisibility(View.VISIBLE);
                                            }else{
                                                zzjg.setVisibility(View.GONE);
                                            }
                                        } catch (BadHanyuPinyinOutputFormatCombination e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                                  int arg3) {
                                    }

                                    public void afterTextChanged(Editable arg0)
                                    {
                                    }
                                });
                            }
                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }
                        break;
                    case 1://组织查询
                        waitDialog.dismiss();
                        loadData();
                        initEList();
                        break;
                }
            }
        };
        zzjg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(getActivity(), OrganizationalActivity.class));
            }
        });


        return v;
    }

    private List<SortModel> filledData(String [] date,String [] img) throws BadHanyuPinyinOutputFormatCombination {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for(int i=0; i<date.length; i++){
            SortModel sortModel=new SortModel();
            sortModel.name=(date[i]);
            //汉字转换成拼音
            String pinyin = PinYinKit.getPingYin(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")){
                sortModel.sortLetters=(sortString.toUpperCase());
            }else{
                sortModel.sortLetters=("#");
            }
            sortModel.img=(img[i]);
            mSortList.add(sortModel);
        }
        return mSortList;

    }

    private void filerData(String str) throws BadHanyuPinyinOutputFormatCombination
    {
        List<SortModel> fSortModels = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(str))
            fSortModels = sortModelList;
        else
        {
            fSortModels.clear();
            for (SortModel sortModel : sortModelList)
            {
                String name = sortModel.name;
                if (name.indexOf(str.toString()) != -1 ||
                        PinYinKit.getPingYin(name).startsWith(str.toString()) || PinYinKit.getPingYin(name).startsWith(str.toUpperCase().toString()))
                {
                    fSortModels.add(sortModel);
                }
            }

        }
        Collections.sort(fSortModels, comparator);
        adapter.updateListView(fSortModels);
    }

    public void changeDatas(List<SortModel> mSortList , String str)
    {
        userListNumTxt.setText(str+":"+"\t"+mSortList.size()+"个联系人");

        Collections.sort(mSortList, comparator);
        adapter.updateListView(mSortList);
    }


    /**
     * @author weixaun
     *
     *         初始化菜单数据源
     * */
    private void loadData() {

        parents = new ArrayList<DataManager.ZZplayer.DataBean.UserlistPageBean>();
        parents = DataManager.zzPlayerList.data.UserlistPage;
    }

    /**
     * @author weixuan
     *
     *         初始化ExpandableListView
     * */
    private void initEList() {
        eList.setOnGroupExpandListener(this);

        adapter2 = new ParentAdapter(getActivity(), parents);
        eList.addHeaderView(headerView2);
        eList.setAdapter(adapter2);

        adapter2.setOnChildTreeViewClickListener(this);

        sideBar.setVisibility(View.GONE);
        lin_js.setVisibility(View.GONE);
        eList.setVisibility(View.VISIBLE);
        sortListView.setVisibility(View.GONE);
        cx = true;
    }
    /**
     * @author weixuan
     *
     *         点击子ExpandableListView的子项时，回调本方法，根据下标获取值来做相应的操作
     * */
    @Override
    public void onClickPosition(int parentPosition, int groupPosition,
                                int childPosition) {
        // do something
//        String childName = parents.get(parentPosition).Departmentlist
//                .get(groupPosition).Userlist.get(childPosition)
//                .toString();
//        Toast.makeText(
//                getActivity(),
//                "点击的下标为： parentPosition=" + parentPosition
//                        + "   groupPosition=" + groupPosition
//                        + "   childPosition=" + childPosition + "\n点击的是："
//                        + childName, Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), DataManager.zzPlayerList.data.UserlistPage.get(parentPosition).Departmentlist.get(groupPosition).Userlist.get(childPosition).USERNAME, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), PartymembersdetailsActivity.class).putExtra("name", DataManager.zzPlayerList.data.UserlistPage.get(parentPosition).Departmentlist.get(groupPosition).Userlist.get(childPosition).USERNAME));
    }

    /**
     * @author weixaun
     *
     *         展开一项，关闭其他项，保证每次只能展开一项
     * */
    @Override
    public void onGroupExpand(int groupPosition) {
        for (int i = 0; i < parents.size(); i++) {
            if (i != groupPosition) {
                eList.collapseGroup(i);
            }
        }
    }
}





