package com.alan.dm.entity;

import java.util.Date;

/**
 * 教育培训
 * @Date: 2015-11-14
 * @author: fan
 */
public class EduTraining {
    private int id;
    private int type; // 培训类型 1-党员培训 2-学习记录 3-专题教育
    private String title; // 培训标题
    private int trainingType; // 培训类别
    private Orgnization organization; // 组织机构
    private Date startTime; // 开始时间
    private Date endTime; // 结束时间
    private int period; // 课时
    private String trainingObject; // 培训对象
    private String content; // 培训内容
    private Person person; // 考生
    private String harvest; // 收获
    private String opinion; // 意见
    private Date createTime; //
    private Date updateTime; //

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(int trainingType) {
        this.trainingType = trainingType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Orgnization getOrganization() {
        return organization;
    }

    public void setOrganization(Orgnization organization) {
        this.organization = organization;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getTrainingObject() {
        return trainingObject;
    }

    public void setTrainingObject(String trainingObject) {
        this.trainingObject = trainingObject;
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

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
