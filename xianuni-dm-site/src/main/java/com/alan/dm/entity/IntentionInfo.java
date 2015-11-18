package com.alan.dm.entity;

import java.util.Date;

/**
 * 发展对象
 * Created by zhangbinalan on 15/11/16.
 */
public class IntentionInfo {
    private int id;
    private Person person;

    private int trainHour;
    private Date meetTime;
    private String meetContent;
    private String politicalChcekContent;
    private int introducer;
    private boolean recorded;
    private String schoolApproval;
    private Date createTime;
    private boolean publiced;

    public boolean isPubliced() {
        return publiced;
    }

    public void setPubliced(boolean publiced) {
        this.publiced = publiced;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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

    public String getPoliticalChcekContent() {
        return politicalChcekContent;
    }

    public void setPoliticalChcekContent(String politicalChcekContent) {
        this.politicalChcekContent = politicalChcekContent;
    }

    public int getIntroducer() {
        return introducer;
    }

    public void setIntroducer(int introducer) {
        this.introducer = introducer;
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
}
