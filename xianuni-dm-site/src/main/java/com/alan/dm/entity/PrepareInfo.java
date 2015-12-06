package com.alan.dm.entity;

import java.util.Date;

/**
 * 预备党员信息
 * Created by zhangbinalan on 15/11/16.
 */
public class PrepareInfo {
    private int id;
    private String branchApproval;
    private String approval;
    private String schoolApproval;
    private boolean application;//是否已经提交申请书
    private Date meetTime;
    private String meetContent;
    private Date createTime;
    private Date updateTime;
    private String evaluation;//考察和综合评价
    private String profession;
    private int degree;
    private boolean publiced;

    public boolean isYushen() {
        return yushen;
    }

    public void setYushen(boolean yushen) {
        this.yushen = yushen;
    }

    public boolean isPubliced() {
        return publiced;
    }

    public void setPubliced(boolean publiced) {
        this.publiced = publiced;
    }

    private boolean yushen;
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

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBranchApproval() {
        return branchApproval;
    }

    public void setBranchApproval(String branchApproval) {
        this.branchApproval = branchApproval;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
