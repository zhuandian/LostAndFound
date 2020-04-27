package com.zhuandian.lostandfound.entity;

import cn.bmob.v3.BmobObject;

/**
 * @author xiedong
 * @desc
 * @date 2020-04-27.
 */
public class LostAndFoundEntity extends BmobObject {
    private String title;
    private String content;
    private int type;  //1 丢失  2 捡到
    private UserEntity userEntity;
    private int likeCount;

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
