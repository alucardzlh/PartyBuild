package com.example.a25908.partybuild.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a25908.partybuild.Activitys.OrganizationalActivity;
import com.example.a25908.partybuild.Activitys.PartymembersdetailsActivity;
import com.example.a25908.partybuild.Contacts.PinYinKit;
import com.example.a25908.partybuild.Contacts.PinyinComparator;
import com.example.a25908.partybuild.Contacts.SearchEditText;
import com.example.a25908.partybuild.Contacts.SideBar;
import com.example.a25908.partybuild.Contacts.SortAdapter;
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

import static com.example.a25908.partybuild.Utils.URLconstant.PARTYRTLISTURL;
import static com.example.a25908.partybuild.Utils.URLconstant.URLINSER;

/**
 * @author
 * 党员名册
 */
public class Fragment2 extends Fragment  {
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


    public static SearchEditText mSearchEditText;

    List<SortModel> sortModelList=new ArrayList<>();
    LinearLayout zzjg;

    public static Handler handler;
    PartySharePreferences psp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment2, container, false);
        ViewUtils.inject(getActivity());
        psp=PartySharePreferences.getLifeSharedPreferences();
        mSearchEditText = (SearchEditText) v.findViewById(R.id.txt_filter_edit);
        zzjg= (LinearLayout) v.findViewById(R.id.zzjg);
        userListNumTxt= (TextView) v.findViewById(R.id.txt_user_list_user_num);
        sideBar= (SideBar) v.findViewById(R.id.sild_bar);
        sortListView= (ListView) v.findViewById(R.id.list_view_user_list);
        dialogTxt= (TextView) v.findViewById(R.id.txt_dialog);
        userListNumTxt= (TextView) v.findViewById(R.id.txt_user_list_user_num);

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
                                        Toast.makeText(getActivity(), ((SortModel) adapter.getItem(position)).name, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getActivity(), PartymembersdetailsActivity.class).putExtra("name", ((SortModel) adapter.getItem(position)).name));
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
                                            if(mSearchEditText.getText().toString().equals("")){
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
                    case 1:
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
}





