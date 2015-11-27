package com.alan.dm.entity;

import java.util.Date;

/**
 * 入党申请人阶段的资料
 * Created by zhangbinalan on 15/11/16.
 */
public class ApplierInfo {
    private int id;
    private Date createTime;
    private Date updateTime;
    private Date applyTime;
    private String talkContent;
    private Person talker;
    private int talkerId;
    private Date talkTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getTalkContent() {
        return talkContent;
    }

    public void setTalkContent(String talkContent) {
        this.talkContent = talkContent;
    }

    public Person getTalker() {
        return talker;
    }

    public void setTalker(Person talker) {
        this.talker = talker;
    }

    public int getTalkerId() {
        return talkerId;
    }

    public void setTalkerId(int talkerId) {
        this.talkerId = talkerId;
    }

    public Date getTalkTime() {
        return talkTime;
    }

    public void setTalkTime(Date talkTime) {
        this.talkTime = talkTime;
    }
}
