package com.example.a25908.partybuild.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class DataManager {

    public static User userlist = new User();

    /**
     * 用户信息
     * @author yusi
     */
    public static class User{
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
                public String UNIT;
                public String AGE;
                public Object HEAD_IMG;
                public String BIRTH;
                public String ID_NUMBER;
                public String PHONE;
                public String MOBILE;
                public int SEX;
                public String INTRODUCTION;
                public String HOME_ADDRESS;
                public String USERNAME;
                public String EMAIL;
                public String CENSUS_REGISTER;
                public String DEPARTMENT;
                public String POSITION;
                public int USERID;

            }
        }
    }

    public static MyPartyPay myPartyPaylist = new MyPartyPay();

    /**
     * 我的党费
     * @author weixuan
     */
    public static class MyPartyPay{

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
    public  static Partyer PartyerList = new Partyer();
    /**
     * 党员名册
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
                public String UNIT;//单位
                public String USER_DATA_ID;//个人资料表的id
                public String SEX;//性别(0男1女)
                public String USERNAME;//用户名称
                public String HEAD_IMG;//头像(base64)
                public String AGE;//年龄

            }
        }
    }


    /**
     * 人物长廊
     * @author weixuan
     */
    public static MyPALMPARTY palmparty = new MyPALMPARTY();
    public static class MyPALMPARTY {

        public String message;
        public String status;

        public String version;


    }
    /**
     * 党规党纪
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
     * @author weixuan
     */

    public static Mydynamic mydynamic = new Mydynamic();
    public static class Mydynamic{
        public String message;
        public String status;
        /**
         * DynamiclistPage : [{"content":"达瓦达瓦","username":"李四","imglist":[{"name":null,"path":null,"did":1},{"name":"dwdw","path":"feffe","did":1},{"name":"dwdw","path":"dwad","did":1}],"head_img":"","userid":"2","commentslist":[{"content":"废物覅额覅分","commentsid":6,"username":"张三","to_userid":null,"nvid":1,"userid":1,"add_time":"2016-09-19 17:39:06","to_username":null}],"praiseType":"2","dynamicid":"1","praise":"0","add_time":"2016-09-20 15:36:01"},{"content":"色色粉色粉色","username":"张三","imglist":[],"head_img":"","userid":"1","commentslist":[{"content":"废物覅额覅分","commentsid":11,"username":"张三","to_userid":null,"nvid":2,"userid":1,"add_time":"2016-09-20 13:49:44","to_username":null}],"praiseType":"2","dynamicid":"2","praise":"0","add_time":"2016-09-14 15:59:56"},{"content":"色色粉色粉色","username":"张三","imglist":[],"head_img":"","userid":"1","commentslist":[],"praiseType":"2","dynamicid":"3","praise":"0","add_time":"2016-09-19 15:32:29"},{"content":"色色粉色粉色","username":"张三","imglist":[],"head_img":"","userid":"1","commentslist":[],"praiseType":"2","dynamicid":"4","praise":"0","add_time":"2016-09-19 16:24:37"},{"content":"啊发放都是高帅富高帅富","username":"张三","imglist":[],"head_img":"","userid":"1","commentslist":[],"praiseType":"2","dynamicid":"5","praise":"0","add_time":"2016-09-19 17:01:28"},{"content":"色色粉色粉色","username":"张三","imglist":[{"name":null,"path":null,"did":7}],"head_img":"","userid":"1","commentslist":[],"praiseType":"2","dynamicid":"7","praise":"0","add_time":"2016-09-20 14:10:34"},{"content":"色色粉色粉色","username":"张三","imglist":[{"name":null,"path":null,"did":8}],"head_img":"","userid":"1","commentslist":[],"praiseType":"2","dynamicid":"8","praise":"0","add_time":"2016-09-20 14:11:24"}]
         * Paging : {"TotalPage":"1","ShowCount":"10","TotalResult":"7","CurrentResult":"0","CurrentPage":"1"}
         */

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
                }

                public static class CommentslistBean {
                    public String content;
                    public int commentsid;
                    public String username;
                    public Object to_userid;
                    public int nvid;
                    public int userid;
                    public String add_time;
                    public Object to_username;
                }
            }
        }
    }

    public  static paertyComm paertyCommList = new paertyComm();

    /**
     * 党委通知,学习园地,党员扶持
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
                public String praiseType;//赞的状态（0为未赞 1为已赞）
                public String add_time;//时间
                public String praise;//赞的数量
                public String newsid;//党委通知id

            }
        }
    }

    public  static partyCommDetails partyCommDetailsList = new partyCommDetails();
    /**
     * 党委通知详情
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
                public long SIZE;//文档大小
            }
        }
    }

    public  static partyvideo partyvideoList = new partyvideo();

    /**
     * 党建视频 PARTYvideo
     * @author yusi
     */
    public static class partyvideo{
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
                public String userid;//发布人id
                public String praiseType;//赞的状态（0为未赞 1为已赞）
                public String praise;//赞的数量
                public String add_time;//时间
                public String videoid;//视频id
                public String img_name;//视频预览图名称
            }
        }
    }


    public  static message messageMar = new message();

    /**
     * 接收 message
     * @author yusi
     */
    public static class message{
        public String message;
        public String status;
        public String version;
    }

//    ============================================================================
    /**
     * 党委通知和党建视频list的信息类
     */
    public static class PartyCommittee{
        public int imageView233;//右边图片
        public String item_bt;
        public String title;//标题
        public String context;//内容
        public String  browse;//浏览数
        public String time;//

    }

    /**
     * 党员动态
     */
    public static class Dynamic {
        public String Dtid;
        public int iv;//头像
        public String Dtname;//名字
        public String context1;//内容
        public int zanshu;//点赞数
        public String time1;//
        public List<String> photos = new ArrayList<>();//图片
        public  List<CommentItem> pinlun = new ArrayList<>();//评论列表

        public static class CommentItem implements Serializable {
            public String Userid;
            public String Username;//名字
            public String ToReplyUser;//回复名字
            public String ToReplyUserid;
            public String context2;//评论
        }

    }
}
