package com.zhuandian.lostandfound.entity;

import cn.bmob.v3.BmobObject;

/**
 * @author xiedong
 * @desc
 * @date 2020-04-27.
 */
public class BookEntity extends BmobObject {
    private String title;
    private String content;
    private UserEntity userEntity;

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
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
}
