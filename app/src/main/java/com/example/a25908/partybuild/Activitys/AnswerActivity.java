package com.example.a25908.partybuild.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a25908.partybuild.Adapters.AnswerAdapter;
import com.example.a25908.partybuild.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 在线答疑
 * @author weixuan
 */
public class AnswerActivity extends BaseActivity {
    @ViewInject(R.id.list_an)
    private ListView listView;
    @ViewInject(R.id.questions_aa)
    private LinearLayout questions;
    @ViewInject(R.id.returnT)
    private ImageView back;
    @ViewInject(R.id.title)
    private TextView title;
    private List<Map> list;
    private AnswerAdapter answerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        ViewUtils.inject(this);
        title.setText("在线答疑");
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        init();

        questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AnswerActivity.this,QuestionsActivity.class));
            }
        });

    }

    private void init() {
        list = new ArrayList<>();

        Map<String,String> map4 = new HashMap<>();
        map4.put("wen","挺意外！特朗普见墨总统 为讲和还为碰硬?");
        map4.put("time","08-30 14:22");
        list.add(map4);

        Map<String,String> map = new HashMap<>();
        map.put("wen","解决党内存在的问题，根本在于什么，使每个党员都能及时纳入管理?");
        map.put("da","远大理想、最终目标与共同理想、具体目标是统一的。远大理想、最终目标为共同理想、具体目标规定着前进的方向，我们党之所以能够经受一次次挫折而又一次次奋起，归根到底是因为我们党有远大理想和崇高追求。而共同理想、具体目标则是远大理想、最终目标在当前的体现，是实现共产主义漫长的历史过程中必须经历的里程碑。实现了这些具体目标，才能实现最终目标。我们必须增强初心意识，做共产主义远大理想和中国特色社会主义共同理想的坚定信仰者和忠实践行者。");
        map.put("time","08-30 14:22");
        list.add(map);

        Map<String,String> map5 = new HashMap<>();
        map5.put("wen","面对困难和挑战，我们如何前进?");
        map5.put("da","当前，中国共产党治国理政面临的问题是以往从来没有的，也是广大干部群众普遍关注的深层次问题，马克思主义当中没有现成的答案，更不可能从西方的话语体系中寻找答案，只能靠我们自己。要坚持把马克思主义基本原理同当代中国实际和时代特点紧密结合起来，推进理论创新、实践创新，不断把马克思主义中国化推向前进。这是中国共产党取得一切成就的根本所在。");
        map5.put("time","08-30 14:22");
        list.add(map5);

        Map<String,String> map3 = new HashMap<>();
        map3.put("wen","挺意外！特朗普见墨总统 为讲和还为碰硬?");
        map3.put("da","　美国共和党总统候选人唐纳德·特朗普8月30日晚证实，他定于次日应邀访问墨西哥，会见墨西哥总统。\n" +
                "　　对特朗普的墨西哥之行，就连一些墨西哥政府官员也感到意外。因特朗普曾发表墨西哥移民大多是“毒贩”“强奸犯”等不友好言论，他会见墨政府官员时是否还会保持这种论调备受外界关注。");
        map3.put("time","08-30 14:22");
        list.add(map3);


        answerAdapter = new AnswerAdapter(this,list);
        listView.setAdapter(answerAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(AnswerActivity.this,AnswerDetailsActivity.class));
            }
        });

    }
}
