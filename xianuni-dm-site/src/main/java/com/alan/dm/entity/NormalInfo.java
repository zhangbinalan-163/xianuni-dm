package com.alan.dm.entity;

import java.util.Date;

/**
 * 正式党员对象
 * Created by zhangbinalan on 15/11/16.
 */
public class NormalInfo {
    private int id;
    private Person person;
    private Date applyTime;
    private String approval;
    private String branchApproval;
    private String schoolApproval;
    private Date createTime;

    private int personId;
    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
