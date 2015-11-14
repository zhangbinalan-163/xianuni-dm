package com.alan.dm.entity;

import java.util.Date;

/**
 * 学习记录信息
 * @Date: 2015-11-14
 * @author: fan
 */
public class StudyRecordInfo {
    private int id;
    private String title; // 学习标题
    private String user; // 学习人员
    private Orgnization organization; // 组织名称
    private Date startDate; // 开始时间
    private Date endDate; // 结束时间
    private int period; // 学时
    private String studyType; // 学习类别
    private String content; // 学习内容
    private String harvest; // 收获

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Orgnization getOrganization() {
        return organization;
    }

    public void setOrganization(Orgnization organization) {
        this.organization = organization;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getStudyType() {
        return studyType;
    }

    public void setStudyType(String studyType) {
        this.studyType = studyType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHarvest() {
        return harvest;
    }

    public void setHarvest(String harvest) {
        this.harvest = harvest;
    }
}
