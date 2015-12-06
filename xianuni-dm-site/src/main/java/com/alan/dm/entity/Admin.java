package com.alan.dm.entity;

import java.util.Date;

/**
 * 管理员信息
 * Created by zhangbinalan on 15/11/15.
 */
public class Admin {
    public static final int STATUS_NORMAL = 0;
    public static final int STATUS_FORBIDDEN = 1;

    public static final int ORG_ADMIN=0;
    public static final int SYSTEM_ADMIN=1;

    private int id;
    private String name;
    private String schoolNumber;
    private String password;//MD5之后的密码
    private Date createTime;
    private Date updateTime;
    private int status;
    private int type;//0-部门管理员 1-超级管理员
    private int orgId;
    private Date passwordUpdateTime;

    public Date getPasswordUpdateTime() {
        return passwordUpdateTime;
    }

    public void setPasswordUpdateTime(Date passwordUpdateTime) {
        this.passwordUpdateTime = passwordUpdateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }
}
