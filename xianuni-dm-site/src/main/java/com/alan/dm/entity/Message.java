package com.alan.dm.entity;

import java.util.Date;

/**
 * 通知
 * @Date: 2015-11-14
 * @author: fan
 */
public class Message {
    private int id;
    private Date createTime; // 日期
    private String title; // 标题
    private String content; // 内容
    private int orgId;
    private Orgnization orgnization;
    private boolean toSub;//是否对下属所有的子部门可见
    private String urlList;

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


    public String getTitle() {
        return title;
    }

    public Orgnization getOrgnization() {
        return orgnization;
    }

    public void setOrgnization(Orgnization orgnization) {
        this.orgnization = orgnization;
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

    public boolean isToSub() {
        return toSub;
    }

    public void setToSub(boolean toSub) {
        this.toSub = toSub;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getUrlList() {
        return urlList;
    }

    public void setUrlList(String urlList) {
        this.urlList = urlList;
    }
}
