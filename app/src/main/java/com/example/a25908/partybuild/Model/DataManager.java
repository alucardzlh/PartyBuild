package com.example.a25908.partybuild.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 25908 on 2016/8/29.
 * 实体类model
 */

public class DataManager {
    /**
     * 党务通知list的信息类
     */
    public static class PartyCommittee{
        public int imageView;//右边图片
        public String title;//标题
        public String context;//内容
        public String  browse;//浏览数
        public String time;//

    }

    /**
     * 党员动态
     */
    public static class Dynamic{
        public int iv;//头像
        public String name;//名字
        public String context1;//内容
        public int zanshu;//点赞数
        public String time1;//
        public List<Integer> photos = new ArrayList<>();
        public List<CommentItem> pinlun = new ArrayList<>();
        public static class CommentItem {

            public String id;
            public String user;//名字
            public String ToReplyUser;
            public String ToReplyUserid;
            public String content;
            public String getToReplyUser(){
                return ToReplyUser;
            }
            public void setToReplyUser(String ToReplyUser){
                this.ToReplyUser = ToReplyUser;
            }
            public String getToReplyUserid(){
                return ToReplyUserid;
            }
            public void setToReplyUserid(String ToReplyUserid){
                this.ToReplyUserid = ToReplyUserid;
            }
            public String getId() {
                return id;
            }
            public void setId(String id) {
                this.id = id;
            }
            public String getContent() {
                return content;
            }
            public void setContent(String content) {
                this.content = content;
            }
            public String getUser() {
                return user;
            }
            public void setUser(String user) {
                this.user = user;
            }

        }
    }
}
