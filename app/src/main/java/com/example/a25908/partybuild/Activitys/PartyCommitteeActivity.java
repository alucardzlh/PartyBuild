package com.example.a25908.partybuild.Activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a25908.partybuild.Adapters.PartyCommitteeAdpter;
import com.example.a25908.partybuild.Model.DataManager.PartyCommittee;
import com.example.a25908.partybuild.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 党委通知
 * @author weixuan
 */
public class PartyCommitteeActivity extends BaseActivity {
    @ViewInject(R.id.list_pc)
    private ListView list_pc;
    private List<PartyCommittee> list;
    private PartyCommitteeAdpter adpter;
    @ViewInject(R.id.returnT)
    private ImageView back;
    @ViewInject(R.id.title)
    private TextView title;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_committee);
        ViewUtils.inject(this);
        title.setText("党委通知");
        title.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        list = new ArrayList<>();
        addlist();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adpter = new PartyCommitteeAdpter(PartyCommitteeActivity.this,list);
        list_pc.setAdapter(adpter);

    }
    private void addlist(){
        PartyCommittee partyCommittee = new PartyCommittee();
        partyCommittee.title = "中建六局华南分公司：“检企共建”筑反腐防线";
        partyCommittee.context = "8月24日上午，中建六局华南分公司与海口市秀英区检察院“检企共建阳光工程”暨南海明珠大桥项目“陈超英廉洁文化示范点”创建启动仪式在南海明珠人工岛上举行。海口市秀英区人民检察院检察长傅铮、副检察长羊健民，中建六局华南分公司党委书记王志利、纪委书记杨永学出席，业主方、监理方及南海明珠大桥项目部分员工代表共计60余人参加。\n" +
                "　　启动仪式上，秀英区检察院与中建六局华南分公司签署了《共同开展职务犯罪预防协议书》，傅铮、王志利分别代表检企双方做了表态发言，并共同为“检企共建预防职务犯罪阳光工程”揭牌；南海明珠大桥项目党支部负责人宣读了项目创建“陈超英廉洁文化示范点”实施方案，并向公司纪委呈递了项目部关键岗位员工廉洁从业承诺书，傅铮、王志利向项目部赠送了预防职务犯罪和企业廉洁文化相关书籍，杨永学为南海明珠大桥项目部创建“陈超英廉洁文化示范点”授牌。\n" +
                "　　作为中建六局华南分公司“纪检监察学习教育季”活动的重要组成部分，此次“检企共建”暨“陈超英廉洁文化示范点”创建活动旨在进一步探索企业惩治和预防腐败体系建设新模式，持续有力推进党风廉政建设和反腐败工作常抓不懈，着力打造“忠诚、干净、担当”的干部员工队伍，努力营造风清气正、廉洁高效的从业氛围，为企业健康发展提供坚实的作风保障和纪律保障。（中建六局华南分公司党建云平台供稿）\n" +
                "　　（责任编辑：王仁锋）";
        partyCommittee.browse = "50";
        partyCommittee.time = "08-29 15:22";
        list.add(partyCommittee);

        PartyCommittee partyCommittee1 = new PartyCommittee();
        partyCommittee1.title = "云南凤庆：“3+2”模式推进党建工作有效覆盖";
        partyCommittee1.context = "“三组建”推进社会组织党的组织有效覆盖。一是按单位建立党组织。凡有3名以上正式党员的，100%建立党组织，并按期进行换届。二是按行业或区域建立党组织。行业特征明显、管理体系健全的行业，依托业务主管单位或行业协会商会建立行业党组织，行业党组织对会员单位党建工作进行指导。三是实现全领域覆盖。扎实开展社会组织党组织覆盖提升行动，正式党员2名的，通过招聘党员员工等方式100%建立党组织；正式党员1名的，按照就近就便原则，联合挂靠100%建立党组织；暂不具备组建条件的，建立工会、共青团、妇委会等组织开展党建工作，条件成熟时及时建立党组织。\n" +
                "　　“两选派”推进社会组织党的工作有效覆盖。一是选派党建联络员抓组建。选派党建联络员到社会组织挂联指导党建工作，原则上1名党建联络员挂联指导1个社会组织，将党组织关系转入县社会组织党委，整合党建联络员建立联合党支部，做好入党积极分子培养、发展党员和党员教育等工作。二是选派党建指导员促规范提升。选派政治素质好、熟悉党建工作、组织协调能力强、善于做群众工作的人员到社会组织担任党建指导员，制定党建工作计划，健全各项制度，提升党建工作整体水平。（作者单位：云南省临沧市凤庆县委办公室）\n" +
                "　　（责任编辑：郭慧）";
        partyCommittee1.browse = "70";
        partyCommittee1.time = "08-29 16:00";
        list.add(partyCommittee1);

        PartyCommittee partyCommittee2 = new PartyCommittee();
        partyCommittee2.title = "宁夏吴忠利通区：“四注重”加强党员教育管理";
        partyCommittee2.context = "今年以来，宁夏回族自治区吴忠市利通区以“两学一做”学习教育为龙头，从发展党员质量、增强党员理想信念教育、落实党员教育制度、发挥党员作用入手，严格党员日常教育管理，促进党员在脱贫攻坚和基层星级服务型党组织建设中走在前、作表率，发挥先锋模范作用。\n" +
                "　　注重发展党员工作质量。按照发展党员“十六字”方针，注重改善党员队伍结构，把质量作为发展党员的生命线，严格执行发展党员推荐、培训、预审、公示、票决、责任追究等制度，严把党员“入口关”。）";
        partyCommittee2.browse = "50";
        partyCommittee2.time = "08-29 13:22";
        list.add(partyCommittee2);

        PartyCommittee partyCommittee3 = new PartyCommittee();
        partyCommittee3.title = "陕西宜君县：“四措并举”提振干部干事创业精气神";
        partyCommittee3.context = "近期，陕西省委出台对干部鼓励激励、容错纠错、能上能下的“三项机制”，即《陕西省党政干部鼓励激励办法(试行)》《陕西省党政干部容错纠错办法(试行)》《陕西省党政干部能上能下办法(试行)》。宜君县委高度重视，迅速在全县进行安排部署，强化各级党委主体责任，层层传导压力，抓好学习贯彻，通过“四个结合”，确保“三项机制”在全县党员干部中落地生根。\n" +
                "　　与“两学一做”学习教育相结合。县委将学习贯彻“三项机制”作为“两学一做”学习教育的重要内容进行安排部署，动员全县党员干部入脑入心地学，深入领会精神实质。县委常委和组织部门先学一步，学深一层。县委书记刘冲第一时间在《铜川日报》发表署名文章《全面落实“三项机制” 凝聚提速转型发展合力》，谈认识、谈落实；全县各级党组织通过领导班子带头学、专题讨论重点学、“周三夜校”集中学、各支部“以考促学”等方式，组织党员干部进行学习，在全县迅速掀起学习“三项机制”精神热潮。\n" +
                "　　与年度目标责任半年考核相结合。为了切实在全县范围内抓好“三项机制”的学习贯彻工作，县委组织部专门下发《关于学习贯彻“三项机制”有关事项的通知》的文件，在对学习贯彻“三项机制”的相关工作进行安排部署外，将各级党组织贯彻落实“三";
        partyCommittee3.browse = "50";
        partyCommittee3.time = "08-29 18:22";
        list.add(partyCommittee3);

        PartyCommittee partyCommittee4 = new PartyCommittee();
        partyCommittee4.title = "西安交通大学：“三个强化”做好党建工作";
        partyCommittee4.context = "西安交通大学积极贯彻落实全面从严治党要求，通过强化顶层设计、强化指导监督、丰富活动载体等方式，扎实推进基层党组织建设。\n" +
                "　　强化党委领导，夯实工作基础。将党建工作放在总揽学校全局的战略地位抓，严格落实党建工作责任制，坚持将党建工作与学校中心工作同谋划、同部署、同落实。校党委以上率下，近两年共43次常委会专题传达中央和省委文件，研究党建工作的内容达47项，召开8次民主生活会，实现民主生活会常态化。制定《明规守纪手册》《党委委员工作守则》《纪委委员履职守则》，提出“约法十则”“用权十要”“十要干”，增强党员干部自律品格、纪律意识和规矩意识。创新优化党组织设置，探索高校党的基层组织与科研管理体制创新相结合的基层党建新模式，在前沿科学技术研究院等教学科研团队成立党组织，将大批高端人才凝聚于党组织周围。坚持选优配强各级党组织带头人，选配政治素质高、业务经验丰富、工作实绩突出的党员担任分党委、党支部负责人，组织开展分党委书记例会制度，开设“党支部书记研讨班”、“新任党支部书记培训班”，提高理论水平和工作能力。";
        partyCommittee.browse = "10";
        partyCommittee.time = "08-29 19:22";
        list.add(partyCommittee4);

        PartyCommittee partyCommittee5 = new PartyCommittee();
        partyCommittee5.title = "云南省南涧县在抓非公经济组织和社会组织党建工作中，按照“无组织抓组建、有组织抓规范、已规范抓示范”三步走的工作思路，循序渐进，极力提高“两新”组织的服务能力。\n" +
                "　　无组织抓组建。探索“企业独建，园区联建，行业统建，协合双建”的模式，在条件成熟的银溪生态茶业有限公司等22个企业中，单独建立党支部；条件不成熟的得胜工业园区、安定工业园区，将进驻企业的党员按区域分布统一合并到各个支部；分布在各类协会和专业合作社的党员，由牵头单位统一建立支部。目前全县的“两新”党组织有56个，非公经济组织党组织覆盖率达81.1%，社会组织党组织覆盖率达81.6%。\n" +
                "　　有组织抓规范。结合“两学一做”集中整治，对照“五个突出问题”，对一个软弱涣散非公经济党支部进行了整顿，调整充实了领导班子，县市场监管局派出党建指导员，配好党务干部，并督促其严格执行“三会一课”制度，开展党建工作座谈会、交流会、外出考察学习等活动，逐步实现组织活动正常化、规范化。";
        partyCommittee5.browse = "90";
        partyCommittee5.time = "08-29 20:22";
        list.add(partyCommittee5);
    }



}
