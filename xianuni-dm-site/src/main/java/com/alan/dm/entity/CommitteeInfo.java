package com.alan.dm.entity;

import java.util.Date;

/**
 * Created by zhangbinalan on 15/11/26.
 */
public class CommitteeInfo {
    private int id;
    private int orgId;
    private Orgnization orgnization;
    private int personId;
    private Person person;
    private int job;
    private Date createTime;
    private Date updateTime;
    private int level;//第几届
    private String tel;
    private String cdesc;
    private String profession;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCdesc() {
        return cdesc;
    }

    public void setCdesc(String cdesc) {
        this.cdesc = cdesc;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public Orgnization getOrgnization() {
        return orgnization;
    }

    public void setOrgnization(Orgnization orgnization) {
        this.orgnization = orgnization;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getJob() {
        return job;
    }

    public void setJob(int job) {
        this.job = job;
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
