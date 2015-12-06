package com.alan.dm.entity;

import java.util.Date;

/**
 * 积极分子信息
 * Created by zhangbinalan on 15/11/16.
 */
public class ActivitistInfo {
    private int id;
    private Person person;
    private int personId;

    private Date evaluationTime;
    private String evaluationContent;
    private Date meetTime;
    private String meetContent;
    private String directorIds;
    private boolean recorded;//是否已备案
    private Date createTime;
    private Date updateTime;
    private int degree;
    private String profession;
    private boolean quntuan;//是否群团推优
    private boolean talked;

    public boolean isQuntuan() {
        return quntuan;
    }

    public void setQuntuan(boolean quntuan) {
        this.quntuan = quntuan;
    }

    public boolean isTalked() {
        return talked;
    }

    public void setTalked(boolean talked) {
        this.talked = talked;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        if(person==null&&personId!=0){
            person=new Person();
            person.setId(personId);
        }
        return person;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setPerson(Person person) {
        this.person = person;
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

    public String getDirectorIds() {
        return directorIds;
    }

    public void setDirectorIds(String directorIds) {
        this.directorIds = directorIds;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
