package com.alan.dm.entity;

import java.util.Date;

/**
 * 发展对象
 * Created by zhangbinalan on 15/11/16.
 */
public class IntentionInfo {
    private int id;
    private int trainHour;
    private Date meetTime;
    private String meetContent;
    private String politicalCheckContent;
    private String introducerIds;
    private boolean recorded;
    private String schoolApproval;
    private Date createTime;
    private Date updateTime;
    private boolean publiced;
    private String profession;
    private int degree;

    private Date beAcTime;
    private boolean yushen;

    public Date getBeAcTime() {
        return beAcTime;
    }

    public void setBeAcTime(Date beAcTime) {
        this.beAcTime = beAcTime;
    }

    public boolean isYushen() {
        return yushen;
    }

    public void setYushen(boolean yushen) {
        this.yushen = yushen;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrainHour() {
        return trainHour;
    }

    public void setTrainHour(int trainHour) {
        this.trainHour = trainHour;
    }

    public Date getMeetTime() {
        return meetTime;
    }

    public void setMeetTime(Date meetTime) {
        this.meetTime = meetTime;
    }

    public String getMeetContent() {
        return meetContent;
    }

    public void setMeetContent(String meetContent) {
        this.meetContent = meetContent;
    }

    public String getPoliticalCheckContent() {
        return politicalCheckContent;
    }

    public void setPoliticalCheckContent(String politicalCheckContent) {
        this.politicalCheckContent = politicalCheckContent;
    }

    public String getIntroducerIds() {
        return introducerIds;
    }

    public void setIntroducerIds(String introducerIds) {
        this.introducerIds = introducerIds;
    }

    public boolean isRecorded() {
        return recorded;
    }

    public void setRecorded(boolean recorded) {
        this.recorded = recorded;
    }

    public String getSchoolApproval() {
        return schoolApproval;
    }

    public void setSchoolApproval(String schoolApproval) {
        this.schoolApproval = schoolApproval;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isPubliced() {
        return publiced;
    }

    public void setPubliced(boolean publiced) {
        this.publiced = publiced;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
