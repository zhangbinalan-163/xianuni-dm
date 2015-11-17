package com.alan.dm.entity;

import java.util.Date;

/**
 * 积极分子信息
 * Created by zhangbinalan on 15/11/16.
 */
public class ActivitistInfo {
    private int id;
    private int personId;
    private Date evaluationTime;
    private String evaluationContent;
    private Date meetTime;
    private String meetContent;
    private String director;
    private boolean recorded;
    private Date createTime;

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

    public Date getEvaluationTime() {
        return evaluationTime;
    }

    public void setEvaluationTime(Date evaluationTime) {
        this.evaluationTime = evaluationTime;
    }

    public String getEvaluationContent() {
        return evaluationContent;
    }

    public void setEvaluationContent(String evaluationContent) {
        this.evaluationContent = evaluationContent;
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

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public boolean isRecorded() {
        return recorded;
    }

    public void setRecorded(boolean recorded) {
        this.recorded = recorded;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
