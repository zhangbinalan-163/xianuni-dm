package com.alan.dm.entity;

import java.util.Date;

/**
 * 入党申请人阶段的资料
 * Created by zhangbinalan on 15/11/16.
 */
public class ApplicationInfo {
    private int id;
    private int personId;
    private Date createTime;
    private Date applyTime;
    private Date talkTime;
    private String talkContent;
    private int talkerId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
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

    public Date getTalkTime() {
        return talkTime;
    }

    public void setTalkTime(Date talkTime) {
        this.talkTime = talkTime;
    }

    public String getTalkContent() {
        return talkContent;
    }

    public void setTalkContent(String talkContent) {
        this.talkContent = talkContent;
    }

    public int getTalkerId() {
        return talkerId;
    }

    public void setTalkerId(int talkerId) {
        this.talkerId = talkerId;
    }
}
