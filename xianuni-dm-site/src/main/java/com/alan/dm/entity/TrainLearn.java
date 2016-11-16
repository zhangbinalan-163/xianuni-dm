package com.alan.dm.entity;

import java.util.Date;

/**
 * 培训收获
 * Created by zhangbinalan on 15/12/6.
 */
public class TrainLearn {
    private int id;
    private int personId;
    private int trainId;
    private String learn;
    private Date createTime;
    private Date updateTime;
    private Person person;
    private EduTraining training;
    private Orgnization orgnization;

    public Orgnization getOrgnization() {
        return orgnization;
    }

    public void setOrgnization(Orgnization orgnization) {
        this.orgnization = orgnization;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getId() {
        return id;
    }

    public int getPersonId() {
        return personId;
    }

    public int getTrainId() {
        return trainId;
    }

    public String getLearn() {
        return learn;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Person getPerson() {
        return person;
    }

    public EduTraining getTraining() {
        return training;
    }

    public void setId(int id) {

        this.id = id;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public void setLearn(String learn) {
        this.learn = learn;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setTraining(EduTraining training) {
        this.training = training;
    }
}
