package com.example.a25908.partybuild.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a25908.partybuild.Adapters.SupportAdapter;
import com.example.a25908.partybuild.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 党员扶持
 * @author weixuan
 */
public class SupportActivity extends BaseActivity {
    @ViewInject(R.id.returnT)
    private ImageView back;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.list_sa)
    private ListView list_sa;
    private List<Map> list;
    private SupportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        ViewUtils.inject(this);
        title.setText("党员扶持");
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        init();

    }

    private void init() {
        list = new ArrayList<>();

        Map<String,String> map = new HashMap<>();
        map.put("time","08-30 13:24");
        map.put("tx","扶持党员提高“双带”能力");
        map.put("tx2","党的群众路线教育实践活动开展以来，景谷傣族彝族自治县加大力度创建基层服务型党组织，注重提高农村党员“带头致富”和“带领致富”的能力，不断增强党组织的凝聚力和战斗力。全面开展“产业扶持党员示范户”活动，通过服务村民党员带头致富，从而以点带面，不断发展，带领更多的群众走上致富路。");
        list.add(map);

        Map<String,String> map2 = new HashMap<>();
        map2.put("time","08-30 13:24");
        map2.put("tx","党员产业大户创业扶持党建项目实施方案");
        map2.put("tx2","按照《**县基层党建工作项目化管理实施方案》和《2016年县级重点党建项目实施方案》（**组发〔2012〕23号、**组发〔2016〕29号）的总体要求，结合工作实际，现就全乡9村组织实施的党员产业大户创业扶持党建项目制定如下实施方案。\n" +
                "一、指导思想 \n" +
                "坚持以党的十八大、十八届三中、四中、五中全会及习近平总书记系列重要讲话精神为指导，认真贯彻落实全乡经济工作暨扶贫开发工作会议精神，紧扣全乡牛、果主导产业发展，以技术培训和帮扶帮带为主要抓手，加大帮扶力度，引导农村党员产业大户做大做强主导产业，形成党组织引领、党员产业大户带动、群众积极参与的产业发展新格局，辐射带动全乡主导产业壮大增效。\n" +
                "二、目标要求 \n" +
                "通过项目实施，不断凝聚全乡各级党组织的凝聚力、感召力和服务基层基础、推动经济发展、共建共享和谐的战斗力，党建引领精准扶贫、助推全面建成小康社会作用更加显现，使农村党员创业致富和服务发展意识不断提升，全乡牛、果主导产业发展的水平进一步提升，农村经济社会转型跨越、科学发展的步伐不断加快。\n" +
                "三、基本条件 \n" +
                "（一）产业现状要求\n" +
                "1.牛产业方面：牛存栏稳定在5头以上且每年都能以养牛为核心产业，通过养牛发\n" +
                "………………………此处隐藏部分文章内容…………………………………………\n" +
                "目。乡党委紧紧围绕健全机制，分项目积极探索制定切合实际、便于操作的验收评估办法，使党建项目验收评估有章可循、有理有据。年底将采取实地查看、入户调查等方式，组织对实施的党建项目逐个进行验收评估，并评选一定数量的优秀党建项目，积极落实以奖代补措施，充分调动村级党组织实施党建项目的积极性和主动性。");
        list.add(map2);

        Map<String,String> map23 = new HashMap<>();
        map23.put("time","08-30 13:24");
        map23.put("tx","“三严三实”专题教育 ");
        map23.put("tx2","在景谷镇景谷村纪家村民小组，党员致富带头人徐昌正通过建设养殖场，成立肉牛养殖专业合作社，去年收入达到了50多万元。“刚起步时，手头资金很是紧张。有几次饲料明显供应不上。在镇党委协调下，我们争取到了县委组织部扶持党员产业发展的1万元，并用这笔钱购买饲料，收购包谷秆。”他说，正是因为有了扶持党员产业发展资金的支持，合作社养殖业发展才得以壮大。");
        list.add(map23);

        Map<String,String> map25 = new HashMap<>();
        map25.put("time","08-30 13:24");
        map25.put("tx","我身边的抗战英雄”征集活动评选结果 ");
        map25.put("tx2","看到徐昌正家养牛有了成效，村子里的不少群众纷纷来学习，并加入到养牛的行列，先后带动48户农户养牛。随着村里养殖规模不断扩大，徐昌正牵头成立了景谷正宏肉牛养殖专业合作社，参与养牛的农户将育肥之后的牛出售给合作社。目前他正投资扩大养殖场建设，完工后可以养牛1000多头，预计合作社今年养牛的毛收入将达到200多万元。");
        list.add(map25);

        Map<String,String> map233 = new HashMap<>();
        map233.put("time","08-30 13:24");
        map233.put("tx","榜样——先进典型库 ");
        map233.put("tx2","“每家扶持3000元、5000元，最多的也就是1万元。钱虽然不多，但起到了‘点石成金’的作用。”景谷县委组织部部长苏有良说，为激励和帮助贫困党员在致富路上争做先锋，提高农村党员“双带”能力，景谷县以帮助党员致富为突破口，在全县开展“产业扶持党员示范户”活动，县财政每年投入50万元，以无息借贷方式支持农村党员发展产业。目前，县财政累计投入300万元，先后有800多名党员走上致富路。同时，该县还建立资金投入机制，制定并下发资金管理办法，把产业扶持资金纳入县财政预算，整合各乡(镇)、村级党组织投入资金，建立产业扶持循环基金，在规范借贷程序的同时抓作用发挥。");
        list.add(map233);

        Map<String,String> map2364 = new HashMap<>();
        map2364.put("time","08-30 13:24");
        map2364.put("tx","党的群众路线教育实践活动 ");
        map2364.put("tx2","党的组织生活是党的生活的重要内容，参加党的组织生活是每名党员的义务和责任。严格组织生活对于加强党员领导干部的教育管理监督，增强党员领导干部的党员意识、党的意识和党性观念具有重要意义。关于这个问题，看看习近平总书记是怎么说、怎么做的？");
        list.add(map2364);

        Map<String,String> map255 = new HashMap<>();
        map255.put("time","08-30 13:24");
        map255.put("tx","军委改进作风的实际成效，必须体现在加强自身建设");
        map255.put("tx2","军委改进作风的实际成效，必须体现在加强自身建设、履行职责使命上。当前，国防和军队建设已站在新的历史起点上，面临着难得的发展机遇，同时国际国内安全形势十分复杂，军事斗争准备任务十分繁重，军队建设内外环境变化十分深刻，风险和挑战明显增多。如何把军队各项建设不断向前推进，实现党在新形势下的强军目标，对军委的战略思维、领导水平、指挥能力、作风形象是一个历史性考验。");
        list.add(map255);
        adapter = new SupportAdapter(this,list);
        list_sa.setAdapter(adapter);
        list_sa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(SupportActivity.this,DetailsPageActivity.class));
            }
        });
    }
}
