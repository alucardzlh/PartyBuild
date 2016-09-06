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
        public int iv;//头像
        public String name;//名字
        public String context1;//内容
        public int zanshu;//点赞数
        public String time1;//
        public List<String> photos = new ArrayList<>();//图片
        public  List<CommentItem> pinlun = new ArrayList<>();//评论列表
        public static class CommentItem implements Serializable {

            private User user;//名字
            private User ToReplyUser;
            private User ToReplyUserid;
            private String content;
            public User getToReplyUser(){
                return ToReplyUser;
            }
            public void setToReplyUser(User ToReplyUser){
                this.ToReplyUser = ToReplyUser;
            }
            public User getToReplyUserid(){
                return ToReplyUserid;
            }
            public void setToReplyUserid(User ToReplyUserid){
                this.ToReplyUserid = ToReplyUserid;
            }
            public String getContent() {
                return content;
            }
            public void setContent(String content) {
                this.content = content;
            }
            public User getUser() {
                return user;
            }
            public void setUser(User user) {
                this.user = user;
            }
        }
        public static class User {
            private String Userid;
            private String Username;
            private String headUrl;
            public User(String name){
                this.Username = name;
            }

            public User(String id, String name){
                this.Userid = id;
                this.Username = name;
            }
            public String getUserid() {
                return Userid;
            }
            public void setUserid(String Userid) {
                this.Userid = Userid;
            }
            public String getUsername() {
                return Username;
            }
            public void setUsername(String Username) {
                this.Username = Username;
            }
            public String getHeadUrl() {
                return headUrl;
            }
            public void setHeadUrl(String headUrl) {
                this.headUrl = headUrl;
            }

            @Override
            public String toString() {
                return "id = " + Userid
                        + "; name = " + Username
                        + "; headUrl = " + headUrl;
            }
        }


    }
}
