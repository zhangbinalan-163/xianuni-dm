package com.alan.dm.entity;

import java.util.Date;

/**
 * 组织关系转入转出
 * @Date: 2015-11-14
 * @author: fan
 */
public class RelationTransferInfo {
    private int id;
    private Person person;
    private int transferType;
    private int fromOrgId;
    private int toOrgId;
    private String fromOrgName;
    private String toOrgName;
    private String tel;
    private Date transferTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getTransferType() {
        return transferType;
    }

    public void setTransferType(int transferType) {
        this.transferType = transferType;
    }

    public int getFromOrgId() {
        return fromOrgId;
    }

    public void setFromOrgId(int fromOrgId) {
        this.fromOrgId = fromOrgId;
    }

    public int getToOrgId() {
        return toOrgId;
    }

    public void setToOrgId(int toOrgId) {
        this.toOrgId = toOrgId;
    }

    public String getFromOrgName() {
        return fromOrgName;
    }

    public void setFromOrgName(String fromOrgName) {
        this.fromOrgName = fromOrgName;
    }

    public String getToOrgName() {
        return toOrgName;
    }

    public void setToOrgName(String toOrgName) {
        this.toOrgName = toOrgName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }
}
