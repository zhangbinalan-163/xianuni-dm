package com.alan.dm.entity;

import java.util.Date;

/**
 * 党组织实体
 * Created by zhangbinalan on 15/11/11.
 */
public class Orgnization {

    public static int NORMAL=0;//正常状态
    public static int CANCEL=1;//撤销状态

    private int id;//ID
    private String name;//
    private int parent;//直接上级组织的ID
    private boolean hasSon;//是否是有下属组织
    private int status=NORMAL;//状态
    private Date createTime;//创建时间
    private Date updateTime;//更新时间
    private Date electionTime;//上次换届时间
    private Date cancelTime;//上次换届时间
    private String orgDesc;//描述

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public Date getElectionTime() {
        return electionTime;
    }

    public void setElectionTime(Date electionTime) {
        this.electionTime = electionTime;
    }

    public String getOrgDesc() {
        return orgDesc;
    }

    public void setOrgDesc(String orgDesc) {
        this.orgDesc = orgDesc;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public boolean isHasSon() {
        return hasSon;
    }

    public void setHasSon(boolean hasSon) {
        this.hasSon = hasSon;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }
}
