package com.zhuandian.lostandfound.entity;

import cn.bmob.v3.BmobObject;

/**
 * desc :
 * author：xiedong
 */
public class MessageCommentEntity extends BmobObject {
    private String content;
    private UserEntity userEntity;
    private LostAndFoundEntity lostAndFoundEntity;

    public LostAndFoundEntity getLostAndFoundEntity() {
        return lostAndFoundEntity;
    }

    public void setLostAndFoundEntity(LostAndFoundEntity lostAndFoundEntity) {
        this.lostAndFoundEntity = lostAndFoundEntity;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }


}
