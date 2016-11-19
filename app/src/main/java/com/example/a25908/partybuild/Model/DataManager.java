package com.example.a25908.partybuild.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class DataManager {

    public static User userlist = new User();

    /**
     * 用户信息
     *
     * @author yusi
     */
    public static class User {
        public String message;
        public String status;
        public DataBean data;
        public String version;

        public static class DataBean {
            public String username;
            public String email;
            public String head_img;
            public int userid;
            public String join_party;
            public String password;
        }
    }


    public static MyData MyDataList = new MyData();

    /**
     * 个人资料
     *
     * @author yusi
     */
    public static class MyData {
        public String message;
        public String status;
        public DataBean data;
        public String version;

        public static class DataBean {
            public PartyMemberlistBean partyMemberlist;

            public static class PartyMemberlistBean {
                //                public String position;//职务
//                public String birth;//出生年月
//                public int sex;//性别(0男1女)
//                public String phone;//电话
//                public String id_number;//身份证号
//                public String department;//部门
//                public int userid;//用户id
//                public String home_address;//现家庭住址
//                public String unit;//单位
//                public String username;//用户名称
//                public String census_register;//户籍
//                public String email;//邮箱
//                public String age;//年龄
//                public String head_img;//头像(base64)
//                public String introduction;//简介
//                public String mobile;//手机号
                public String AGE;
                public String UNIT_NAME;
                public String HEAD_IMG;
                public String DEPARTMENT_NAME;
                public int UNIT_ID;
                public String BIRTH;
                public String ID_NUMBER;
                public String PHONE;
                public String MOBILE;
                public int SEX;
                public String INTRODUCTION;
                public String HOME_ADDRESS;
                public String USERNAME;
                public int DEPARTMENT_ID;
                public String EMAIL;
                public String CENSUS_REGISTER;
                public String POSITION;
                public int USERID;

            }
        }
    }

    public static MyDataBM MyDataBMList = new MyDataBM();

    /**
     * 个人资料-单位部门
     *
     * @author yusi
     */
    public static class MyDataBM {
        public String message;
        public String status;
        public DataBean data;
        public String version;

        public static class DataBean {
            public PagingBean Paging;
            public List<UnitlistPageBean> UnitlistPage;

            public static class PagingBean {
                public String TotalPage;
                public String ShowCount;
                public String TotalResult;
                public String CurrentResult;
                public String CurrentPage;
            }

            public static class UnitlistPageBean {
                public String unit_id;
                public String unit_name;
                public List<DepartmentlistBean> Departmentlist;

                public static class DepartmentlistBean {
                    public int department_id;
                    public String department_name;
                }
            }
        }
    }


    public static MyPartyPay myPartyPaylist = new MyPartyPay();

    /**
     * 我的党费、党费查询
     *
     * @author weixuan
     */
    public static class MyPartyPay {

        /**
         * message : success
         * status : 1
         * data : {"partyMemberlist":[{"PERIOD_OF_TIME":"2016年01月","MONEY":"300","USERNAME":"李四","ADD_TIME":"","PARTY_MEMBERSHIP_DUES_ID":"4","TYPE":"0","USERID":"7"},{"PERIOD_OF_TIME":"2016年02月","MONEY":"300","USERNAME":"李四","ADD_TIME":"","PARTY_MEMBERSHIP_DUES_ID":"5","TYPE":"0","USERID":"7"}],"Paging":{"TotalPage":"1","ShowCount":"10","TotalResult":"2","CurrentResult":"0","CurrentPage":"1"}}
         * version : v1.0
         */

        public String message;
        public String status;
        /**
         * partyMemberlist : [{"PERIOD_OF_TIME":"2016年01月","MONEY":"300","USERNAME":"李四","ADD_TIME":"","PARTY_MEMBERSHIP_DUES_ID":"4","TYPE":"0","USERID":"7"},{"PERIOD_OF_TIME":"2016年02月","MONEY":"300","USERNAME":"李四","ADD_TIME":"","PARTY_MEMBERSHIP_DUES_ID":"5","TYPE":"0","USERID":"7"}]
         * Paging : {"TotalPage":"1","ShowCount":"10","TotalResult":"2","CurrentResult":"0","CurrentPage":"1"}
         */

        public DataBean data;
        public String version;

        public static class DataBean {
            /**
             * TotalPage : 1
             * ShowCount : 10
             * TotalResult : 2
             * CurrentResult : 0
             * CurrentPage : 1
             */

            public PagingBean Paging;
            /**
             * PERIOD_OF_TIME : 2016年01月
             * MONEY : 300
             * USERNAME : 李四
             * ADD_TIME :
             * PARTY_MEMBERSHIP_DUES_ID : 4
             * TYPE : 0
             * USERID : 7
             */

            public List<PartyMemberlistBean> partyMemberlist;

            public static class PagingBean {
                public String TotalPage;
                public String ShowCount;
                public String TotalResult;
                public String CurrentResult;
                public String CurrentPage;

            }

            public static class PartyMemberlistBean {
                public String PERIOD_OF_TIME;//时间段
                public String MONEY;//金额
                public String USERNAME;
                public String ADD_TIME;
                public String PARTY_MEMBERSHIP_DUES_ID;//党费表id
                public String TYPE;//状态（0未交费  1已缴费）
                public String USERID;

            }
        }
    }

    public static Partyer PartyerList = new Partyer();

    /**
     * 党员名册
     *
     * @author yusi
     */
    public static class Partyer {
        public DataBean data;
        public String message;
        public String version;
        public String status;

        public static class DataBean {
            public PagingBean Paging;
            public List<UserlistPageBean> UserlistPage;

            public static class PagingBean {
                public String ShowCount;
                public String TotalPage;
                public String CurrentPage;
                public String CurrentResult;
                public String TotalResult;
            }

            public static class UserlistPageBean {
                public String USERID;
                public String POSITION;//职务
                public String note;//备注（标签）
                public String UNIT_NAME;//单位
                public String DEPARTMENT_NAME;//部门
                public String USER_DATA_ID;//个人资料表的id
                public String SEX;//性别(0男1女)
                public String USERNAME;//用户名称
                public String HEAD_IMG;//头像(base64)
                public String AGE;//年龄
                public String MOBILE;//手机号码


            }
        }
    }
    public static  ZZplayer zzPlayerList = new ZZplayer();
    public static class ZZplayer{

        public DataBean data;
        public String message;
        public String version;
        public String status;
        public static class DataBean {

            public List<UserlistPageBean> UserlistPage;//单位

            public static class UserlistPageBean {
                public String unit_name;
                public String unit_id;


                public List<DepartmentlistBean> Departmentlist;//部门


                public static class DepartmentlistBean {
                    public int department_id;
                    public String department_name;
                    public List<UserlistBean> Userlist;

                    public static class UserlistBean {
                        public Object note;
                        public String DEPARTMENT_NAME;
                        public String POSITION;
                        public int USER_DATA_ID;
                        public int SEX;
                        public String UNIT_NAME;
                        public int USERID;
                        public String USERNAME;
                        public String HEAD_IMG;
                        public String AGE;
                        public String MOBILE;

                    }
                }
            }
        }
    }


    /**
     * 人物长廊
     *
     * @author weixuan
     */
    public static MyPALMPARTY palmparty = new MyPALMPARTY();

    public static class MyPALMPARTY {

        public String message;
        public String status;


        public DataBean data;
        public String version;

        public static class DataBean {
            /**
             * TotalPage : 1
             * ShowCount : 10
             * TotalResult : 1
             * CurrentResult : 0
             * CurrentPage : 1
             */

            public PagingBean Paging;


            public List<CommentListBean> commentList;


            public static class PagingBean {
                public String TotalPage;
                public String ShowCount;
                public String TotalResult;
                public String CurrentResult;
                public String CurrentPage;

            }

            public static class CommentListBean {
                public String title;//标题
                public String title_img_name;//标题图片名
                public String title_img;//图片名
                public String add_time;//时间
                public String newsid;//id

            }
        }
    }

    /**
     * 党规党纪
     *
     * @author weixuan
     */
    public static TCTPdanggui myTCTPdanggui = new TCTPdanggui();

    public static class TCTPdanggui {

        /**
         * message : success
         * status : 1
         * data : {"Paging":{"TotalPage":"1","ShowCount":"10","TotalResult":"2","CurrentResult":"0","CurrentPage":"1"},"commentList":[{"CONTENT":"飞飞飞","DESCRIBES":"分身乏术","USERNAME":"李四","ADD_TIME":"2016-09-14 11:53:51","TYPE":"1","USERID":"2","TITLE":"呵呵"},{"CONTENT":"爱妃","DESCRIBES":"阿斯达","USERNAME":"admin","ADD_TIME":"2016-09-14 17:39:32","TYPE":"1","USERID":"999999999","TITLE":"是大法官"}]}
         * version : v1.0
         */

        public String message;
        public String status;
        /**
         * Paging : {"TotalPage":"1","ShowCount":"10","TotalResult":"2","CurrentResult":"0","CurrentPage":"1"}
         * commentList : [{"CONTENT":"飞飞飞","DESCRIBES":"分身乏术","USERNAME":"李四","ADD_TIME":"2016-09-14 11:53:51","TYPE":"1","USERID":"2","TITLE":"呵呵"},{"CONTENT":"爱妃","DESCRIBES":"阿斯达","USERNAME":"admin","ADD_TIME":"2016-09-14 17:39:32","TYPE":"1","USERID":"999999999","TITLE":"是大法官"}]
         */

        public DataBean data;
        public String version;


        public static class DataBean {
            /**
             * TotalPage : 1
             * ShowCount : 10
             * TotalResult : 2
             * CurrentResult : 0
             * CurrentPage : 1
             */

            public PagingBean Paging;
            /**
             * CONTENT : 飞飞飞
             * DESCRIBES : 分身乏术
             * USERNAME : 李四
             * ADD_TIME : 2016-09-14 11:53:51
             * TYPE : 1
             * USERID : 2
             * TITLE : 呵呵
             */

            public List<CommentListBean> commentList;


            public static class PagingBean {
                public String TotalPage;
                public String ShowCount;
                public String TotalResult;
                public String CurrentResult;
                public String CurrentPage;


            }

            public static class CommentListBean {
                public String CONTENT;
                public String DESCRIBES;
                public String USERNAME;
                public String ADD_TIME;
                public String TYPE;
                public String USERID;
                public String TITLE;


            }
        }
    }

    /**
     * 党群动态,评论查看
     *
     * @author weixuan
     */

    public static Mydynamic mydynamic = new Mydynamic();

    public static class Mydynamic {
        public String message;
        public String status;
        public DataBean data;
        public String version;

        public static class DataBean {
            /**
             * TotalPage : 1
             * ShowCount : 10
             * TotalResult : 7
             * CurrentResult : 0
             * CurrentPage : 1
             */

            public PagingBean Paging;
            /**
             * content : 达瓦达瓦
             * username : 李四
             * imglist : [{"name":null,"path":null,"did":1},{"name":"dwdw","path":"feffe","did":1},{"name":"dwdw","path":"dwad","did":1}]
             * head_img :
             * userid : 2
             * commentslist : [{"content":"废物覅额覅分","commentsid":6,"username":"张三","to_userid":null,"nvid":1,"userid":1,"add_time":"2016-09-19 17:39:06","to_username":null}]
             * praiseType : 2
             * dynamicid : 1
             * praise : 0
             * add_time : 2016-09-20 15:36:01
             */
            public List<DynamiclistPageBean> DynamiclistPage;//动态

            public static class PagingBean {
                public String TotalPage;
                public String ShowCount;
                public String TotalResult;
                public String CurrentResult;
                public String CurrentPage;

            }

            public static class DynamiclistPageBean {
                public String content;
                public String username;
                public String head_img;
                public String userid;
                public String praiseType;
                public String dynamicid;
                public String praise;
                public String add_time;
                /**
                 * name : null
                 * path : null
                 * did : 1
                 */

                public List<ImglistBean> imglist;//图片集合
                /**
                 * content : 废物覅额覅分
                 * commentsid : 6
                 * username : 张三
                 * to_userid : null
                 * nvid : 1
                 * userid : 1
                 * add_time : 2016-09-19 17:39:06
                 * to_username : null
                 */

                public List<CommentslistBean> commentslist;//评论列表

                public static class ImglistBean {
                    public Object name;
                    public Object path;
                    public int did;
                    public String pictureid;
                }

                public static class CommentslistBean {
                    public String content;//评论内容
                    public int commentsid;//评论表id
                    public String username;//用户
                    public Object to_userid;
                    public int nvid;
                    public int userid;
                    public String add_time;
                    public Object to_username;
                }
            }
        }
    }
    public static Imageid myimageid = new Imageid();
    public static class Imageid{

        public DataBean data;

        public String message;
        public String version;
        public String status;

        public static class DataBean {

            public DynamicImgBean DynamicImg;

            public static class DynamicImgBean {
                public String path;
                public int pictureid;
                public String name;
                public int did;
            }
        }
    }


    public static paertyComm paertyCommList = new paertyComm();

    /**
     * 党委通知,学习园地,党员扶持
     *
     * @author yusi
     */
    public static class paertyComm {
        public String message;
        public String status;
        public DataBean data;
        public String version;

        public static class DataBean {
            public PagingBean Paging;
            public List<CommentListBean> commentList;

            public static class PagingBean {
                public String TotalPage;
                public String ShowCount;
                public String TotalResult;
                public String CurrentResult;
                public String CurrentPage;
            }

            public static class CommentListBean {
                public String title;//标题
                public String describes;//描述
                public String browse;//浏览量
                public String title_img_name;//标题图片名称
                public String title_img;//标题图片(base64)
                public String studyType;//学习状态（0为未学习 1为已学习）
                public String praiseType;//赞的状态（0为未赞 1为已赞）
                public String add_time;//时间
                public String praise;//赞的数量
                public String newsid;//党委通知id
                public String username;//用户名

            }
        }
    }

    public static partyCommDetails partyCommDetailsList = new partyCommDetails();

    /**
     * 党委通知详情
     *
     * @author yusi
     */
    public static class partyCommDetails {
        public String message;
        public String status;
        public DataBean data;
        public String version;

        public static class DataBean {
            public NewsListBean NewsList;

            public static class NewsListBean {
                public String content;//内容（图文混合）
                public String title;//标题
                public int browse;//浏览量
                public int praiseType;//赞的状态（0为未赞 1为已赞）
                public String add_time;//时间
                public int praise;//赞的数量
                public int newsid;//党委通知id/学习园地id/党员扶持id

                public int studyType;//学习状态（0为未学习 1为已学习）[学习园地专用]
            }
        }
    }

    public static DucomentRoom DucomentRoomList = new DucomentRoom();
    public static String DucomentName = "";

    /**
     * 文档中心
     *
     * @author yusi
     */
    public static class DucomentRoom {
        public String message;
        public String status;
        public DataBean data;
        public String version;

        public static class DataBean {
            public PagingBean Paging;
            public List<CommentListBean> commentList;

            public static class PagingBean {
                public String TotalPage;
                public String ShowCount;
                public String TotalResult;
                public String CurrentResult;
                public String CurrentPage;
            }

            public static class CommentListBean {
                public String DOCUMENTID;//文档id
                public String PATH;//文档地址
                public String USERNAME;//发布人姓名
                public String ADD_TIME;//发布时间
                public String NAME;//文档名字
                public long SIZE;//文档大小
            }
        }
    }

    public static partyvideo partyvideoList = new partyvideo();

    /**
     * 党建视频 PARTYvideo
     *
     * @author yusi
     */
    public static class partyvideo {
        public String message;
        public String status;
        public DataBean data;
        public String version;

        public static class DataBean {
            public PagingBean Paging;
            public List<VideolistPageBean> videolistPage;

            public static class PagingBean {
                public String TotalPage;
                public String ShowCount;
                public String TotalResult;
                public String CurrentResult;
                public String CurrentPage;
            }

            public static class VideolistPageBean {
                public String browe;//浏览量
                public String username;//发布人姓名
                public String describes;//描述
                public String title;//标题
                public String img;//视频预览图(base64)
                public String path;//视频地址
                public String content;//
                public String userid;//发布人id
                public String praiseType;//赞的状态（0为未赞 1为已赞）
                public String praise;//赞的数量
                public String add_time;//时间
                public String videoid;//视频id
                public String img_name;//视频预览图名称
            }
        }
    }

    /**
     * 党建视频详情 PARTYvideo
     *
     * @author weixaun
     */
    public static Mypartyvideo mypartyvideo = new Mypartyvideo();
    public static class Mypartyvideo{
        public String message;
        public String status;
        public DataBean data;
        public String version;
        public static class DataBean {

            public VideoPageBean videoPage;


            public static class VideoPageBean {
                public int browe;//浏览量
                public String content;//内容
                public String username;//发布人
                public String title;//标题
                public String path;//视频地址
                public int userid;//发布人id
                public int praiseType;//
                public int praise;//
                public String add_time;//时间
                public int videoid;//视频id

            }
        }
    }


    public static FAQmar FAQmarList = new FAQmar();

    /**
     * 在线答疑
     *
     * @author yusi
     */
    public static class FAQmar {
        public String message;
        public String status;
        public DataBean data;
        public String version;

        public static class DataBean {
            public PagingBean Paging;
            public List<OnlineAnswerlistPageBean> onlineAnswerlistPage;

            public static class PagingBean {
                public String TotalPage;
                public String ShowCount;
                public String TotalResult;
                public String CurrentResult;
                public String CurrentPage;
            }

            public static class OnlineAnswerlistPageBean {
                public String online_answer_id;//问题表id
                public String userid;//提问人id
                public String answer;//回答内容
                public String head_img;//头像(base64)
                public String response_time;//回答时间
                public String response_username;//回答人姓名
                public String answer_state;//回答状态（0为审核中  1为待回答  2为已答复  3拒审）默认为0
                public String problem;//问题
                public String add_time;//提问时间
                public String username;//提问人名称
                public String response_userid;//回答人id
            }
        }
    }

    public static survey surveyList = new survey();

    /**
     * 问卷调查
     *
     * @author yusi
     */
    public static class survey {
        public String message;
        public String status;
        public DataBean data;
        public String version;

        public static class DataBean {
            public PagingBean Paging;
            public List<DynamiclistPageBean> DynamiclistPage;

            public static class PagingBean {
                public String TotalPage;
                public String ShowCount;
                public String TotalResult;
                public String CurrentResult;
                public String CurrentPage;
            }

            public static class DynamiclistPageBean {
                public String answerid;
                public String username;//发布人姓名
                public String topicid;
                public String qsid;
                public String userid;//发布人id
                public String period_of_time;//期次
                public String add_time;//发布时间
                public String questionnaire_survey_id;//问卷调查表id
                public String custom_answer_id;
                public List<TopiclistBean> Topiclist;

                public static class TopiclistBean {
                    public int topic_number;//题目序号
                    public int topicid;//题目id
                    public int qsid;
                    public String name;//题目名称
                    public int type;//题目类型(0单选  1多选)
                }
            }
        }
    }

    public static CommentMar CommentMarList = new CommentMar();
    public static int commType = 0;//1 党建通知 2 学习园地 3党员扶持

    /**
     * 评论
     *
     * @author yusi
     */
    public static class CommentMar {
        public String message;
        public String status;
        public DataBean data;
        public String version;

        public static class DataBean {
            public PagingBean Paging;
            public List<CommentListBean> commentList;

            public static class PagingBean {
                public String TotalPage;
                public String ShowCount;
                public String TotalResult;
                public String CurrentResult;
                public String CurrentPage;
            }

            public static class CommentListBean {
                public String CONTENT;//评论内容
                public String TO_USERID;//被评论人id
                public String USERNAME;//评论人姓名
                public String TO_USERNAME;//被评论人姓名
                public String ADD_TIME;//评论时间
                public String COMMENTSID;//评论表id
                public String USERID;//评论人id
            }
        }
    }

    public static message messageMar = new message();

    /**
     * 接收 message
     *
     * @author yusi
     */
    public static class message {
        public String message;
        public String status;
        public String version;
        public String head_img;
    }

    /**
     * 问卷调查查询
     */
    public static MyQuestions myQuestions = new MyQuestions();

    public static class MyQuestions {
        public String message;
        public String status;
        public DataBean data;
        public String version;

        public static class DataBean {
            /**
             * username : 李四
             * alist : [{"answer_id":6,"answer_name":"苹果","topic_id":24},{"answer_id":7,"answer_name":"香蕉","topic_id":24},{"answer_id":8,"answer_name":"葡萄","topic_id":24},{"answer_id":9,"answer_name":"梨","topic_id":24},{"answer_id":10,"answer_name":"人生果","topic_id":24},{"answer_id":11,"answer_name":"其他","topic_id":24}]
             * userid : 7
             * topic_name : 你喜欢以下哪些水果？
             * period_of_time : 3
             * type : 1
             * topic_id : 24
             */

            public List<QuestionlistPageBean> QuestionlistPage;

            public static class QuestionlistPageBean {
                public String username;
                public int userid;
                public String topic_name;
                public int period_of_time;
                public int type;
                public int topic_id;
                /**
                 * answer_id : 6
                 * answer_name : 苹果
                 * topic_id : 24
                 */

                public List<AlistBean> alist;

                public static class AlistBean {
                    public int answer_id;
                    public String answer_name;
                    public int topic_id;
                }
            }
        }
    }

    /**
     * 版本更新
     */
    public static NewVersion newVersion = new NewVersion();
    public static class NewVersion{
        public DataBean data;
        public String message;
        public String version;
        public String status;

        public static class DataBean {
            public AppBean App;

            public static class AppBean {
                public String path;
                public String name;
                public int state;
                public int type;
                public String version;
            }
        }
    }


//    ============================================================================

    /**
     * 党委通知和党建视频list的信息类
     */
    public static class PartyCommittee {
        public int imageView233;//右边图片
        public String item_bt;
        public String title;//标题
        public String context;//内容
        public String browse;//浏览数
        public String time;//

    }

    //    =========微信支付model
    public static WechatPay wechatpay = new WechatPay();

    public static class WechatPay {

        /**
         * appid : wxb4ba3c02aa476ea1
         * partnerid : 1305176001
         * package : Sign=WXPay
         * noncestr : f5f45f26c06c3e9532d0feb2b0d8b680
         * timestamp : 1475913841
         * prepayid : wx20161008160401c8ad3949f00563123837
         * sign : 95A364D8177FC1A27AEB12A0FD40997A
         */
        public String appid;
        public String partnerid;
        @SerializedName("package")
        public String packageX;
        public String noncestr;
        public String timestamp;
        public String prepayid;
        public String sign;


    }

    /**
     * 支付宝
     */
    public static zhifubao myzhifubao = new zhifubao();
    public static class zhifubao{

        /**
         * SecretKeyList : {"ali_name":"TUlJQ2RnSUJBREFOQmdrcWhraUc5dzBCQVFFRkFBU0NBbUF3Z2dKY0FnRUFBb0dCQUtoYksxelZ3dHlnOWd4cit6ZzRCbVY3UnphM1d4ekF4cm9US2VkWlJaUGQrSmlvSzQzZ295MTBoVEFWMTVlOWhYWlZzZjJiQ3NzWTQvdDVSS2tJNS9ITmQ2SmMwTHNNTU10Q0ptUTNLNUdxU1FUeGEyR3hyV3ZWNFJCS2paM1ljODlKYU5kckhtRFY5ZUc2dWtKaG93V1V6M255cStpVjhXTVpqc213M1IzL0FnTUJBQUVDZ1lBRk1SS01RVmRLQUhpMTkxcCs2Rk5pSzhRTmJ1Z1RZZXArOEhwWlZRcHRQZW9kb3duOHpSR2JKRDI3TnVFNEg3TU83Q09hQTM3NHRtTEpiWHJ0ajRXL2hWbFN0TEVGL3QyeDhaeTZkZ1dvV3oxeVY0SFBEVUtoaGNuSkF5VjYxSzdkd2pQbms2QS9XS2ZQVUJ0aVgrOEZ5YytGdUdQRDhIdlZ4ME1ITUl5RTZRSkJBTnFYcWxuTXd4VmlHSG90aldydXMyVS96c0FXWWhPdkJaTmc4cmhjQUg3K0o2Y1JWd1RKTThOYTlkMjZqWXdYN0RqOUhsNEs0amIyZVFVVWRtSlI1UVVDUVFERktySjMxdW5xOElzWU1oV29ZQWpYLzRMVmVpc3UwVlViTVpyN3BjWWQxaDhUUDFYbDZXSW80Um02ZE5DcFNRTTJhaXJ6VVEzd2pYQTJFZVJsd3VZekFrQm5FaGliR3pmcGYwVzNabjlHS3FPZ1hFUHF3eU1mME9rNkl2NlArNkdvUDhNR3ZlQmdPMWNUQ0hMaVNES3lHaDJpaVllbUpFK2lSdm10Y1lhWXViRFpBa0FzS0pIc0VUQTJ0RVVTMkRUTmp5U3I2OGdMczk3MEQySTJRdmZwSUltc3FRWXdzMkN6cTMrV2xFUEU1T0RPNlZGVTRKWmFCRzlRenZ5d3YzVWQ3WGJOQWtFQXVpeEhIN1B5ZWRLZmh3eWkrc3Z6Uy9UYlpXU0FwRG9CNGxjVk9MSXY0ZDhEUm5LbWxjczMzUzJTUE44MEdlSUFOdkc5dlhCQkxQdlZCeDJ1b0c3bkZ3JTNE"}
         */

        public DataBean data;
        /**
         * data : {"SecretKeyList":{"ali_name":"TUlJQ2RnSUJBREFOQmdrcWhraUc5dzBCQVFFRkFBU0NBbUF3Z2dKY0FnRUFBb0dCQUtoYksxelZ3dHlnOWd4cit6ZzRCbVY3UnphM1d4ekF4cm9US2VkWlJaUGQrSmlvSzQzZ295MTBoVEFWMTVlOWhYWlZzZjJiQ3NzWTQvdDVSS2tJNS9ITmQ2SmMwTHNNTU10Q0ptUTNLNUdxU1FUeGEyR3hyV3ZWNFJCS2paM1ljODlKYU5kckhtRFY5ZUc2dWtKaG93V1V6M255cStpVjhXTVpqc213M1IzL0FnTUJBQUVDZ1lBRk1SS01RVmRLQUhpMTkxcCs2Rk5pSzhRTmJ1Z1RZZXArOEhwWlZRcHRQZW9kb3duOHpSR2JKRDI3TnVFNEg3TU83Q09hQTM3NHRtTEpiWHJ0ajRXL2hWbFN0TEVGL3QyeDhaeTZkZ1dvV3oxeVY0SFBEVUtoaGNuSkF5VjYxSzdkd2pQbms2QS9XS2ZQVUJ0aVgrOEZ5YytGdUdQRDhIdlZ4ME1ITUl5RTZRSkJBTnFYcWxuTXd4VmlHSG90aldydXMyVS96c0FXWWhPdkJaTmc4cmhjQUg3K0o2Y1JWd1RKTThOYTlkMjZqWXdYN0RqOUhsNEs0amIyZVFVVWRtSlI1UVVDUVFERktySjMxdW5xOElzWU1oV29ZQWpYLzRMVmVpc3UwVlViTVpyN3BjWWQxaDhUUDFYbDZXSW80Um02ZE5DcFNRTTJhaXJ6VVEzd2pYQTJFZVJsd3VZekFrQm5FaGliR3pmcGYwVzNabjlHS3FPZ1hFUHF3eU1mME9rNkl2NlArNkdvUDhNR3ZlQmdPMWNUQ0hMaVNES3lHaDJpaVllbUpFK2lSdm10Y1lhWXViRFpBa0FzS0pIc0VUQTJ0RVVTMkRUTmp5U3I2OGdMczk3MEQySTJRdmZwSUltc3FRWXdzMkN6cTMrV2xFUEU1T0RPNlZGVTRKWmFCRzlRenZ5d3YzVWQ3WGJOQWtFQXVpeEhIN1B5ZWRLZmh3eWkrc3Z6Uy9UYlpXU0FwRG9CNGxjVk9MSXY0ZDhEUm5LbWxjczMzUzJTUE44MEdlSUFOdkc5dlhCQkxQdlZCeDJ1b0c3bkZ3JTNE"}}
         * message : success
         * version : v1.0
         * status : 1
         */

        public String message;
        public String version;
        public String status;

        public static class DataBean {
            /**
             * ali_name : TUlJQ2RnSUJBREFOQmdrcWhraUc5dzBCQVFFRkFBU0NBbUF3Z2dKY0FnRUFBb0dCQUtoYksxelZ3dHlnOWd4cit6ZzRCbVY3UnphM1d4ekF4cm9US2VkWlJaUGQrSmlvSzQzZ295MTBoVEFWMTVlOWhYWlZzZjJiQ3NzWTQvdDVSS2tJNS9ITmQ2SmMwTHNNTU10Q0ptUTNLNUdxU1FUeGEyR3hyV3ZWNFJCS2paM1ljODlKYU5kckhtRFY5ZUc2dWtKaG93V1V6M255cStpVjhXTVpqc213M1IzL0FnTUJBQUVDZ1lBRk1SS01RVmRLQUhpMTkxcCs2Rk5pSzhRTmJ1Z1RZZXArOEhwWlZRcHRQZW9kb3duOHpSR2JKRDI3TnVFNEg3TU83Q09hQTM3NHRtTEpiWHJ0ajRXL2hWbFN0TEVGL3QyeDhaeTZkZ1dvV3oxeVY0SFBEVUtoaGNuSkF5VjYxSzdkd2pQbms2QS9XS2ZQVUJ0aVgrOEZ5YytGdUdQRDhIdlZ4ME1ITUl5RTZRSkJBTnFYcWxuTXd4VmlHSG90aldydXMyVS96c0FXWWhPdkJaTmc4cmhjQUg3K0o2Y1JWd1RKTThOYTlkMjZqWXdYN0RqOUhsNEs0amIyZVFVVWRtSlI1UVVDUVFERktySjMxdW5xOElzWU1oV29ZQWpYLzRMVmVpc3UwVlViTVpyN3BjWWQxaDhUUDFYbDZXSW80Um02ZE5DcFNRTTJhaXJ6VVEzd2pYQTJFZVJsd3VZekFrQm5FaGliR3pmcGYwVzNabjlHS3FPZ1hFUHF3eU1mME9rNkl2NlArNkdvUDhNR3ZlQmdPMWNUQ0hMaVNES3lHaDJpaVllbUpFK2lSdm10Y1lhWXViRFpBa0FzS0pIc0VUQTJ0RVVTMkRUTmp5U3I2OGdMczk3MEQySTJRdmZwSUltc3FRWXdzMkN6cTMrV2xFUEU1T0RPNlZGVTRKWmFCRzlRenZ5d3YzVWQ3WGJOQWtFQXVpeEhIN1B5ZWRLZmh3eWkrc3Z6Uy9UYlpXU0FwRG9CNGxjVk9MSXY0ZDhEUm5LbWxjczMzUzJTUE44MEdlSUFOdkc5dlhCQkxQdlZCeDJ1b0c3bkZ3JTNE
             */

            public SecretKeyListBean SecretKeyList;
            public static class SecretKeyListBean {
                public String ali_name;

            }
        }
    }

    /**
     * 支付宝信息
     */
    public static zfbinfo myzfbinfo = new zfbinfo();
    public static  class zfbinfo{
        public String body;
        public int total_amount;
    }

    /**
     * 支付宝信息
     */
    public static tixing mytixing = new tixing();
    public static  class tixing{
        public int posn;
        public boolean checked;
    }


}
