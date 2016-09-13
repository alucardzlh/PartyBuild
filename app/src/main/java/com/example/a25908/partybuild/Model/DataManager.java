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
                public String position;//职务
                public String birth;//出生年月
                public int sex;//性别(0男1女)
                public String phone;//电话
                public String id_number;//身份证号
                public String department;//部门
                public int userid;//用户id
                public String home_address;//现家庭住址
                public String unit;//单位
                public String username;//用户名称
                public String census_register;//户籍
                public String email;//邮箱
                public String age;//年龄
                public String head_img;//头像(base64)
                public String introduction;//简介
                public String mobile;//手机号
            }
        }
    }

    public  static MyPartyPay myPartyPaylist = new MyPartyPay();
    /**
     * 我的党费
     */
    public static class MyPartyPay{
        public String message;
        public String status;
        public String version;
        public List<partyMemberlist> pmlist = new ArrayList<>();

        public static class partyMemberlist{
            public String message;
            public String status;
            public String party_membership_dues_id;
            public String money;
            public String period_of_time;
            public int type;
            public String add_time;
            public String userid;
            public String username;

        }
    }






//    ============================================================================
    /**
     * 我的文档
     */
    public static class MyFilesModels{
        public String FilesName;//名字
        public String FilesSize;//大小
        public String FilesPath;//下载地址
    }
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
