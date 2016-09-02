package com.example.a25908.partybuild.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a25908.partybuild.Adapters.StudyListAdapter;
import com.example.a25908.partybuild.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yusi
 * 学习园地
 */
public class StudyActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    ImageView returnT;
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.listStudy)
    ListView listStudy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        ViewUtils.inject(this);
        title.setText("学习园地");
        returnT.setVisibility(View.VISIBLE);
        returnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
    }
    public void init(){//模拟数据
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        map1.put("img",R.mipmap.banner);
        map1.put("txt1","为国争光，振奋民族自信心");
        map1.put("txt2","里约奥运会已经落下帷幕，而以中国女排为代表的中国奥运军团展现的为国争光的拼搏奋斗精神，依然久久激荡在国人心头。当女排精神超越体育层面，成为鼓舞各族人民的精神力量；当“里约奥运会内地奥运精英代表团”访问香港，市民们在酒店门口高喊“中国女排”……女排精神，成为一种豪情的抒发、志气的高扬、自信的挥洒。");
        map1.put("time","12-05 18:25");

        Map<String,Object> map2 = new HashMap<>();
        map2.put("img",R.mipmap.banner);
        map2.put("txt1","人民日报民生观：别让诈骗“私人定制”");
        map2.put("txt2","　互联网上，我们不得不留下信息，但我可以留，你不可以漏，更不能非法买卖\t中了骗子圈套，大学生徐玉玉不幸离世。消息传开，人人痛惜。截至目前，涉案嫌疑人已经全部落网。人们刚有些宽慰，可是，又曝出了类似的“中招”悲剧。");
        map2.put("time","12-05 18:25");

        Map<String,Object> map3 = new HashMap<>();
        map3.put("img",R.mipmap.banner);
        map3.put("txt1","青岛市崂山区：“政治生日”让党员牢记身份");
        map3.put("txt2","“刘文东同志，今天是你的政治生日，统计局党支部向你表示祝贺！”打开手机，扫了扫二维码，刘文东进入了青岛市崂山区统计局党支部精心制作的党员政治生日电子贺卡页面。");
        map3.put("time","12-05 18:25");

        Map<String,Object> map4 = new HashMap<>();
        map4.put("img",R.mipmap.banner);
        map4.put("txt1","武夷山市星村镇：“七学法”强化分类指导抓实效");
        map4.put("txt2","武夷山市星村镇党委根据镇村党员涉及面广、文化水平较低、个体差异性大，且大多数党员处在农村基层生产一线等特点，在党员“两学一做”学习教育中，加强分类指导，注重提升党员的学习实效。");
        map4.put("time","12-05 18:25");

        Map<String,Object> map5 = new HashMap<>();
        map5.put("img",R.mipmap.banner);
        map5.put("txt1","陈云：共产党员的“六条标准”");
        map5.put("txt2","陈云同志发表于1939年5月的《怎样做一个共产党员》是马克思主义党建史上的一篇重要文献。在这篇著作中，陈云同志以马克思主义者求真务实的精神，提出了一名好的共产党员的“六条标准”。");
        map5.put("time","12-05 18:25");

        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        list.add(map5);
        StudyListAdapter adapter=new StudyListAdapter(StudyActivity.this,list);
        listStudy.setAdapter(adapter);
        listStudy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(StudyActivity.this,DetailsPageActivity.class));
            }
        });

    }
}
