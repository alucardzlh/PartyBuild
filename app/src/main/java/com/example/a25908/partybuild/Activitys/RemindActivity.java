package com.example.a25908.partybuild.Activitys;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a25908.partybuild.Contacts.PinYinKit;
import com.example.a25908.partybuild.Contacts.PinyinComparator;
import com.example.a25908.partybuild.Contacts.SearchEditText;
import com.example.a25908.partybuild.Contacts.SideBar;
import com.example.a25908.partybuild.Contacts.SortAdapter2;
import com.example.a25908.partybuild.Dialogs.WaitDialog;
import com.example.a25908.partybuild.Model.DataManager;
import com.example.a25908.partybuild.Model.SortModel;
import com.example.a25908.partybuild.R;
import com.example.a25908.partybuild.Utils.PartySharePreferences;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 提醒谁看
 * @author weixuan
 */
public class RemindActivity extends BaseActivity  {
    public PinyinComparator comparator = new PinyinComparator();
    @ViewInject(R.id.returnT)
    ImageView returnT;
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.fileclear)
    TextView fileclear;
    @ViewInject(R.id.sild_bar)
    private SideBar sideBar;
    @ViewInject(R.id.list_view_user_list)
    private ListView sortListView;
    @ViewInject(R.id.txt_user_list_user_num)
    private TextView userListNumTxt;
    @ViewInject(R.id.txt_dialog)
    private TextView dialogTxt;
    private List<String> liststr;
    @ViewInject(R.id.renming)
    private TextView renming;

    public static Handler handler;
    PartySharePreferences psp;
    WaitDialog waitDialog;
    List<SortModel> sortModelList=new ArrayList<>();
    @ViewInject(R.id.txt_filter_edit)
    public static SearchEditText mSearchEditText;
    private SortAdapter2 adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind);
        ViewUtils.inject(this);
        liststr = new ArrayList<>();
        fileclear.setText("确定(0/10)");
        fileclear.setVisibility(View.VISIBLE);
        waitDialog = new WaitDialog(this);
        psp = new PartySharePreferences();
        title.setText("提醒谁看");
        returnT.setVisibility(View.VISIBLE);
        returnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fileclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

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
//                                        Toast.makeText(RemindActivity.this, ((SortModel) adapter.getItem(position)).name, Toast.LENGTH_SHORT).show();
//                                        startActivity(new Intent(RemindActivity.this, PartymembersdetailsActivity.class).putExtra("name", ((SortModel) adapter.getItem(position)).name));
//                        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.pay_cb);
//                        LinearLayout layout = (LinearLayout) view.findViewById(R.id.lin_tx);
//                        layout.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                if (liststr.size()>=10){
//                                    checkBox.setClickable(false);
//                                    checkBox.setEnabled(false);
//
//                                }
//                                else {
//                                    checkBox.setClickable(true);
//                                    checkBox.setEnabled(true);
//                                }
//                            }
//                        });


//                        adapter.setSaveCB(checkBox.isChecked(),position);

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
                adapter = new SortAdapter2(RemindActivity.this, sortModelList);
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
//                                                zzjg.setVisibility(View.VISIBLE);
                            }else{
//                                                zzjg.setVisibility(View.GONE);
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
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        adapter.setkaiguan(adapter.getBody2());
                        break;
                    case 1:
                        if (liststr.size()!=0){
                            for (int i = 0; i < liststr.size(); i++){
                                if(liststr.get(i).equals(sortModelList.get(adapter.getBody2()).name)){
                                    liststr.remove(i);
                                    break;
                                }
                                else if (liststr.size()-1==i){
                                    liststr.add(sortModelList.get(adapter.getBody2()).name);
                                    break;
                                }
                            }
                        }
                        else {
                            liststr.add(sortModelList.get(adapter.getBody2()).name);
                        }
                        if (liststr.size()>9){
                            adapter.setBl(true);
                        }else {
                            adapter.setBl(false);
                        }
                        fileclear.setText("确定("+liststr.size()+"/10)");
                        renming.setText(liststr.toString());
                        break;
                }
            }
        };



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
