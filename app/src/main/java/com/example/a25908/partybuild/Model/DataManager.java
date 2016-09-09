package com.example.a25908.partybuild.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 25908 on 2016/8/29.
 * 实体类model
 */

public class DataManager {
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
