package com.alan.dm.entity;

import java.util.Date;

/**
 * 预备党员信息
 * Created by zhangbinalan on 15/11/16.
 */
public class PrepareInfo {
    private int id;
    private Person person;

    private String branchApproval;
    private String approval;
    private String schoolApproval;
    private boolean application;//是否已经提交申请书
    private Date meetTime;
    private String meetContent;
    private Date createTime;

    private int personId;
    public int getPersonId() {
        if(person==null&&personId!=0){
            person=new Person();
            person.setId(personId);
        }
        return personId;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
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

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getBranchApproval() {
        return branchApproval;
    }

    public void setBranchApproval(String branchApproval) {
        this.branchApproval = branchApproval;
    }

    public String getSchoolApproval() {
        return schoolApproval;
    }

    public void setSchoolApproval(String schoolApproval) {
        this.schoolApproval = schoolApproval;
    }

    public boolean isApplication() {
        return application;
    }

    public void setApplication(boolean application) {
        this.application = application;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
