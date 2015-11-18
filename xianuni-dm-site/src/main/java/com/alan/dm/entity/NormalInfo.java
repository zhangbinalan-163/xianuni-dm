package com.alan.dm.entity;

import java.util.Date;

/**
 * 正式党员对象
 * Created by zhangbinalan on 15/11/16.
 */
public class NormalInfo {
    private int id;
    private Person person;
    private int applyTime;
    private String approval;
    private String branchApproval;
    private String schoolApproval;
    private Date createTime;

    public Person getPerson() {
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

    public int getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(int applyTime) {
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
